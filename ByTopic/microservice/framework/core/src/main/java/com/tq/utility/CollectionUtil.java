package com.tq.utility;

import java.io.Console;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.Field;
import java.lang.InstantiationException;
import java.lang.IllegalAccessException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;


public class CollectionUtil {

  public static <T> List<T> mergeList(List<T>... lists) {
    List<T> merged = new ArrayList<T>();
    for (List<T> l: lists) {
      merged.addAll(l);
    }
    return merged;
  }

  public static <T> T propertiesToObject(Properties properties, Class<T> clazz)
    throws InstantiationException, IllegalAccessException {
    T result = clazz.newInstance();
    for (String name: properties.stringPropertyNames()) {
      fillObject(result, name, properties.getProperty(name));
    }
    
    return result;
  }

  public static boolean isObjectContainField(Object object, String fieldName) {
    return Stream.of(object.getClass().getDeclaredFields())
      .anyMatch(field -> field.getName().equals(fieldName));
  }

  public static Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException {
    Field field = object.getClass().getDeclaredField(fieldName);
    boolean accessible = field.isAccessible();
    if (!accessible) {
      field.setAccessible(true);
    }
    Object value = null;
    try {
      value = field.get(object);
    } catch (IllegalAccessException e) {
      // ignore
    }
    if (!accessible) {
      field.setAccessible(accessible);
    }

    return value;
  }

  public static Object getOrCreateFieldValue(Object object, String fieldName) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
    Field field = object.getClass().getDeclaredField(fieldName);
    boolean accessible = field.isAccessible();
    if (!accessible) {
      field.setAccessible(true);
    }
    Object value = null;
    value = field.get(object);
    if (value == null) {
      value = field.getType().newInstance();
      field.set(object, value);
    }
    if (!accessible) {
      field.setAccessible(accessible);
    }

    return value;
  }

  public static void setFieldValue(Object object, String fieldName, Object value) {
    Util.mustRun(() -> {
        Field field = object.getClass().getDeclaredField(fieldName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        field.set(object, value);
        field.setAccessible(accessible);
      });
  }

  public static void setFieldValueString(Object object, String fieldName, String value) {
    Util.mustRun(() -> {
        Field field = object.getClass().getDeclaredField(fieldName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        String fieldType = field.getType().getName();
        
        if (fieldType.equals("int")) {
          field.setInt(object, Integer.valueOf(value));
        } else if (fieldType.equals("short")) {
          field.setShort(object, Short.valueOf(value));
        } else if (fieldType.equals("long")) {
          field.setLong(object, Long.valueOf(value));
        } else if (fieldType.equals("float")) {
          field.setFloat(object, Float.valueOf(value));
        } else if (fieldType.equals("double")) {
          field.setDouble(object, Double.valueOf(value));
        } else if (fieldType.equals("char")) {
          field.setChar(object, value.charAt(0));
        } else if (fieldType.equals("byte")) {
          field.setByte(object, Byte.valueOf(value));
        } else if (fieldType.equals("boolean")) {
          field.setBoolean(object, Boolean.valueOf(value));
        } else {
          field.set(object, value);
        }
        field.setAccessible(accessible);
      });
  }

  private static void fillObject(Object object, String fullFieldName, final Object value) {
    final String dot = "\\.";

    if (fullFieldName.isEmpty()) {
      return;
    }
    
    String[] piece = fullFieldName.split(dot, 2);
    if (piece.length == 1) {
      setFieldValueString(object, fullFieldName, (String) value);      
      return;
    } 
      
    final String fieldName = piece[0];
    if (!isObjectContainField(object, fieldName)) {
      return;
    }

    final String memberFieldName = piece[1];
    Util.mustRun(() -> {
        fillObject(getOrCreateFieldValue(object, fieldName), memberFieldName, value);
      });
  }
}
