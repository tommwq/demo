// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: service.proto

package helloworld;

public final class HelloWorldProto {
  private HelloWorldProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_Request_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_Request_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_helloworld_Reply_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_helloworld_Reply_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rservice.proto\022\nhelloworld\"\033\n\007Request\022\020" +
      "\n\010userName\030\001 \001(\t\"\025\n\005Reply\022\014\n\004text\030\001 \001(\t2" +
      "=\n\005Greet\0224\n\010SayHello\022\023.helloworld.Reques" +
      "t\032\021.helloworld.Reply\"\000B\037\n\nhelloworldB\017He" +
      "lloWorldProtoP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_helloworld_Request_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_helloworld_Request_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_Request_descriptor,
        new java.lang.String[] { "UserName", });
    internal_static_helloworld_Reply_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_helloworld_Reply_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_helloworld_Reply_descriptor,
        new java.lang.String[] { "Text", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
