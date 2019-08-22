package com.tq.gateway.generator;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import com.google.protobuf.DescriptorProtos.MethodDescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;

import com.tq.utility.Utils;

public class ProxyServiceGenerator {

  private static class Config {
    String descriptorSetFileName = "";
    String outputDirectory = "";
    Configuration freemarkerConfiguration;
  }
  
  public static class Builder {

    private Config config = new Config();

    public Builder setDescriptorSetFileName(String aFileName) {
      config.descriptorSetFileName = aFileName;
      return this;
    }

    public Builder setOutputDirectory(String aDirectory) {
      config.outputDirectory = aDirectory;
      return this;
    }
    
    public Builder setFreemarkerConfiguration(Configuration aConfig) {
      config.freemarkerConfiguration = aConfig;
      return this;
    }

    public ProxyServiceGenerator build() {
      return new ProxyServiceGenerator(config);
    }
  }

  private Config config;
  private ProxyServiceGenerator(Config aConfig) {
    config = aConfig;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public void generate() throws Exception {
    String descriptorSetFileName = config.descriptorSetFileName;
    
    FileDescriptorSet fileDescriptorSet = FileDescriptorSet.parseFrom(Files.readAllBytes(Paths.get(descriptorSetFileName)));

    fileDescriptorSet.getFileList()
      .stream()
      .forEach(file -> generateFromFile(file));
  }

  /**
   * Generate POJO and transmit service classes' source code from gRPC file.
   */
  private void generateFromFile(FileDescriptorProto file) {

    file.getMessageTypeList()
      .stream()
      .forEach(message -> {
          try {
            generatePojoSourceFile(message, file).write();
            generateCodecSourceFile(message, file).write();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });

    file.getServiceList()
      .stream()
      .forEach(service -> {
          try {
            generateServiceSourceFile(service, file).write();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  public SourceFile generateServiceSourceFile(ServiceDescriptorProto service, FileDescriptorProto file)
    throws TemplateException, TemplateNotFoundException, IOException, MalformedTemplateNameException {
    String rootPath = config.outputDirectory;
    String serviceName = service.getName();
    String fileName = rootPath + "/" + file.getPackage().replace(".", "/") + "/" + serviceName + ".java";
    String implClassName = serviceName + "Grpc." + serviceName + "ImplBase";
    ArrayList<MethodInfo> methodList = new ArrayList<>();

    String sourceCode = "";

    service.getMethodList()
      .stream()
      .forEach(method -> {
          String inputType = Utils.adjustClassName(method.getInputType());
          String outputType = Utils.adjustClassName(method.getOutputType());
          String javaMethodName = Utils.grpcMethodNameToJava(method.getName());
          HashMap<String, Object> values = new HashMap<>();
          values.put("methodName", javaMethodName);
          values.put("inputType", inputType);
          values.put("outputType", outputType);

          try {
            methodList.add(new MethodInfo(Utils.renderTemplate(config.freemarkerConfiguration,
                                                               getMethodTemplateName(method),
                                                               values)));
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });

    HashMap<String,Object> root = new HashMap<>();
    root.put("package", file.getPackage());
    root.put("service", serviceName);
    root.put("methods", methodList);
    sourceCode = Utils.renderTemplate(config.freemarkerConfiguration, "ServiceClass.ftlh", root);

    return new SourceFile(fileName, sourceCode);
  }

  /**
   * Generate POJO source code for gRPC message.
   */
  private SourceFile generatePojoSourceFile(DescriptorProto message, FileDescriptorProto file)
    throws TemplateException, TemplateNotFoundException, IOException, MalformedTemplateNameException {
    String rootPath = config.outputDirectory;
    String packageName = file.getPackage().isEmpty() ? "http" : file.getPackage() + ".http";
    String fullName = packageName + "." + message.getName();
    String fileName = rootPath + "/" + fullName.replace(".", "/") + ".java";
    ArrayList<FieldInfo> fieldList = new ArrayList<>(); 

    message.getFieldList()
      .stream()
      .forEach(field -> fieldList.add(new FieldInfo(Utils.toPojoClassName(field), Utils.underlineToPascalCase(field.getName()))));

    String sourceCode = "";
    HashMap<String,Object> root = new HashMap<>();
    root.put("package", packageName);
    root.put("class", message.getName());
    root.put("fields", fieldList);
    sourceCode = Utils.renderTemplate(config.freemarkerConfiguration, "POJOClass.ftlh", root);

    return new SourceFile(fileName, sourceCode);
  }

  /**
   * Generate Pojo2Proto codec.
   */
  private SourceFile generateCodecSourceFile(DescriptorProto message, FileDescriptorProto file) 
    throws TemplateException, TemplateNotFoundException, IOException, MalformedTemplateNameException {

    String rootPath = config.outputDirectory;
    String packageName = file.getPackage();
    String messageName = message.getName();
    ArrayList<String> protoSetStat = new ArrayList<>();
    ArrayList<String> pojoSetStat = new ArrayList<>();

    message.getFieldList()
      .stream()
      .forEach(field -> {
          String fieldName = field.getName();
          String protoSetter = "";
          String protoParam = "";
          String pojoSetter = "";
          String pojoParam = "";
          String pascalCase = Utils.underlineToPascalCase(fieldName);
          int fieldType = field.getType().getNumber();
          String fieldTypeName = Utils.adjustClassName(field.getTypeName());
          
          protoSetter = "set" + pascalCase;
          pojoSetter = "set" + pascalCase;
          protoParam = "pojo.get" + pascalCase + "()";
          pojoParam = "proto.get" + pascalCase + "()";

          if (fieldType == Type.TYPE_MESSAGE_VALUE) {
            protoParam = Utils.toCodecClassName(fieldTypeName) + ".toProto(pojo.get" + pascalCase + "())";
            pojoParam = Utils.toCodecClassName(fieldTypeName) + ".toPojo(proto.get" + pascalCase + "())";
          } else if (fieldType == Type.TYPE_ENUM_VALUE) {
            protoParam = fieldTypeName + ".forNumber(pojo.get" + pascalCase + "())";
            pojoParam = "proto.get" + pascalCase + "().getNumber()";
          }

          if (field.getLabel().getNumber() == Label.LABEL_REPEATED_VALUE) {
            protoSetter = "addAll" + pascalCase;
            pojoSetter = "set" + pascalCase;
            pojoParam = "proto.get" + pascalCase + "List()";
          }

          protoSetStat.add(String.format(".%s(%s)", protoSetter, protoParam));
          pojoSetStat.add(String.format("pojo.%s(%s);", pojoSetter, pojoParam));
        });

    HashMap<String,Object> values = new HashMap<>();
    values.put("package", packageName);
    values.put("message", messageName);
    values.put("protoSetStat", String.join("\n", protoSetStat));
    values.put("pojoSetStat", String.join("\n", pojoSetStat));
    
    String sourceCode = Utils.renderTemplate(config.freemarkerConfiguration, "CodecClass.ftlh", values);
    String fileName = rootPath + "/" + packageName.replace(".", "/") + "/http/codec/" + messageName + "Codec.java";
    return new SourceFile(fileName, sourceCode);
  }

  private String getMethodTemplateName(MethodDescriptorProto method) {
    if (method.hasClientStreaming() && method.hasServerStreaming()) {
      return "BidirectionStreamMethod.ftlh";
    } else if (method.hasClientStreaming() && !method.hasServerStreaming()) {
      return "ClientStreamMethod.ftlh";
    } else if (!method.hasClientStreaming() && !method.hasServerStreaming()) {
      return "UnaryMethod.ftlh";
    } else {
      return "ServerStreamMethod.ftlh";
    }
  }
}
