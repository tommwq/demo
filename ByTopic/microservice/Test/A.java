import java.io.*;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class A {

    static String protoRoot = "D:/Workspace/project/demo/ByTopic/microservice/Protocol/src/main/proto/";
    static String javaOut = "D:/Workspace/project/demo/ByTopic/microservice/Test/";
    static String protoPath = "\"D:/Program Files/protoc-3.6.1-win32/include/\"";
    
    static void walk(File root, List<File> output) {
        for (File file: root.listFiles()) {
            if (file.isFile()) {
                output.add(file);
            } else if (file.isDirectory()) {
                walk(file, output);
            }
        }
    }

    // protoc D:/Workspace/project/demo/ByTopic/microservice/Protocol/src/main/proto/*.proto --java_out D:/Workspace/project/demo/ByTopic/microservice/Test/ --proto_path "D:/Program Files/protoc-3.6.1-win32/include/"  --proto_path  D:/Workspace/project/demo/ByTopic/microservice/Protocol/src/main/proto

    // javac -d build D:/Workspace/project/demo/ByTopic/microservice/Test/./com/tq/microservice/common/InstanceId.java --class-path "C:/Users/guosen/.gradle/caches/modules-2/files-2.1/com.google.protobuf/protobuf-java/3.7.1/bce1b6dc9e4531169542ab37a1c8641bcaa8afb/protobuf-java-3.7.1.jar;."

        
    public static void main(String... args) {
        // System.out.println("ok");
        File root = new File(protoRoot);
        ArrayList<File> files = new ArrayList<>();
        walk(root, files);

        // files.stream().forEach((file)-> System.out.println(file.getAbsolutePath()));
        //        String.join(" ", files.stream().map((x)->x.getAbsolutePath()).toArray());

        String cmdline = Stream.concat(Stream.of("\"D:/Program Files/protoc-3.6.1-win32/bin/protoc.exe\"",
                                                 "--java_out",
                                                 javaOut,
                                                 "--proto_path",
                                                 protoPath,
                                                 "--proto_path",
                                                 protoRoot),
                                       files.stream().map((x)->x.getAbsolutePath())).collect(Collectors.joining(" "));
            try {
                System.out.println(cmdline);
                Process process = Runtime.getRuntime().exec(cmdline);

                BufferedReader ir = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader er = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                Thread t1 = new Thread(()-> {
                        ir.lines().forEach((x)-> System.out.println(x));
                });

                Thread t2 = new Thread(()-> {
                        er.lines().forEach((x)-> System.out.println(x));
                });

                t1.start();
                t2.start();
            
                System.out.println(process.waitFor());
        
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}

