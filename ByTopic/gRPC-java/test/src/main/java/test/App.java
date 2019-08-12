package test;

// TODO 处理repeat/required/optional。
// TODO 处理Any、OneOf。

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;


class SourceFile {
  public String sourceCode = "";
  public String fileName = "";

  public SourceFile() {
  }

  public SourceFile(String aFileName, String aSourceCode) {
    fileName = aFileName;
    sourceCode = aSourceCode;
  }

  @Override
  public String toString() {
    return String.format("<SourceCode: fileName=%s,sourceCode=...>", fileName);
  }

  public void write() throws IOException {
    Path path = Paths.get(fileName);
    File parent = path.getParent().toFile();
    if (!parent.exists() && !parent.mkdirs()) {
      throw new RuntimeException("cannot create output directory");
    }
    
    Files.write(path, sourceCode.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
  }
}

public class App {

  public static String renderTemplate(Configuration config, String templateName, Map<String,Object> values) throws Exception {

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(buffer);
    config.getTemplate(templateName).process(values, writer);
    return new String(buffer.toByteArray());
  }
  
  public static String adjustClassName(String className) {
    if (className.startsWith(".")) {
      return className.substring(1);
    }

    return className;
  }

  public static void main(String[] args) throws Exception {
    String descriptorSetFileName = "d:/workspace/project/demo/ByTopic/gRPC-java/build/a.d";

    FileDescriptorSet fileDescriptorSet = FileDescriptorSet.parseFrom(Files.readAllBytes(Paths.get(descriptorSetFileName)));

    fileDescriptorSet.getFileList()
      .stream()
      .forEach(App::processFile);
  }

  public static void processFile(FileDescriptorProto file) {

    String outputDirectory = "d:/workspace/project/demo/ByTopic/gRPC-java/build/";

    file.getMessageTypeList()
      .stream()
      .forEach(message -> {
          SourceFile sourceFile = generatePojoSourceFile(message, file, outputDirectory);
          try {
            sourceFile.write();
          } catch(IOException e) {
            e.printStackTrace();
          }
        });

    file.getServiceList()
      .stream()
      .forEach(service -> {
          try {
            generateServiceSourceFile(service, file, outputDirectory).write();
          } catch(IOException e) {
            e.printStackTrace();
          }
        });
  }

  public static SourceFile generateServiceSourceFile(ServiceDescriptorProto service, FileDescriptorProto file, String rootPath) {
    String serviceName = service.getName();
    String fileName = rootPath + "/" + file.getPackage().replace(".", "/") + "/" + serviceName + ".java";
    String implClassName = serviceName + "Grpc." + serviceName + "ImplBase";
    ArrayList<MethodInfo> methodList = new ArrayList<>();

    String sourceCode = "";
    try {
    
      Configuration config = new Configuration(Configuration.VERSION_2_3_28);
      config.setDirectoryForTemplateLoading(new File("d:/workspace/project/demo/ByTopic/gRPC-java/test/tmpl/"));

      service.getMethodList()
        .stream()
        .forEach(method -> {
            String inputType = adjustClassName(method.getInputType());
            String outputType = adjustClassName(method.getOutputType());
            String javaMethodName = grpcMethodNameToJava(method.getName());
            String templateName;
            HashMap<String, Object> values = new HashMap<>();
            values.put("methodName", javaMethodName);
            values.put("inputType", inputType);
            values.put("outputType", outputType);
            String methodBody = "";
              
            if (method.hasClientStreaming() && method.hasServerStreaming()) {
              templateName = "BidirectionStreamMethod.ftlh";
              values.put("stubObjectName", "blockingStub");
            } else if (method.hasClientStreaming() && !method.hasServerStreaming()) {
              templateName = "ClientStreamMethod.ftlh";
              values.put("stubObjectName", "blockingStub");
            } else if (!method.hasClientStreaming() && !method.hasServerStreaming()) {
              templateName = "UnaryMethod.ftlh";
              values.put("stubObjectName", "blockingStub");
            } else {
              templateName = "ServerStreamMethod.ftlh";
              values.put("stubObjectName", "blockingStub");
            }

            try {
              methodBody = renderTemplate(config, templateName, values);
            } catch (Exception e) {
              e.printStackTrace();
            }

            methodList.add(new MethodInfo(methodBody));
          });

      HashMap<String,Object> root = new HashMap<>();
      root.put("package", file.getPackage());
      root.put("service", serviceName);
      root.put("methods", methodList);
      Template tmpl = config.getTemplate("ServiceClass.ftlh");
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      OutputStreamWriter out = new OutputStreamWriter(buffer);
      tmpl.process(root, out);
      sourceCode = new String(buffer.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new SourceFile(fileName, sourceCode);
  }

  public static String grpcMethodNameToJava(String grpcServiceName) {
    if (grpcServiceName == null || grpcServiceName.isEmpty()) {
      return "";
    }

    return grpcServiceName.substring(0, 1).toLowerCase() + grpcServiceName.substring(1);
  }

  public static SourceFile generatePojoSourceFile(DescriptorProto message, FileDescriptorProto file, String rootPath) {
    String packageName = file.getPackage() + ".http";
    String fullName = packageName + "." + message.getName();
    String fileName = rootPath + "/" + fullName.replace(".", "/") + ".java";

    ArrayList<FieldInfo> fieldList = new ArrayList<>(); 

    message.getFieldList()
      .stream()
      .forEach(field -> {
          fieldList.add(new FieldInfo(translateType(field), field.getName()));
        });

    String sourceCode = "";
    try {
      Configuration config = new Configuration(Configuration.VERSION_2_3_28);
      config.setDirectoryForTemplateLoading(new File("d:/workspace/project/demo/ByTopic/gRPC-java/test/tmpl/"));
      HashMap<String,Object> root = new HashMap<>();
      root.put("package", packageName);
      root.put("class", message.getName());
      root.put("fields", fieldList);
      Template tmpl = config.getTemplate("pojo.ftlh");
      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      OutputStreamWriter out = new OutputStreamWriter(buffer);
      tmpl.process(root, out);
      sourceCode = new String(buffer.toByteArray());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return new SourceFile(fileName, sourceCode);
  }

  public static void processField(FieldDescriptorProto field, String message) {
    System.out.println("FIELD: " + field.getName() + " " + getTypeName(field.getType()));
  }

  public static String translateType(FieldDescriptorProto field) {
    switch (field.getType().getNumber()) {
    case Type.TYPE_BOOL_VALUE:
      return "boolean";
    case Type.TYPE_BYTES_VALUE:
      return "byte[]";
    case Type.TYPE_DOUBLE_VALUE:
      return "double";
    case Type.TYPE_ENUM_VALUE:
      return "int";
    case Type.TYPE_FIXED32_VALUE:
      return "int";
    case Type.TYPE_FIXED64_VALUE:
      return "long";
    case Type.TYPE_FLOAT_VALUE:
      return "float";
      // case Type.TYPE_GROUP_VALUE:
      //   return "group";
    case Type.TYPE_INT32_VALUE:
      return "int";
    case Type.TYPE_INT64_VALUE:
      return "long";
    case Type.TYPE_MESSAGE_VALUE:
      return adjustClassName(field.getTypeName());
    case Type.TYPE_SFIXED32_VALUE:
      return "int";
    case Type.TYPE_SFIXED64_VALUE:
      return "long";
    case Type.TYPE_SINT32_VALUE:
      return "int";
    case Type.TYPE_SINT64_VALUE:
      return "long";
    case Type.TYPE_STRING_VALUE:
      return "String";
    case Type.TYPE_UINT32_VALUE:
      return "int";
    case Type.TYPE_UINT64_VALUE:
      return "long";
    default:
      return "Object";
    }
  }
  
  public static String getTypeName(Type type) {

    switch (type.getNumber()) { 
    case Type.TYPE_BOOL_VALUE:
      return "bool";
    case Type.TYPE_BYTES_VALUE:
      return "bytes";
    case Type.TYPE_DOUBLE_VALUE:
      return "double";
    case Type.TYPE_ENUM_VALUE:
      return "enum";
    case Type.TYPE_FIXED32_VALUE:
      return "fixed32";
    case Type.TYPE_FIXED64_VALUE:
      return "fixed64";
    case Type.TYPE_FLOAT_VALUE:
      return "float";
    case Type.TYPE_GROUP_VALUE:
      return "group";
    case Type.TYPE_INT32_VALUE:
      return "int32";
    case Type.TYPE_INT64_VALUE:
      return "int64";
    case Type.TYPE_MESSAGE_VALUE:
      return "message";
    case Type.TYPE_SFIXED32_VALUE:
      return "sfixed32";
    case Type.TYPE_SFIXED64_VALUE:
      return "sfixed64";
    case Type.TYPE_SINT32_VALUE:
      return "sint32";
    case Type.TYPE_SINT64_VALUE:
      return "sint64";
    case Type.TYPE_STRING_VALUE:
      return "string";
    case Type.TYPE_UINT32_VALUE:
      return "uint32";
    case Type.TYPE_UINT64_VALUE:
      return "uint64";
    default:
      return "unknown";
    }
  }
}

