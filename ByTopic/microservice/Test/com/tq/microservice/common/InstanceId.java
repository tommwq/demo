// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common.proto

package com.tq.microservice.common;

/**
 * <pre>
 * id for a service instance or client
 * </pre>
 *
 * Protobuf type {@code common.InstanceId}
 */
public  final class InstanceId extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:common.InstanceId)
    InstanceIdOrBuilder {
private static final long serialVersionUID = 0L;
  // Use InstanceId.newBuilder() to construct.
  private InstanceId(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private InstanceId() {
    ip_ = 0;
    port_ = 0;
    startTime_ = 0;
    pid_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private InstanceId(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {

            ip_ = input.readInt32();
            break;
          }
          case 16: {

            port_ = input.readInt32();
            break;
          }
          case 24: {

            startTime_ = input.readInt32();
            break;
          }
          case 32: {

            pid_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownFieldProto3(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.tq.microservice.common.CommonProto.internal_static_common_InstanceId_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.tq.microservice.common.CommonProto.internal_static_common_InstanceId_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.tq.microservice.common.InstanceId.class, com.tq.microservice.common.InstanceId.Builder.class);
  }

  public static final int IP_FIELD_NUMBER = 1;
  private int ip_;
  /**
   * <code>int32 ip = 1;</code>
   */
  public int getIp() {
    return ip_;
  }

  public static final int PORT_FIELD_NUMBER = 2;
  private int port_;
  /**
   * <pre>
   * 0 for ignore this field
   * </pre>
   *
   * <code>int32 port = 2;</code>
   */
  public int getPort() {
    return port_;
  }

  public static final int START_TIME_FIELD_NUMBER = 3;
  private int startTime_;
  /**
   * <pre>
   * 0 for ignore this field
   * </pre>
   *
   * <code>int32 start_time = 3;</code>
   */
  public int getStartTime() {
    return startTime_;
  }

  public static final int PID_FIELD_NUMBER = 4;
  private int pid_;
  /**
   * <pre>
   * 0 for ignore this field
   * </pre>
   *
   * <code>int32 pid = 4;</code>
   */
  public int getPid() {
    return pid_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (ip_ != 0) {
      output.writeInt32(1, ip_);
    }
    if (port_ != 0) {
      output.writeInt32(2, port_);
    }
    if (startTime_ != 0) {
      output.writeInt32(3, startTime_);
    }
    if (pid_ != 0) {
      output.writeInt32(4, pid_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (ip_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(1, ip_);
    }
    if (port_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, port_);
    }
    if (startTime_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, startTime_);
    }
    if (pid_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, pid_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.tq.microservice.common.InstanceId)) {
      return super.equals(obj);
    }
    com.tq.microservice.common.InstanceId other = (com.tq.microservice.common.InstanceId) obj;

    boolean result = true;
    result = result && (getIp()
        == other.getIp());
    result = result && (getPort()
        == other.getPort());
    result = result && (getStartTime()
        == other.getStartTime());
    result = result && (getPid()
        == other.getPid());
    result = result && unknownFields.equals(other.unknownFields);
    return result;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + IP_FIELD_NUMBER;
    hash = (53 * hash) + getIp();
    hash = (37 * hash) + PORT_FIELD_NUMBER;
    hash = (53 * hash) + getPort();
    hash = (37 * hash) + START_TIME_FIELD_NUMBER;
    hash = (53 * hash) + getStartTime();
    hash = (37 * hash) + PID_FIELD_NUMBER;
    hash = (53 * hash) + getPid();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.tq.microservice.common.InstanceId parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.tq.microservice.common.InstanceId parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.InstanceId parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.InstanceId parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.tq.microservice.common.InstanceId prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * id for a service instance or client
   * </pre>
   *
   * Protobuf type {@code common.InstanceId}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:common.InstanceId)
      com.tq.microservice.common.InstanceIdOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.tq.microservice.common.CommonProto.internal_static_common_InstanceId_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.tq.microservice.common.CommonProto.internal_static_common_InstanceId_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.tq.microservice.common.InstanceId.class, com.tq.microservice.common.InstanceId.Builder.class);
    }

    // Construct using com.tq.microservice.common.InstanceId.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      ip_ = 0;

      port_ = 0;

      startTime_ = 0;

      pid_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.tq.microservice.common.CommonProto.internal_static_common_InstanceId_descriptor;
    }

    @java.lang.Override
    public com.tq.microservice.common.InstanceId getDefaultInstanceForType() {
      return com.tq.microservice.common.InstanceId.getDefaultInstance();
    }

    @java.lang.Override
    public com.tq.microservice.common.InstanceId build() {
      com.tq.microservice.common.InstanceId result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.tq.microservice.common.InstanceId buildPartial() {
      com.tq.microservice.common.InstanceId result = new com.tq.microservice.common.InstanceId(this);
      result.ip_ = ip_;
      result.port_ = port_;
      result.startTime_ = startTime_;
      result.pid_ = pid_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return (Builder) super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return (Builder) super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return (Builder) super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return (Builder) super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return (Builder) super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.tq.microservice.common.InstanceId) {
        return mergeFrom((com.tq.microservice.common.InstanceId)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.tq.microservice.common.InstanceId other) {
      if (other == com.tq.microservice.common.InstanceId.getDefaultInstance()) return this;
      if (other.getIp() != 0) {
        setIp(other.getIp());
      }
      if (other.getPort() != 0) {
        setPort(other.getPort());
      }
      if (other.getStartTime() != 0) {
        setStartTime(other.getStartTime());
      }
      if (other.getPid() != 0) {
        setPid(other.getPid());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.tq.microservice.common.InstanceId parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.tq.microservice.common.InstanceId) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int ip_ ;
    /**
     * <code>int32 ip = 1;</code>
     */
    public int getIp() {
      return ip_;
    }
    /**
     * <code>int32 ip = 1;</code>
     */
    public Builder setIp(int value) {
      
      ip_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int32 ip = 1;</code>
     */
    public Builder clearIp() {
      
      ip_ = 0;
      onChanged();
      return this;
    }

    private int port_ ;
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 port = 2;</code>
     */
    public int getPort() {
      return port_;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 port = 2;</code>
     */
    public Builder setPort(int value) {
      
      port_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 port = 2;</code>
     */
    public Builder clearPort() {
      
      port_ = 0;
      onChanged();
      return this;
    }

    private int startTime_ ;
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 start_time = 3;</code>
     */
    public int getStartTime() {
      return startTime_;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 start_time = 3;</code>
     */
    public Builder setStartTime(int value) {
      
      startTime_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 start_time = 3;</code>
     */
    public Builder clearStartTime() {
      
      startTime_ = 0;
      onChanged();
      return this;
    }

    private int pid_ ;
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 pid = 4;</code>
     */
    public int getPid() {
      return pid_;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 pid = 4;</code>
     */
    public Builder setPid(int value) {
      
      pid_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 0 for ignore this field
     * </pre>
     *
     * <code>int32 pid = 4;</code>
     */
    public Builder clearPid() {
      
      pid_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFieldsProto3(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:common.InstanceId)
  }

  // @@protoc_insertion_point(class_scope:common.InstanceId)
  private static final com.tq.microservice.common.InstanceId DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.tq.microservice.common.InstanceId();
  }

  public static com.tq.microservice.common.InstanceId getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<InstanceId>
      PARSER = new com.google.protobuf.AbstractParser<InstanceId>() {
    @java.lang.Override
    public InstanceId parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new InstanceId(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<InstanceId> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<InstanceId> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.tq.microservice.common.InstanceId getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

