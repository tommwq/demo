package test;

// TODO 处理repeat/required/optional。
// TODO 处理Any、OneOf。

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {

  public static void main(String[] args) throws Exception {
    String descriptorSetFileName = "d:/workspace/project/demo/ByTopic/gRPC-java/build/a.d";

    FileDescriptorSet fileDescriptorSet = FileDescriptorSet.parseFrom(Files.readAllBytes(Paths.get(descriptorSetFileName)));

    fileDescriptorSet.getFileList()
      .stream()
      .forEach(App::processFile);
        
  }

  public static void processFile(FileDescriptorProto file) {

    file.getMessageTypeList()
      .stream()
      .forEach(message -> processMessage(message, file.getPackage()));

    file.getServiceList()
      .stream()
      .forEach(service -> {

          StringBuilder stringBuilder = new StringBuilder();
          String implClassName = String.format("%sGrpc.%sImplBase", service.getName(), service.getName());
          stringBuilder.append("package " + file.getPackage() + ";\n")
            .append("public class " + service.getName() + " extends " + implClassName + " {\n");

          System.out.println(service.getName());
          service.getMethodList()
            .stream()
            .forEach(method -> {
                String inputType = "void";
                String outputType = "void";

                if (!method.hasClientStreaming() && !method.hasServerStreaming()) {
                  inputType = method.getInputType() + " request, " + method.getOutputType() + " response";
                  outputType = "void";
                } else if (method.hasClientStreaming() && !method.hasServerStreaming()) {
                  inputType = String.format("io.grpc.stub.StreamObserver<%s>", method.getInputType());
                  outputType = String.format("io.grpc.stub.StreamObserver<%s> response", method.getOutputType());
                } else if (method.hasClientStreaming() && method.hasServerStreaming()) {
                  inputType = String.format("io.grpc.stub.StreamObserver<%s>", method.getInputType());
                  outputType = String.format("io.grpc.stub.StreamObserver<%s> response", method.getOutputType());
                } else {
                  inputType = method.getInputType() + " request, " + method.getOutputType() + " response";
                  outputType = "void";
                }
                
                stringBuilder.append(String.format("  public %s %s(%s) {}\n", outputType, method.getName(), inputType));
              });

          stringBuilder.append("}\n");
          System.out.println(stringBuilder.toString());
        });
  }

  public static void processMessage(DescriptorProto message, String packageName) {
    String fullName = packageName + "." + message.getName();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("package " + packageName + ";\n")
      .append("public class " + message.getName() + "{\n");

    message.getFieldList()
      .stream()
      .forEach(field -> {
          stringBuilder.append("  public " + translateType(field) + " " + field.getName() + ";\n");
        });

    stringBuilder.append("}\n");
    System.out.println(stringBuilder.toString());
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
      return field.getTypeName().startsWith(".") ? field.getTypeName().substring(1) : field.getTypeName();
    case Type.TYPE_SFIXED32_VALUE:
      return "int";
    case Type.TYPE_SFIXED64_VALUE:
      return "long";
    case Type.TYPE_SINT32_VALUE:
      return "int";
    case Type.TYPE_SINT64_VALUE:
      return "long";
    case Type.TYPE_STRING_VALUE:
      return "string";
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

