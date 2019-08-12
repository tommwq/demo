package test;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Utils {
  public static String renderTemplate(Configuration config,
                                      String templateName,
                                      Map<String, Object> values)
    throws TemplateException, TemplateNotFoundException, MalformedTemplateNameException, IOException {

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

  private static Map<Integer, String> typeNameTable = new HashMap<>();
  private static String unknownType = "unknown";
  static {
    typeNameTable.put(Type.TYPE_BOOL_VALUE, "bool");
    typeNameTable.put(Type.TYPE_BYTES_VALUE, "bytes");
    typeNameTable.put(Type.TYPE_DOUBLE_VALUE, "double");
    typeNameTable.put(Type.TYPE_ENUM_VALUE, "enum");
    typeNameTable.put(Type.TYPE_FIXED32_VALUE, "fixed32");
    typeNameTable.put(Type.TYPE_FIXED64_VALUE, "fixed64");
    typeNameTable.put(Type.TYPE_FLOAT_VALUE, "float");
    typeNameTable.put(Type.TYPE_GROUP_VALUE, "group");
    typeNameTable.put(Type.TYPE_INT32_VALUE, "int32");
    typeNameTable.put(Type.TYPE_INT64_VALUE, "int64");
    typeNameTable.put(Type.TYPE_MESSAGE_VALUE, "message");
    typeNameTable.put(Type.TYPE_SFIXED32_VALUE, "sfixed32");
    typeNameTable.put(Type.TYPE_SFIXED64_VALUE, "sfixed64");
    typeNameTable.put(Type.TYPE_SINT32_VALUE, "sint32");
    typeNameTable.put(Type.TYPE_SINT64_VALUE, "sint64");
    typeNameTable.put(Type.TYPE_STRING_VALUE, "string");
    typeNameTable.put(Type.TYPE_UINT32_VALUE, "uint32");
    typeNameTable.put(Type.TYPE_UINT64_VALUE, "uint64");
  }
  
  public static String getTypeName(Type type) {
    if (typeNameTable.containsKey(type.getNumber())) {
      return typeNameTable.get(type.getNumber());
    }

    return unknownType;    
  }

  private static Map<Integer, String> typeTranslateTable = new HashMap<>();
  static {
    typeTranslateTable.put(Type.TYPE_BOOL_VALUE, "boolean");
    typeTranslateTable.put(Type.TYPE_BYTES_VALUE, "byte[]");
    typeTranslateTable.put(Type.TYPE_DOUBLE_VALUE, "double");
    typeTranslateTable.put(Type.TYPE_ENUM_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_FIXED32_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_FIXED64_VALUE, "long");
    typeTranslateTable.put(Type.TYPE_FLOAT_VALUE, "float");
    typeTranslateTable.put(Type.TYPE_INT32_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_INT64_VALUE, "long");
    typeTranslateTable.put(Type.TYPE_SFIXED32_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_SFIXED64_VALUE, "long");
    typeTranslateTable.put(Type.TYPE_SINT32_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_SINT64_VALUE, "long");
    typeTranslateTable.put(Type.TYPE_STRING_VALUE, "String");
    typeTranslateTable.put(Type.TYPE_UINT32_VALUE, "int");
    typeTranslateTable.put(Type.TYPE_UINT64_VALUE, "long");
  }
  public static String translateType(FieldDescriptorProto field) {
    int type = field.getType().getNumber();
    if (typeTranslateTable.containsKey(type)) {
      return typeTranslateTable.get(type);
    }

    if (type == Type.TYPE_MESSAGE_VALUE) {
      return adjustClassName(field.getTypeName());
    }
    
    return "Object";
  }

  public static String grpcMethodNameToJava(String grpcServiceName) {
    if (grpcServiceName == null || grpcServiceName.isEmpty()) {
      return "";
    }

    return grpcServiceName.substring(0, 1).toLowerCase() + grpcServiceName.substring(1);
  }

}
