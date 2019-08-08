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
    String fileName = rootPath + "/" + file.getPackage().replace(".", "/") + "/" + service.getName() + ".java";
    
    StringBuilder stringBuilder = new StringBuilder();
    String implClassName = String.format("%sGrpc.%sImplBase", service.getName(), service.getName());
    stringBuilder.append("package " + file.getPackage() + ";\n")
      .append("public class " + service.getName() + " extends " + implClassName + " {\n");

    service.getMethodList()
      .stream()
      .forEach(method -> {
          String parameterList = "void";
          String returnType = "void";
          String methodBody = "";
          String inputType = adjustClassName(method.getInputType());
          String outputType = adjustClassName(method.getOutputType());

          if (!method.hasClientStreaming() && !method.hasServerStreaming()) {
            parameterList = inputType + " request, io.grpc.stub.StreamObserver<" + outputType + "> response";
            returnType = "void";
          } else if (method.hasClientStreaming() && !method.hasServerStreaming()) {
            parameterList = String.format("io.grpc.stub.StreamObserver<%s> responseStream", outputType);
            returnType = String.format("io.grpc.stub.StreamObserver<%s>", inputType);
            methodBody = String.format("    return new io.grpc.stub.StreamObserver<%s>() {\n  @Override\n  public void onNext(%s value) {}\n  @Override\n  public void onError(Throwable t) {}\n  @Override\n  public void onCompleted() {}\n  };", inputType, inputType);
          } else if (method.hasClientStreaming() && method.hasServerStreaming()) {
            parameterList = String.format("io.grpc.stub.StreamObserver<%s> responseStream", outputType);
            returnType = String.format("io.grpc.stub.StreamObserver<%s>", inputType);
            methodBody = String.format("    return new io.grpc.stub.StreamObserver<%s>() {\n  @Override\n  public void onNext(%s value) {}\n  @Override\n  public void onError(Throwable t) {}\n  @Override\n  public void onCompleted() {}\n  };", inputType, inputType);
          } else {
            parameterList = inputType + " request, io.grpc.stub.StreamObserver<" + outputType + "> response";
            returnType = "void";
          }
                
          stringBuilder.append(String.format("  public %s %s(%s) {\n%s\n  }\n", returnType, method.getName(), parameterList, methodBody));
        });

    stringBuilder.append("}\n");
    String sourceCode = stringBuilder.toString();

    return new SourceFile(fileName, sourceCode);
  }

  public static SourceFile generatePojoSourceFile(DescriptorProto message, FileDescriptorProto file, String rootPath) {
    String packageName = file.getPackage() + ".http";
    String fullName = packageName + "." + message.getName();
    String fileName = rootPath + "/" + fullName.replace(".", "/") + ".java";

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package " + packageName + ";\n")
      .append("public class " + message.getName() + "{\n");

    message.getFieldList()
      .stream()
      .forEach(field -> {
          stringBuilder.append("  public " + translateType(field) + " " + field.getName() + ";\n");
        });

    stringBuilder.append("}\n");
    String sourceCode = stringBuilder.toString();

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

