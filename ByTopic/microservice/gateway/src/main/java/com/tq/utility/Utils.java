package com.tq.utility;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

  public static <T,R> R call(Emitter<T,R> emitter, T... args) {
    return call(null, emitter, args);
  }

  public static <T,R> R call(Emitter<Throwable,R> errorHandler, Emitter<T,R> emitter, T... args) {
    try {
      return emitter.call(args);
    } catch (Exception error) {
      if (errorHandler == null) {
        return null;
      }
      
      try {
        return errorHandler.call(error);
      } catch (Exception fatalError) {
        throw new RuntimeException(fatalError);
      }
    }
  }

  public static <T> void run(Procedure<T> procedure, T... args) {
    run(null, procedure, args);
  }

  public static <T> void run(Procedure<Throwable> errorHandler, Procedure<T> procedure, T... args) {
    try {
      procedure.call(args);
    } catch (Exception error) {
      if (errorHandler == null) {
        return;
      }
      
      try {
        errorHandler.call(error);
      } catch (Exception fatalError) {
        throw new RuntimeException(fatalError);
      }
    }
  }

  public static Boolean notNull(Object object) {
    return not(object == null);
  }
  
  public static URL fileNameToUrl(String fileName) {
    return call(name -> new File(name[0]).toURI().toURL(), fileName);
  }
  
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

    String javaTypeName = "Object";
    int type = field.getType().getNumber();
    if (typeTranslateTable.containsKey(type)) {
      javaTypeName = typeTranslateTable.get(type);
    }

    if (type == Type.TYPE_MESSAGE_VALUE) {
      javaTypeName = adjustClassName(field.getTypeName());
    }

    int label = field.getLabel().getNumber();
    if (label == Label.LABEL_REPEATED_VALUE) {
      javaTypeName = String.format("java.util.List<%s>", javaTypeName);
    }

    return javaTypeName;
  }

  public static String grpcMethodNameToJava(String grpcServiceName) {
    if (grpcServiceName == null || grpcServiceName.isEmpty()) {
      return "";
    }

    return grpcServiceName.substring(0, 1).toLowerCase() + grpcServiceName.substring(1);
  }

  List<Class> getAllInterfaces(Class clazz) {
    if (clazz == null) {
      return new ArrayList<Class>();
    }

    return CollectionUtils.mergeList(Arrays.asList(clazz.getInterfaces()),
                                     getAllInterfaces(clazz.getSuperclass()));
  }

  public static boolean not(boolean condition) {
    return !condition;
  }
}
