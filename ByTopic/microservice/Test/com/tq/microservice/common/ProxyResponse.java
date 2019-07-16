// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: common.proto

package com.tq.microservice.common;

/**
 * <pre>
 * service response
 * </pre>
 *
 * Protobuf type {@code common.ProxyResponse}
 */
public  final class ProxyResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:common.ProxyResponse)
    ProxyResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ProxyResponse.newBuilder() to construct.
  private ProxyResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ProxyResponse() {
    serviceName_ = "";
    serviceVersion_ = "";
    error_ = 0;
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private ProxyResponse(
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
          case 10: {
            com.tq.microservice.common.ProxyHeader.Builder subBuilder = null;
            if (header_ != null) {
              subBuilder = header_.toBuilder();
            }
            header_ = input.readMessage(com.tq.microservice.common.ProxyHeader.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(header_);
              header_ = subBuilder.buildPartial();
            }

            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            serviceName_ = s;
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            serviceVersion_ = s;
            break;
          }
          case 32: {
            int rawValue = input.readEnum();

            error_ = rawValue;
            break;
          }
          case 42: {
            com.google.protobuf.Any.Builder subBuilder = null;
            if (payload_ != null) {
              subBuilder = payload_.toBuilder();
            }
            payload_ = input.readMessage(com.google.protobuf.Any.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(payload_);
              payload_ = subBuilder.buildPartial();
            }

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
    return com.tq.microservice.common.CommonProto.internal_static_common_ProxyResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.tq.microservice.common.CommonProto.internal_static_common_ProxyResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.tq.microservice.common.ProxyResponse.class, com.tq.microservice.common.ProxyResponse.Builder.class);
  }

  public static final int HEADER_FIELD_NUMBER = 1;
  private com.tq.microservice.common.ProxyHeader header_;
  /**
   * <code>.common.ProxyHeader header = 1;</code>
   */
  public boolean hasHeader() {
    return header_ != null;
  }
  /**
   * <code>.common.ProxyHeader header = 1;</code>
   */
  public com.tq.microservice.common.ProxyHeader getHeader() {
    return header_ == null ? com.tq.microservice.common.ProxyHeader.getDefaultInstance() : header_;
  }
  /**
   * <code>.common.ProxyHeader header = 1;</code>
   */
  public com.tq.microservice.common.ProxyHeaderOrBuilder getHeaderOrBuilder() {
    return getHeader();
  }

  public static final int SERVICE_NAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object serviceName_;
  /**
   * <code>string service_name = 2;</code>
   */
  public java.lang.String getServiceName() {
    java.lang.Object ref = serviceName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      serviceName_ = s;
      return s;
    }
  }
  /**
   * <code>string service_name = 2;</code>
   */
  public com.google.protobuf.ByteString
      getServiceNameBytes() {
    java.lang.Object ref = serviceName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      serviceName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SERVICE_VERSION_FIELD_NUMBER = 3;
  private volatile java.lang.Object serviceVersion_;
  /**
   * <code>string service_version = 3;</code>
   */
  public java.lang.String getServiceVersion() {
    java.lang.Object ref = serviceVersion_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      serviceVersion_ = s;
      return s;
    }
  }
  /**
   * <code>string service_version = 3;</code>
   */
  public com.google.protobuf.ByteString
      getServiceVersionBytes() {
    java.lang.Object ref = serviceVersion_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      serviceVersion_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int ERROR_FIELD_NUMBER = 4;
  private int error_;
  /**
   * <code>.common.Error error = 4;</code>
   */
  public int getErrorValue() {
    return error_;
  }
  /**
   * <code>.common.Error error = 4;</code>
   */
  public com.tq.microservice.common.Error getError() {
    @SuppressWarnings("deprecation")
    com.tq.microservice.common.Error result = com.tq.microservice.common.Error.valueOf(error_);
    return result == null ? com.tq.microservice.common.Error.UNRECOGNIZED : result;
  }

  public static final int PAYLOAD_FIELD_NUMBER = 5;
  private com.google.protobuf.Any payload_;
  /**
   * <code>.google.protobuf.Any payload = 5;</code>
   */
  public boolean hasPayload() {
    return payload_ != null;
  }
  /**
   * <code>.google.protobuf.Any payload = 5;</code>
   */
  public com.google.protobuf.Any getPayload() {
    return payload_ == null ? com.google.protobuf.Any.getDefaultInstance() : payload_;
  }
  /**
   * <code>.google.protobuf.Any payload = 5;</code>
   */
  public com.google.protobuf.AnyOrBuilder getPayloadOrBuilder() {
    return getPayload();
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
    if (header_ != null) {
      output.writeMessage(1, getHeader());
    }
    if (!getServiceNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, serviceName_);
    }
    if (!getServiceVersionBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, serviceVersion_);
    }
    if (error_ != com.tq.microservice.common.Error.NO_ERROR.getNumber()) {
      output.writeEnum(4, error_);
    }
    if (payload_ != null) {
      output.writeMessage(5, getPayload());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (header_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getHeader());
    }
    if (!getServiceNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, serviceName_);
    }
    if (!getServiceVersionBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, serviceVersion_);
    }
    if (error_ != com.tq.microservice.common.Error.NO_ERROR.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, error_);
    }
    if (payload_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(5, getPayload());
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
    if (!(obj instanceof com.tq.microservice.common.ProxyResponse)) {
      return super.equals(obj);
    }
    com.tq.microservice.common.ProxyResponse other = (com.tq.microservice.common.ProxyResponse) obj;

    boolean result = true;
    result = result && (hasHeader() == other.hasHeader());
    if (hasHeader()) {
      result = result && getHeader()
          .equals(other.getHeader());
    }
    result = result && getServiceName()
        .equals(other.getServiceName());
    result = result && getServiceVersion()
        .equals(other.getServiceVersion());
    result = result && error_ == other.error_;
    result = result && (hasPayload() == other.hasPayload());
    if (hasPayload()) {
      result = result && getPayload()
          .equals(other.getPayload());
    }
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
    if (hasHeader()) {
      hash = (37 * hash) + HEADER_FIELD_NUMBER;
      hash = (53 * hash) + getHeader().hashCode();
    }
    hash = (37 * hash) + SERVICE_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getServiceName().hashCode();
    hash = (37 * hash) + SERVICE_VERSION_FIELD_NUMBER;
    hash = (53 * hash) + getServiceVersion().hashCode();
    hash = (37 * hash) + ERROR_FIELD_NUMBER;
    hash = (53 * hash) + error_;
    if (hasPayload()) {
      hash = (37 * hash) + PAYLOAD_FIELD_NUMBER;
      hash = (53 * hash) + getPayload().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.tq.microservice.common.ProxyResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.tq.microservice.common.ProxyResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.ProxyResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.tq.microservice.common.ProxyResponse parseFrom(
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
  public static Builder newBuilder(com.tq.microservice.common.ProxyResponse prototype) {
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
   * service response
   * </pre>
   *
   * Protobuf type {@code common.ProxyResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:common.ProxyResponse)
      com.tq.microservice.common.ProxyResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.tq.microservice.common.CommonProto.internal_static_common_ProxyResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.tq.microservice.common.CommonProto.internal_static_common_ProxyResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.tq.microservice.common.ProxyResponse.class, com.tq.microservice.common.ProxyResponse.Builder.class);
    }

    // Construct using com.tq.microservice.common.ProxyResponse.newBuilder()
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
      if (headerBuilder_ == null) {
        header_ = null;
      } else {
        header_ = null;
        headerBuilder_ = null;
      }
      serviceName_ = "";

      serviceVersion_ = "";

      error_ = 0;

      if (payloadBuilder_ == null) {
        payload_ = null;
      } else {
        payload_ = null;
        payloadBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.tq.microservice.common.CommonProto.internal_static_common_ProxyResponse_descriptor;
    }

    @java.lang.Override
    public com.tq.microservice.common.ProxyResponse getDefaultInstanceForType() {
      return com.tq.microservice.common.ProxyResponse.getDefaultInstance();
    }

    @java.lang.Override
    public com.tq.microservice.common.ProxyResponse build() {
      com.tq.microservice.common.ProxyResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.tq.microservice.common.ProxyResponse buildPartial() {
      com.tq.microservice.common.ProxyResponse result = new com.tq.microservice.common.ProxyResponse(this);
      if (headerBuilder_ == null) {
        result.header_ = header_;
      } else {
        result.header_ = headerBuilder_.build();
      }
      result.serviceName_ = serviceName_;
      result.serviceVersion_ = serviceVersion_;
      result.error_ = error_;
      if (payloadBuilder_ == null) {
        result.payload_ = payload_;
      } else {
        result.payload_ = payloadBuilder_.build();
      }
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
      if (other instanceof com.tq.microservice.common.ProxyResponse) {
        return mergeFrom((com.tq.microservice.common.ProxyResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.tq.microservice.common.ProxyResponse other) {
      if (other == com.tq.microservice.common.ProxyResponse.getDefaultInstance()) return this;
      if (other.hasHeader()) {
        mergeHeader(other.getHeader());
      }
      if (!other.getServiceName().isEmpty()) {
        serviceName_ = other.serviceName_;
        onChanged();
      }
      if (!other.getServiceVersion().isEmpty()) {
        serviceVersion_ = other.serviceVersion_;
        onChanged();
      }
      if (other.error_ != 0) {
        setErrorValue(other.getErrorValue());
      }
      if (other.hasPayload()) {
        mergePayload(other.getPayload());
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
      com.tq.microservice.common.ProxyResponse parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.tq.microservice.common.ProxyResponse) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.tq.microservice.common.ProxyHeader header_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.tq.microservice.common.ProxyHeader, com.tq.microservice.common.ProxyHeader.Builder, com.tq.microservice.common.ProxyHeaderOrBuilder> headerBuilder_;
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public boolean hasHeader() {
      return headerBuilder_ != null || header_ != null;
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public com.tq.microservice.common.ProxyHeader getHeader() {
      if (headerBuilder_ == null) {
        return header_ == null ? com.tq.microservice.common.ProxyHeader.getDefaultInstance() : header_;
      } else {
        return headerBuilder_.getMessage();
      }
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public Builder setHeader(com.tq.microservice.common.ProxyHeader value) {
      if (headerBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        header_ = value;
        onChanged();
      } else {
        headerBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public Builder setHeader(
        com.tq.microservice.common.ProxyHeader.Builder builderForValue) {
      if (headerBuilder_ == null) {
        header_ = builderForValue.build();
        onChanged();
      } else {
        headerBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public Builder mergeHeader(com.tq.microservice.common.ProxyHeader value) {
      if (headerBuilder_ == null) {
        if (header_ != null) {
          header_ =
            com.tq.microservice.common.ProxyHeader.newBuilder(header_).mergeFrom(value).buildPartial();
        } else {
          header_ = value;
        }
        onChanged();
      } else {
        headerBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public Builder clearHeader() {
      if (headerBuilder_ == null) {
        header_ = null;
        onChanged();
      } else {
        header_ = null;
        headerBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public com.tq.microservice.common.ProxyHeader.Builder getHeaderBuilder() {
      
      onChanged();
      return getHeaderFieldBuilder().getBuilder();
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    public com.tq.microservice.common.ProxyHeaderOrBuilder getHeaderOrBuilder() {
      if (headerBuilder_ != null) {
        return headerBuilder_.getMessageOrBuilder();
      } else {
        return header_ == null ?
            com.tq.microservice.common.ProxyHeader.getDefaultInstance() : header_;
      }
    }
    /**
     * <code>.common.ProxyHeader header = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.tq.microservice.common.ProxyHeader, com.tq.microservice.common.ProxyHeader.Builder, com.tq.microservice.common.ProxyHeaderOrBuilder> 
        getHeaderFieldBuilder() {
      if (headerBuilder_ == null) {
        headerBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.tq.microservice.common.ProxyHeader, com.tq.microservice.common.ProxyHeader.Builder, com.tq.microservice.common.ProxyHeaderOrBuilder>(
                getHeader(),
                getParentForChildren(),
                isClean());
        header_ = null;
      }
      return headerBuilder_;
    }

    private java.lang.Object serviceName_ = "";
    /**
     * <code>string service_name = 2;</code>
     */
    public java.lang.String getServiceName() {
      java.lang.Object ref = serviceName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serviceName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string service_name = 2;</code>
     */
    public com.google.protobuf.ByteString
        getServiceNameBytes() {
      java.lang.Object ref = serviceName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serviceName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string service_name = 2;</code>
     */
    public Builder setServiceName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serviceName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string service_name = 2;</code>
     */
    public Builder clearServiceName() {
      
      serviceName_ = getDefaultInstance().getServiceName();
      onChanged();
      return this;
    }
    /**
     * <code>string service_name = 2;</code>
     */
    public Builder setServiceNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      serviceName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object serviceVersion_ = "";
    /**
     * <code>string service_version = 3;</code>
     */
    public java.lang.String getServiceVersion() {
      java.lang.Object ref = serviceVersion_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        serviceVersion_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string service_version = 3;</code>
     */
    public com.google.protobuf.ByteString
        getServiceVersionBytes() {
      java.lang.Object ref = serviceVersion_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        serviceVersion_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string service_version = 3;</code>
     */
    public Builder setServiceVersion(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      serviceVersion_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string service_version = 3;</code>
     */
    public Builder clearServiceVersion() {
      
      serviceVersion_ = getDefaultInstance().getServiceVersion();
      onChanged();
      return this;
    }
    /**
     * <code>string service_version = 3;</code>
     */
    public Builder setServiceVersionBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      serviceVersion_ = value;
      onChanged();
      return this;
    }

    private int error_ = 0;
    /**
     * <code>.common.Error error = 4;</code>
     */
    public int getErrorValue() {
      return error_;
    }
    /**
     * <code>.common.Error error = 4;</code>
     */
    public Builder setErrorValue(int value) {
      error_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.common.Error error = 4;</code>
     */
    public com.tq.microservice.common.Error getError() {
      @SuppressWarnings("deprecation")
      com.tq.microservice.common.Error result = com.tq.microservice.common.Error.valueOf(error_);
      return result == null ? com.tq.microservice.common.Error.UNRECOGNIZED : result;
    }
    /**
     * <code>.common.Error error = 4;</code>
     */
    public Builder setError(com.tq.microservice.common.Error value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      error_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.common.Error error = 4;</code>
     */
    public Builder clearError() {
      
      error_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.Any payload_ = null;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> payloadBuilder_;
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public boolean hasPayload() {
      return payloadBuilder_ != null || payload_ != null;
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public com.google.protobuf.Any getPayload() {
      if (payloadBuilder_ == null) {
        return payload_ == null ? com.google.protobuf.Any.getDefaultInstance() : payload_;
      } else {
        return payloadBuilder_.getMessage();
      }
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public Builder setPayload(com.google.protobuf.Any value) {
      if (payloadBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        payload_ = value;
        onChanged();
      } else {
        payloadBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public Builder setPayload(
        com.google.protobuf.Any.Builder builderForValue) {
      if (payloadBuilder_ == null) {
        payload_ = builderForValue.build();
        onChanged();
      } else {
        payloadBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public Builder mergePayload(com.google.protobuf.Any value) {
      if (payloadBuilder_ == null) {
        if (payload_ != null) {
          payload_ =
            com.google.protobuf.Any.newBuilder(payload_).mergeFrom(value).buildPartial();
        } else {
          payload_ = value;
        }
        onChanged();
      } else {
        payloadBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public Builder clearPayload() {
      if (payloadBuilder_ == null) {
        payload_ = null;
        onChanged();
      } else {
        payload_ = null;
        payloadBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public com.google.protobuf.Any.Builder getPayloadBuilder() {
      
      onChanged();
      return getPayloadFieldBuilder().getBuilder();
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    public com.google.protobuf.AnyOrBuilder getPayloadOrBuilder() {
      if (payloadBuilder_ != null) {
        return payloadBuilder_.getMessageOrBuilder();
      } else {
        return payload_ == null ?
            com.google.protobuf.Any.getDefaultInstance() : payload_;
      }
    }
    /**
     * <code>.google.protobuf.Any payload = 5;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder> 
        getPayloadFieldBuilder() {
      if (payloadBuilder_ == null) {
        payloadBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.google.protobuf.Any, com.google.protobuf.Any.Builder, com.google.protobuf.AnyOrBuilder>(
                getPayload(),
                getParentForChildren(),
                isClean());
        payload_ = null;
      }
      return payloadBuilder_;
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


    // @@protoc_insertion_point(builder_scope:common.ProxyResponse)
  }

  // @@protoc_insertion_point(class_scope:common.ProxyResponse)
  private static final com.tq.microservice.common.ProxyResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.tq.microservice.common.ProxyResponse();
  }

  public static com.tq.microservice.common.ProxyResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ProxyResponse>
      PARSER = new com.google.protobuf.AbstractParser<ProxyResponse>() {
    @java.lang.Override
    public ProxyResponse parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new ProxyResponse(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<ProxyResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ProxyResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.tq.microservice.common.ProxyResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

