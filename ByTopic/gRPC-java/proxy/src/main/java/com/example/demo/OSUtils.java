package com.example.demo;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OSUtils {

  public static List<String> recurseListProtocolFileNames(File file) {
    return recurseListFileNamesBySuffix(file, ".proto");
  }

  public static List<String> recurseListFileNamesBySuffix(File file, String suffix) {
    return recurseListFileNames(file, f -> f.getName().endsWith(suffix));
  }

  public static List<String> recurseListJavaFileNames(String path) {
    return recurseListJavaFileNames(new File(path));
  }

  public static List<String> recurseListJavaFileNames(File file) {
    return recurseListFileNamesBySuffix(file, ".java");
  }

  public static List<String> recurseListFileNames(File root, Predicate<File> predicate) {
    List<String> result = new ArrayList<>();
    if (root.isDirectory()) {
      return Stream.of(root.listFiles())
        .map(file -> recurseListFileNames(file, predicate))
        .flatMap(List::stream)
        .collect(Collectors.toList());
    }

    if (predicate.test(root)) {
      result.add(root.getAbsolutePath());
    }

    return result;
  }

  public static void createDirectoryInNeed(String path) {
    File file = new File(path);
    if (file.exists() && !file.isDirectory()) {
      throw new RuntimeException("fail to create directory '" + path + "' which is existed but not a directory");
    }

    if (!file.exists() && !file.mkdirs()) {
      throw new RuntimeException("fail to create directory '" + path + "'.");
    }
  }

  public static Process createProcess(String... commandLine) throws IOException {
    return createProcess(Arrays.asList(commandLine));
  }

  public static Process createProcess(List<String> commandLine) throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder();
    processBuilder.command(commandLine);
    processBuilder.inheritIO();
    return processBuilder.start();    
  }
}
