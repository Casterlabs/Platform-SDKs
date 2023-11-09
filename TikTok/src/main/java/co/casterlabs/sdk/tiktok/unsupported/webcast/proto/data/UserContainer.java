// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: data.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data;

/**
 * Protobuf type {@code TikTok.UserContainer}
 */
public final class UserContainer extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TikTok.UserContainer)
    UserContainerOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UserContainer.newBuilder() to construct.
  private UserContainer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UserContainer() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new UserContainer();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_UserContainer_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_UserContainer_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.Builder.class);
  }

  private int bitField0_;
  public static final int USER_FIELD_NUMBER = 1;
  private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User user_;
  /**
   * <code>.TikTok.User user = 1;</code>
   * @return Whether the user field is set.
   */
  @java.lang.Override
  public boolean hasUser() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>.TikTok.User user = 1;</code>
   * @return The user.
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User getUser() {
    return user_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.getDefaultInstance() : user_;
  }
  /**
   * <code>.TikTok.User user = 1;</code>
   */
  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder getUserOrBuilder() {
    return user_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.getDefaultInstance() : user_;
  }

  public static final int DATA1_FIELD_NUMBER = 2;
  private int data1_ = 0;
  /**
   * <code>uint32 data1 = 2;</code>
   * @return The data1.
   */
  @java.lang.Override
  public int getData1() {
    return data1_;
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
      output.writeMessage(1, getUser());
    }
    if (data1_ != 0) {
      output.writeUInt32(2, data1_);
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
        .computeMessageSize(1, getUser());
    }
    if (data1_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, data1_);
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
    if (!(obj instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer)) {
      return super.equals(obj);
    }
    co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer other = (co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer) obj;

    if (hasUser() != other.hasUser()) return false;
    if (hasUser()) {
      if (!getUser()
          .equals(other.getUser())) return false;
    }
    if (getData1()
        != other.getData1()) return false;
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
    if (hasUser()) {
      hash = (37 * hash) + USER_FIELD_NUMBER;
      hash = (53 * hash) + getUser().hashCode();
    }
    hash = (37 * hash) + DATA1_FIELD_NUMBER;
    hash = (53 * hash) + getData1();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer parseFrom(
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
  public static Builder newBuilder(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer prototype) {
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
   * Protobuf type {@code TikTok.UserContainer}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TikTok.UserContainer)
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainerOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_UserContainer_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_UserContainer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.Builder.class);
    }

    // Construct using co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.newBuilder()
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
        getUserFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      user_ = null;
      if (userBuilder_ != null) {
        userBuilder_.dispose();
        userBuilder_ = null;
      }
      data1_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_UserContainer_descriptor;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer getDefaultInstanceForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.getDefaultInstance();
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer build() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer buildPartial() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer result = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.user_ = userBuilder_ == null
            ? user_
            : userBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.data1_ = data1_;
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
      if (other instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer) {
        return mergeFrom((co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer other) {
      if (other == co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer.getDefaultInstance()) return this;
      if (other.hasUser()) {
        mergeUser(other.getUser());
      }
      if (other.getData1() != 0) {
        setData1(other.getData1());
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
                  getUserFieldBuilder().getBuilder(),
                  extensionRegistry);
              bitField0_ |= 0x00000001;
              break;
            } // case 10
            case 16: {
              data1_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
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

    private co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User user_;
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder> userBuilder_;
    /**
     * <code>.TikTok.User user = 1;</code>
     * @return Whether the user field is set.
     */
    public boolean hasUser() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     * @return The user.
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User getUser() {
      if (userBuilder_ == null) {
        return user_ == null ? co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.getDefaultInstance() : user_;
      } else {
        return userBuilder_.getMessage();
      }
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public Builder setUser(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User value) {
      if (userBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        user_ = value;
      } else {
        userBuilder_.setMessage(value);
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public Builder setUser(
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.Builder builderForValue) {
      if (userBuilder_ == null) {
        user_ = builderForValue.build();
      } else {
        userBuilder_.setMessage(builderForValue.build());
      }
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public Builder mergeUser(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User value) {
      if (userBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0) &&
          user_ != null &&
          user_ != co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.getDefaultInstance()) {
          getUserBuilder().mergeFrom(value);
        } else {
          user_ = value;
        }
      } else {
        userBuilder_.mergeFrom(value);
      }
      if (user_ != null) {
        bitField0_ |= 0x00000001;
        onChanged();
      }
      return this;
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public Builder clearUser() {
      bitField0_ = (bitField0_ & ~0x00000001);
      user_ = null;
      if (userBuilder_ != null) {
        userBuilder_.dispose();
        userBuilder_ = null;
      }
      onChanged();
      return this;
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.Builder getUserBuilder() {
      bitField0_ |= 0x00000001;
      onChanged();
      return getUserFieldBuilder().getBuilder();
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder getUserOrBuilder() {
      if (userBuilder_ != null) {
        return userBuilder_.getMessageOrBuilder();
      } else {
        return user_ == null ?
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.getDefaultInstance() : user_;
      }
    }
    /**
     * <code>.TikTok.User user = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder> 
        getUserFieldBuilder() {
      if (userBuilder_ == null) {
        userBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User.Builder, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder>(
                getUser(),
                getParentForChildren(),
                isClean());
        user_ = null;
      }
      return userBuilder_;
    }

    private int data1_ ;
    /**
     * <code>uint32 data1 = 2;</code>
     * @return The data1.
     */
    @java.lang.Override
    public int getData1() {
      return data1_;
    }
    /**
     * <code>uint32 data1 = 2;</code>
     * @param value The data1 to set.
     * @return This builder for chaining.
     */
    public Builder setData1(int value) {

      data1_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data1 = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearData1() {
      bitField0_ = (bitField0_ & ~0x00000002);
      data1_ = 0;
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


    // @@protoc_insertion_point(builder_scope:TikTok.UserContainer)
  }

  // @@protoc_insertion_point(class_scope:TikTok.UserContainer)
  private static final co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer();
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UserContainer>
      PARSER = new com.google.protobuf.AbstractParser<UserContainer>() {
    @java.lang.Override
    public UserContainer parsePartialFrom(
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

  public static com.google.protobuf.Parser<UserContainer> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UserContainer> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
