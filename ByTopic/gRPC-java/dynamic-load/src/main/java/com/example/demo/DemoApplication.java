package com.example.demo;

import java.io.Console;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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

  @Override
  public void run(String... args) throws Exception {
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


    // 加载
    String[] jarFileNames = new String[]{
      "d:/workspace/project/demo/ByTopic/gRPC-java/dynamic-load/output/a.jar",
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
    };
       
    try {
      URLClassLoader classLoader = new URLClassLoader(Stream.of(jarFileNames)
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

      Stream.of(classLoader.getURLs())
        .forEach((url) -> System.out.println(url));

      String packageName = "com.tq.test.helloworld";
      String outerClassName = "HelloWorldProto";
      String serviceName = "Greeter";
      String className = String.format("%s.%sGrpc$%sImplBase", packageName, serviceName, serviceName);

      Class serviceClass = classLoader.loadClass(className); // Class.forName(className, false, classLoader);
      Method[] serviceMethods = serviceClass.getDeclaredMethods();

      Stream.of(serviceMethods)
        .filter((method) -> !method.getName().equals("bindService"))
        .forEach((method) -> {
          System.out.println(method.getName());

          System.out.println(method.getReturnType().getName());

          Stream.of(method.getParameterTypes())
            .forEach((type) -> {
                System.out.println(type.getName());
              });
        });
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
