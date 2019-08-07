package com.example.demo;

import java.io.Console;
import java.io.IOException;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

  public static class Config {
    public String protocolDirectory = "";
    public String buildDirectory = "";
    public String protoCompilerRootDirectory = "";
    public String grpcPluginPath = "";
    public String javaCompilerPath = "";
    public String classPath = "";
  }


  public static List<String> recurseListProtocolFileNames(File file) {
    return recurseListFileNamesBySuffix(file, ".proto");
  }

  public static List<String> recurseListFileNamesBySuffix(File file, String suffix) {
    return recurseListFileNames(file, f -> f.getName().endsWith(suffix));
  }

  public static boolean not(boolean condition) {
    return !condition;
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

  public static <T> List<T> mergeList(List<T>... lists) {
    List<T> merged = new ArrayList<T>();
    for (List<T> l: lists) {
      merged.addAll(l);
    }
    return merged;
  }

  private void compileGrpcFiles(String protoCompilerRootDirectory,
                                String protocolDirectory,
                                String buildDirectory,
                                String grpcPluginPath)
    throws IOException, InterruptedException {
    // TODO 处理操作系统是Linux的情况。

    createProcess(mergeList(Arrays.asList(protoCompilerRootDirectory + "/bin/protoc.exe",
                                          "--proto_path",
                                          protocolDirectory,
                                          "--proto_path",
                                          protoCompilerRootDirectory + "/include/google/protobuf",
                                          "--java_out=" + buildDirectory),
                            recurseListProtocolFileNames(new File(protocolDirectory)))).waitFor();

    createProcess(mergeList(Arrays.asList(protoCompilerRootDirectory + "/bin/protoc.exe",
                                          "--proto_path",
                                          protocolDirectory,
                                          "--proto_path",
                                          protoCompilerRootDirectory + "/include/google/protobuf",
                                          "--grpc-java_out=" + buildDirectory,
                                          "--plugin=protoc-gen-grpc-java=" + grpcPluginPath),
                            recurseListProtocolFileNames(new File(protocolDirectory)))).waitFor();
  }


  private static void compileJavaFiles(String javaCompilerPath,
                                       String sourceDirectory,
                                       String outputDirectory,
                                       String classPath)
    throws IOException, InterruptedException {

    createDirectoryInNeed(outputDirectory);
    createProcess(mergeList(Arrays.asList(javaCompilerPath,
                                          "-d",
                                          outputDirectory,
                                          "-classpath",
                                          classPath),
                            recurseListJavaFileNames(sourceDirectory))).waitFor();
  }

  private static void packageClassFiles(String jarPath,
                                        String outputFilename,
                                        String classFileDirectory) throws IOException, InterruptedException {
  
    createProcess(mergeList(Arrays.asList(jarPath,
                                          "cf",
                                          outputFilename,
                                          "-C",
                                          classFileDirectory),
                            Stream.of(new File(classFileDirectory).listFiles((dir, name) -> not(name.startsWith("."))))
                            .map(File::getName)
                            .collect(Collectors.toList()))).waitFor();
  }

  private void loadGrpcService(String jarFileName) {

    List<String> jarList = Arrays.asList(jarFileName,
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar",
                                         "C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-api/1.22.1/77311e5735c4097c5cce57f0f4d0847c51db63bb/grpc-api-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-context/1.22.1/1a074f9cf6f367b99c25e70dc68589f142f82d11/grpc-context-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-core/1.22.1/f8b6f872b7f069aaff1c3380b2ba7f91f06e4da1/grpc-core-1.22.1.jar",
                                         "D:/workspace/project/study/grpc-java/netty/build/libs/grpc-netty-1.21.0-SNAPSHOT.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-protobuf/1.21.0/ac92a46921f9bf922e76b46e5731eaf312545acb/grpc-protobuf-1.21.0.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-stub/1.22.1/910550293aab760b706827c5f71c80551e5490f3/grpc-stub-1.22.1.jar",
                                         "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/27.0.1-jre/bd41a290787b5301e63929676d792c507bbc00ae/guava-27.0.1-jre.jar",
                                         "D:/workspace/project/study/bazel/third_party/javax_annotations/javax.annotation-api-1.3.2.jar"
      );
       
    try {
      URLClassLoader classLoader = new URLClassLoader(jarList.stream()
                                                      .map((fileName) -> {
                                                          try {
                                                            return new File(fileName).toURI().toURL();
                                                          } catch (MalformedURLException e) {
                                                            return null;
                                                          }
                                                        })
                                                      .filter((url) -> url != null)
                                                      .collect(Collectors.toList())
                                                      .toArray(new URL[]{}),
                                                      Thread.currentThread().getContextClassLoader());

      String packageName = "com.tq.test.helloworld";
      String outerClassName = "HelloWorldProto";
      String serviceName = "Greeter";
      String className = String.format("%s.%sGrpc$%sImplBase", packageName, serviceName, serviceName);

      Class serviceClass = classLoader.loadClass(className); // Class.forName(className, false, classLoader);
      Method[] serviceMethods = serviceClass.getDeclaredMethods();

      Stream.of(serviceMethods)
        .filter((method) -> not(method.getName().equals("bindService")))
        // .forEach((method) -> {
        //     System.out.println(method.getName());
        //     System.out.println(method.getReturnType().getName());

        //     Stream.of(method.getParameterTypes())
        //       .forEach((type) -> {
        //           System.out.println(type.getName());
        //         });
        //   });
        .forEach(method -> printGrpcMethod(method));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void printGrpcMethod(Method method) {
    String returnType = method.getGenericReturnType().getTypeName();
    
    List<String> parameterList = Arrays.asList(method.getGenericParameterTypes())
      .stream()
      .map(Type::getTypeName)
      .collect(Collectors.toList());
    String methodName = method.getName();

    System.out.println(methodName + ": " + String.join(",", parameterList) + " -> " + returnType);
  }
  
  @Override
  public void run(String... args) throws Exception {

    // 配置
    Config config = new Config();
    config.protocolDirectory = "d:/workspace/project/demo/ByTopic/gRPC-java/protocol";
    config.buildDirectory = "d:/workspace/project/demo/ByTopic/gRPC-java/build";
    config.protoCompilerRootDirectory = "D:/Program Files/protoc-3.6.1-win32";
    config.grpcPluginPath = "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/protoc-gen-grpc-java/1.22.1/e0f304c1f3a7892543de91a3b6d7be4f0409257f/protoc-gen-grpc-java-1.22.1-windows-x86_64.exe";
    config.javaCompilerPath = "C:/Program Files (x86)/Java/jdk1.8.0_192/bin/javac.exe";
    config.classPath = ".;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-api/1.22.1/77311e5735c4097c5cce57f0f4d0847c51db63bb/grpc-api-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-context/1.22.1/1a074f9cf6f367b99c25e70dc68589f142f82d11/grpc-context-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-core/1.22.1/f8b6f872b7f069aaff1c3380b2ba7f91f06e4da1/grpc-core-1.22.1.jar;D:/workspace/project/study/grpc-java/netty/build/libs/grpc-netty-1.21.0-SNAPSHOT.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-protobuf/1.21.0/ac92a46921f9bf922e76b46e5731eaf312545acb/grpc-protobuf-1.21.0.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/io.grpc/grpc-stub/1.22.1/910550293aab760b706827c5f71c80551e5490f3/grpc-stub-1.22.1.jar;C:/Users/guosen/.gradle/caches/modules-2/files-2.1/com.google.guava/guava/27.0.1-jre/bd41a290787b5301e63929676d792c507bbc00ae/guava-27.0.1-jre.jar;D:/workspace/project/study/bazel/third_party/javax_annotations/javax.annotation-api-1.3.2.jar";

    String protocolDirectory = config.protocolDirectory;

    String buildDirectory = config.buildDirectory;
    createDirectoryInNeed(buildDirectory);
    compileGrpcFiles(config.protoCompilerRootDirectory,
                      config.protocolDirectory,
                      config.buildDirectory,
                      config.grpcPluginPath);

    compileJavaFiles(config.javaCompilerPath,
                     config.buildDirectory,
                     config.buildDirectory + "/classes",
                     config.classPath);

    packageClassFiles("C:/Program Files (x86)/Java/jdk1.8.0_192/bin/jar.exe",
                      config.buildDirectory + "/a.jar",
                      config.buildDirectory + "/classes");
                      
    loadGrpcService(config.buildDirectory + "/a.jar");
    
    /*
      # 进入主目录
      cd "d:/workspace/project/demo/ByTopic/gRPC-java/dynamic-load/"

      # 清理
      Remove-Item output/* -Recurse
      Clear-Host

      # 生成java
      protoc --proto_path=. --proto_path="D:\Program Files\protoc-3.6.1-win32\include\google\protobuf" --java_out=output helloworld.proto

      protoc --plugin="protoc-gen-grpc-java=C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\protoc-gen-grpc-java\1.22.1\e0f304c1f3a7892543de91a3b6d7be4f0409257f\protoc-gen-grpc-java-1.22.1-windows-x86_64.exe" --grpc-java_out=output helloworld.proto 


      # 生成文件列表
      cd output
      Get-ChildItem *.java -Recurse | ForEach-Object { Out-File -FilePath a.txt -Append -InputObject ($_.FullName -Replace "\\", "/") -Encoding ascii }

      New-Item -Type Directory -Name classes

      # 编译
      &"C:\Program Files (x86)\Java\jdk1.8.0_192\bin\javac.exe" "@a.txt" -d classes -classpath ".;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java/3.7.1/protobuf-java-3.7.1.jar;C:/Users/guosen/.m2/repository/com/google/protobuf/protobuf-java-util/3.7.1/protobuf-java-util-3.7.1.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\grpc-api\1.22.1\77311e5735c4097c5cce57f0f4d0847c51db63bb\grpc-api-1.22.1.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\grpc-context\1.22.1\1a074f9cf6f367b99c25e70dc68589f142f82d11\grpc-context-1.22.1.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\grpc-core\1.22.1\f8b6f872b7f069aaff1c3380b2ba7f91f06e4da1\grpc-core-1.22.1.jar;D:\workspace\project\study\grpc-java\netty\build\libs\grpc-netty-1.21.0-SNAPSHOT.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\grpc-protobuf\1.21.0\ac92a46921f9bf922e76b46e5731eaf312545acb\grpc-protobuf-1.21.0.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\io.grpc\grpc-stub\1.22.1\910550293aab760b706827c5f71c80551e5490f3\grpc-stub-1.22.1.jar;C:\Users\guosen\.gradle\caches\modules-2\files-2.1\com.google.guava\guava\27.0.1-jre\bd41a290787b5301e63929676d792c507bbc00ae\guava-27.0.1-jre.jar;D:\workspace\project\study\bazel\third_party\javax_annotations\javax.annotation-api-1.3.2.jar"

      # 打包
      jar cf a.jar -C classes com

      cd ..

      gradle bootRun

      # 加载
      # 寻找服务接口。
      # helloworld.Fareweller/SayGoodBye
      # option java_package = "com.tq.test.helloworld";
      # option java_outer_classname = "HelloWorldProto";
    */


  }
}
