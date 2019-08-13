package test;

import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorProto;
import com.google.protobuf.DescriptorProtos.ServiceDescriptorProto;
import com.google.protobuf.DescriptorProtos.FileDescriptorSet;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto;
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Type;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

import freemarker.template.Configuration;
import freemarker.template.Template;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.ByteArrayOutputStream;

public class App {

  public static void main(String[] args) throws Exception {

    Configuration config = new Configuration(Configuration.VERSION_2_3_28);
    config.setDirectoryForTemplateLoading(new File("d:/workspace/project/demo/ByTopic/gRPC-java/test/tmpl/"));

    TransmitServiceSourceCodeGenerator.newBuilder()
      .setDescriptorSetFileName("d:/workspace/project/demo/ByTopic/gRPC-java/build/a.d")
      .setOutputDirectory("d:/workspace/project/demo/ByTopic/gRPC-java/build/")
      .setFreemarkerConfiguration(config)
      .build()
      .generate();
  }
}

