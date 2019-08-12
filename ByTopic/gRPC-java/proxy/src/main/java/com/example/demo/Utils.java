package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

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
