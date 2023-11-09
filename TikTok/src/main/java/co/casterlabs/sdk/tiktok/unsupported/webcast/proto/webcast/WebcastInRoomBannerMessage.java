// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

/**
 * <pre>
 *&#64;WebcastInRoomBannerMessage
 * </pre>
 *
 * Protobuf type {@code TikTok.WebcastInRoomBannerMessage}
 */
public final class WebcastInRoomBannerMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TikTok.WebcastInRoomBannerMessage)
    WebcastInRoomBannerMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WebcastInRoomBannerMessage.newBuilder() to construct.
  private WebcastInRoomBannerMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WebcastInRoomBannerMessage() {
    json_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new WebcastInRoomBannerMessage();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastInRoomBannerMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastInRoomBannerMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.Builder.class);
  }

  private int bitField0_;
  public static final int HEADER_FIELD_NUMBER = 1;
  private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common header_;
  /**
   * <code>.TikTok.Common header = 1;</code>
   * @return Whether the header field is set.
   */
  @java.lang.Override
  public boolean hasHeader() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.TikTok.Common header = 1;</code>
   * @return The header.
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common getHeader() {
    return header_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : header_;
  }
  /**
   * <code>.TikTok.Common header = 1;</code>
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder getHeaderOrBuilder() {
    return header_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : header_;
  }

  public static final int JSON_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private volatile java.lang.Object json_ = "";
  /**
   * <pre>
   * Json-Data for BannerMessage
   * </pre>
   *
   * <code>string json = 2;</code>
   * @return The json.
   */
  @java.lang.Override
  public java.lang.String getJson() {
    java.lang.Object ref = json_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      json_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * Json-Data for BannerMessage
   * </pre>
   *
   * <code>string json = 2;</code>
   * @return The bytes for json.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getJsonBytes() {
    java.lang.Object ref = json_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      json_ = b;
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
      output.writeMessage(1, getHeader());
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(json_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, json_);
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
        .computeMessageSize(1, getHeader());
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(json_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, json_);
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
    if (!(obj instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage)) {
      return super.equals(obj);
    }
    co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage other = (co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage) obj;

    if (hasHeader() != other.hasHeader()) return false;
    if (hasHeader()) {
      if (!getHeader()
          .equals(other.getHeader())) return false;
    }
    if (!getJson()
        .equals(other.getJson())) return false;
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
    if (hasHeader()) {
      hash = (37 * hash) + HEADER_FIELD_NUMBER;
      hash = (53 * hash) + getHeader().hashCode();
    }
    hash = (37 * hash) + JSON_FIELD_NUMBER;
    hash = (53 * hash) + getJson().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage parseFrom(
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
  public static Builder newBuilder(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage prototype) {
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
   *&#64;WebcastInRoomBannerMessage
   * </pre>
   *
   * Protobuf type {@code TikTok.WebcastInRoomBannerMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TikTok.WebcastInRoomBannerMessage)
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastInRoomBannerMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastInRoomBannerMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.Builder.class);
    }

    // Construct using co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.newBuilder()
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
        getHeaderFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      header_ = null;
      if (headerBuilder_ != null) {
        headerBuilder_.dispose();
        headerBuilder_ = null;
      }
      json_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastInRoomBannerMessage_descriptor;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage getDefaultInstanceForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.getDefaultInstance();
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage build() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage buildPartial() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage result = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.header_ = headerBuilder_ == null
            ? header_
            : headerBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.json_ = json_;
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
      if (other instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage) {
        return mergeFrom((co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage other) {
      if (other == co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage.getDefaultInstance()) return this;
      if (other.hasHeader()) {
        mergeHeader(other.getHeader());
      }
      if (!other.getJson().isEmpty()) {
        json_ = other.json_;
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
                  getHeaderFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 18: {
              json_ = input.readStringRequireUtf8();
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

    private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common header_;
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder> headerBuilder_;
    /**
     * <code>.TikTok.Common header = 1;</code>
     * @return Whether the header field is set.
     */
    public boolean hasHeader() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     * @return The header.
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common getHeader() {
      if (headerBuilder_ == null) {
        return header_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : header_;
      } else {
        return headerBuilder_.getMessage();
      }
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public Builder setHeader(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common value) {
      if (headerBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        header_ = value;
      } else {
        headerBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public Builder setHeader(
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder builderForValue) {
      if (headerBuilder_ == null) {
        header_ = builderForValue.build();
      } else {
        headerBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public Builder mergeHeader(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common value) {
      if (headerBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          header_ != null &&
          header_ != co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance()) {
          getHeaderBuilder().mergeFrom(value);
        } else {
          header_ = value;
        }
      } else {
        headerBuilder_.mergeFrom(value);
      }
      if (header_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public Builder clearHeader() {
      bitField0_ = (bitField0_ & ~0x00000001);
      header_ = null;
      if (headerBuilder_ != null) {
        headerBuilder_.dispose();
        headerBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder getHeaderBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getHeaderFieldBuilder().getBuilder();
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder getHeaderOrBuilder() {
      if (headerBuilder_ != null) {
        return headerBuilder_.getMessageOrBuilder();
      } else {
        return header_ == null ?
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.getDefaultInstance() : header_;
      }
    }
    /**
     * <code>.TikTok.Common header = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder> 
        getHeaderFieldBuilder() {
      if (headerBuilder_ == null) {
        headerBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder>(
                getHeader(),
                getParentForChildren(),
                isClean());
        header_ = null;
      }
      return headerBuilder_;
    }

    private java.lang.Object json_ = "";
    /**
     * <pre>
     * Json-Data for BannerMessage
     * </pre>
     *
     * <code>string json = 2;</code>
     * @return The json.
     */
    public java.lang.String getJson() {
      java.lang.Object ref = json_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        json_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * Json-Data for BannerMessage
     * </pre>
     *
     * <code>string json = 2;</code>
     * @return The bytes for json.
     */
    public com.google.protobuf.ByteString
        getJsonBytes() {
      java.lang.Object ref = json_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        json_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * Json-Data for BannerMessage
     * </pre>
     *
     * <code>string json = 2;</code>
     * @param value The json to set.
     * @return This builder for chaining.
     */
    public Builder setJson(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      json_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Json-Data for BannerMessage
     * </pre>
     *
     * <code>string json = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearJson() {
      json_ = getDefaultInstance().getJson();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     * Json-Data for BannerMessage
     * </pre>
     *
     * <code>string json = 2;</code>
     * @param value The bytes for json to set.
     * @return This builder for chaining.
     */
    public Builder setJsonBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      json_ = value;
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


    // @@protoc_insertion_point(builder_scope:TikTok.WebcastInRoomBannerMessage)
  }

  // @@protoc_insertion_point(class_scope:TikTok.WebcastInRoomBannerMessage)
  private static final co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage();
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WebcastInRoomBannerMessage>
      PARSER = new com.google.protobuf.AbstractParser<WebcastInRoomBannerMessage>() {
    @java.lang.Override
    public WebcastInRoomBannerMessage parsePartialFrom(
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

  public static com.google.protobuf.Parser<WebcastInRoomBannerMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WebcastInRoomBannerMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastInRoomBannerMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
