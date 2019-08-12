package com.example.demo;

import java.io.Console;
import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import com.sun.tools.javac.main.Main;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import java.io.*;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;


public class CollectionUtils {

  public static <T> List<T> mergeList(List<T>... lists) {
    List<T> merged = new ArrayList<T>();
    for (List<T> l: lists) {
      merged.addAll(l);
    }
    return merged;
  }
}
