package com.tq.applogmanagement;

import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * 字符串工具类。
 */
public class StringUtils {

  /**
   * 将对象转换为字符串。
   *
   * 只适用与原生类型、对象和数组。
   */
  public static String stringify(Object object) {
    if (object == null) {
      return "null";
    }

    if (object.getClass().isArray()) {
      Object[] array = (Object[]) object;
      return "Object[]{" + String.join(",", stringify(array)) + "}";
    }

    return object.toString();
  }

  /**
   * 将数组转换为字符串数组。
   */
  public static String[] stringify(Object[] objects) {
    if (objects == null) {
      return new String[]{};
    }
        
    return Stream.of(objects)
      .map(StringUtils::stringify)
      .collect(Collectors.toList())
      .toArray(new String[]{});
  }
}
