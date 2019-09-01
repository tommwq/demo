package com.tq.applogmanagement.utility;

import java.util.stream.Stream;
import java.util.stream.Collectors;

/**
 * Utility functions for string.
 */
public class StringUtil {

  /**
   * Dump object to string, support primitive type, Object and array.
   *
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
   * Dump array to string.
   */
  public static String[] stringify(Object[] objects) {
    if (objects == null) {
      return new String[]{};
    }
        
    return Stream.of(objects)
      .map(StringUtil::stringify)
      .collect(Collectors.toList())
      .toArray(new String[]{});
  }
}
