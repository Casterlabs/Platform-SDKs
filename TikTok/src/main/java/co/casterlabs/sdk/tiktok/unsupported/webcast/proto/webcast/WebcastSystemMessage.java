// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

/**
 * <pre>
 *&#64;SystemMessage
 * </pre>
 *
 * Protobuf type {@code TikTok.WebcastSystemMessage}
 */
public final class WebcastSystemMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TikTok.WebcastSystemMessage)
    WebcastSystemMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WebcastSystemMessage.newBuilder() to construct.
  private WebcastSystemMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WebcastSystemMessage() {
    message_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new WebcastSystemMessage();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastSystemMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastSystemMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.Builder.class);
  }

  private int bitField0_;
  public static final int COMMON_FIELD_NUMBER = 1;
  private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common common_;
  /**
   * <code>.TikTok.Common common = 1;</code>
   * @return Whether the common field is set.
   */
  @java.lang.Override
  public boolean hasCommon() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.TikTok.Common common = 1;</code>
   * @return The common.
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common getCommon() {
    return common_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : common_;
  }
  /**
   * <code>.TikTok.Common common = 1;</code>
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder getCommonOrBuilder() {
    return common_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : common_;
  }

  public static final int MESSAGE_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object message_ = "";
  /**
   * <code>string message = 2;</code>
   * @return The message.
   */
  @java.lang.Override
  public java.lang.String getMessage() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      message_ = s;
      return s;
    }
  }
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getMessageBytes() {
    java.lang.Object ref = message_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      message_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getCommon());
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, message_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getCommon());
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(message_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, message_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage)) {
      return super.equals(obj);
    }
    co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage other = (co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage) obj;

    if (hasCommon() != other.hasCommon()) return false;
    if (hasCommon()) {
      if (!getCommon()
          .equals(other.getCommon())) return false;
    }
    if (!getMessage()
        .equals(other.getMessage())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasCommon()) {
      hash = (37 * hash) + COMMON_FIELD_NUMBER;
      hash = (53 * hash) + getCommon().hashCode();
    }
    hash = (37 * hash) + MESSAGE_FIELD_NUMBER;
    hash = (53 * hash) + getMessage().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage parseFrom(
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
  public static Builder newBuilder(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage prototype) {
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
   *&#64;SystemMessage
   * </pre>
   *
   * Protobuf type {@code TikTok.WebcastSystemMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TikTok.WebcastSystemMessage)
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastSystemMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastSystemMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.Builder.class);
    }

    // Construct using co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.newBuilder()
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
        getCommonFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      common_ = null;
      if (commonBuilder_ != null) {
        commonBuilder_.dispose();
        commonBuilder_ = null;
      }
      message_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastSystemMessage_descriptor;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage getDefaultInstanceForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.getDefaultInstance();
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage build() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage buildPartial() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage result = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.common_ = commonBuilder_ == null
            ? common_
            : commonBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.message_ = message_;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage) {
        return mergeFrom((co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage other) {
      if (other == co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage.getDefaultInstance()) return this;
      if (other.hasCommon()) {
        mergeCommon(other.getCommon());
      }
      if (!other.getMessage().isEmpty()) {
        message_ = other.message_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
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
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              input.readMessage(
                  getCommonFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              message_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 18
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common common_;
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder> commonBuilder_;
    /**
     * <code>.TikTok.Common common = 1;</code>
     * @return Whether the common field is set.
     */
    public boolean hasCommon() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     * @return The common.
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common getCommon() {
      if (commonBuilder_ == null) {
        return common_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : common_;
      } else {
        return commonBuilder_.getMessage();
      }
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public Builder setCommon(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common value) {
      if (commonBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        common_ = value;
      } else {
        commonBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public Builder setCommon(
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder builderForValue) {
      if (commonBuilder_ == null) {
        common_ = builderForValue.build();
      } else {
        commonBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public Builder mergeCommon(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common value) {
      if (commonBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          common_ != null &&
          common_ != co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance()) {
          getCommonBuilder().mergeFrom(value);
        } else {
          common_ = value;
        }
      } else {
        commonBuilder_.mergeFrom(value);
      }
      if (common_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public Builder clearCommon() {
      bitField0_ = (bitField0_ & ~0x00000001);
      common_ = null;
      if (commonBuilder_ != null) {
        commonBuilder_.dispose();
        commonBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder getCommonBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getCommonFieldBuilder().getBuilder();
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder getCommonOrBuilder() {
      if (commonBuilder_ != null) {
        return commonBuilder_.getMessageOrBuilder();
      } else {
        return common_ == null ?
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : common_;
      }
    }
    /**
     * <code>.TikTok.Common common = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder> 
        getCommonFieldBuilder() {
      if (commonBuilder_ == null) {
        commonBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder>(
                getCommon(),
                getParentForChildren(),
                isClean());
        common_ = null;
      }
      return commonBuilder_;
    }

    private java.lang.Object message_ = "";
    /**
     * <code>string message = 2;</code>
     * @return The message.
     */
    public java.lang.String getMessage() {
      java.lang.Object ref = message_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        message_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string message = 2;</code>
     * @return The bytes for message.
     */
    public com.google.protobuf.ByteString
        getMessageBytes() {
      java.lang.Object ref = message_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        message_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string message = 2;</code>
     * @param value The message to set.
     * @return This builder for chaining.
     */
    public Builder setMessage(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      message_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMessage() {
      message_ = getDefaultInstance().getMessage();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <code>string message = 2;</code>
     * @param value The bytes for message to set.
     * @return This builder for chaining.
     */
    public Builder setMessageBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      message_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:TikTok.WebcastSystemMessage)
  }

  // @@protoc_insertion_point(class_scope:TikTok.WebcastSystemMessage)
  private static final co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage();
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WebcastSystemMessage>
      PARSER = new com.google.protobuf.AbstractParser<WebcastSystemMessage>() {
    @java.lang.Override
    public WebcastSystemMessage parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<WebcastSystemMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WebcastSystemMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastSystemMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
