// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

/**
 * <pre>
 * Message related to Chat-moderation?
 *&#64;WebcastImDeleteMessage
 * </pre>
 *
 * Protobuf type {@code TikTok.WebcastImDeleteMessage}
 */
public final class WebcastImDeleteMessage extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TikTok.WebcastImDeleteMessage)
    WebcastImDeleteMessageOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WebcastImDeleteMessage.newBuilder() to construct.
  private WebcastImDeleteMessage(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WebcastImDeleteMessage() {
    deleteMsgIdsList_ = emptyLongList();
    deleteUserIdsList_ = emptyLongList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new WebcastImDeleteMessage();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastImDeleteMessage_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastImDeleteMessage_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.Builder.class);
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

  public static final int DELETEMSGIDSLIST_FIELD_NUMBER = 2;
  @SuppressWarnings("serial")
  private com.google.protobuf.Internal.LongList deleteMsgIdsList_ =
      emptyLongList();
  /**
   * <code>repeated int64 deleteMsgIdsList = 2;</code>
   * @return A list containing the deleteMsgIdsList.
   */
  @java.lang.Override
  public java.util.List<java.lang.Long>
      getDeleteMsgIdsListList() {
    return deleteMsgIdsList_;
  }
  /**
   * <code>repeated int64 deleteMsgIdsList = 2;</code>
   * @return The count of deleteMsgIdsList.
   */
  public int getDeleteMsgIdsListCount() {
    return deleteMsgIdsList_.size();
  }
  /**
   * <code>repeated int64 deleteMsgIdsList = 2;</code>
   * @param index The index of the element to return.
   * @return The deleteMsgIdsList at the given index.
   */
  public long getDeleteMsgIdsList(int index) {
    return deleteMsgIdsList_.getLong(index);
  }
  private int deleteMsgIdsListMemoizedSerializedSize = -1;

  public static final int DELETEUSERIDSLIST_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private com.google.protobuf.Internal.LongList deleteUserIdsList_ =
      emptyLongList();
  /**
   * <code>repeated int64 deleteUserIdsList = 3;</code>
   * @return A list containing the deleteUserIdsList.
   */
  @java.lang.Override
  public java.util.List<java.lang.Long>
      getDeleteUserIdsListList() {
    return deleteUserIdsList_;
  }
  /**
   * <code>repeated int64 deleteUserIdsList = 3;</code>
   * @return The count of deleteUserIdsList.
   */
  public int getDeleteUserIdsListCount() {
    return deleteUserIdsList_.size();
  }
  /**
   * <code>repeated int64 deleteUserIdsList = 3;</code>
   * @param index The index of the element to return.
   * @return The deleteUserIdsList at the given index.
   */
  public long getDeleteUserIdsList(int index) {
    return deleteUserIdsList_.getLong(index);
  }
  private int deleteUserIdsListMemoizedSerializedSize = -1;

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
    getSerializedSize();
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeMessage(1, getCommon());
    }
    if (getDeleteMsgIdsListList().size() > 0) {
      output.writeUInt32NoTag(18);
      output.writeUInt32NoTag(deleteMsgIdsListMemoizedSerializedSize);
    }
    for (int i = 0; i < deleteMsgIdsList_.size(); i++) {
      output.writeInt64NoTag(deleteMsgIdsList_.getLong(i));
    }
    if (getDeleteUserIdsListList().size() > 0) {
      output.writeUInt32NoTag(26);
      output.writeUInt32NoTag(deleteUserIdsListMemoizedSerializedSize);
    }
    for (int i = 0; i < deleteUserIdsList_.size(); i++) {
      output.writeInt64NoTag(deleteUserIdsList_.getLong(i));
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
    {
      int dataSize = 0;
      for (int i = 0; i < deleteMsgIdsList_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt64SizeNoTag(deleteMsgIdsList_.getLong(i));
      }
      size += dataSize;
      if (!getDeleteMsgIdsListList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      deleteMsgIdsListMemoizedSerializedSize = dataSize;
    }
    {
      int dataSize = 0;
      for (int i = 0; i < deleteUserIdsList_.size(); i++) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeInt64SizeNoTag(deleteUserIdsList_.getLong(i));
      }
      size += dataSize;
      if (!getDeleteUserIdsListList().isEmpty()) {
        size += 1;
        size += com.google.protobuf.CodedOutputStream
            .computeInt32SizeNoTag(dataSize);
      }
      deleteUserIdsListMemoizedSerializedSize = dataSize;
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
    if (!(obj instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage)) {
      return super.equals(obj);
    }
    co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage other = (co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage) obj;

    if (hasCommon() != other.hasCommon()) return false;
    if (hasCommon()) {
      if (!getCommon()
          .equals(other.getCommon())) return false;
    }
    if (!getDeleteMsgIdsListList()
        .equals(other.getDeleteMsgIdsListList())) return false;
    if (!getDeleteUserIdsListList()
        .equals(other.getDeleteUserIdsListList())) return false;
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
    if (getDeleteMsgIdsListCount() > 0) {
      hash = (37 * hash) + DELETEMSGIDSLIST_FIELD_NUMBER;
      hash = (53 * hash) + getDeleteMsgIdsListList().hashCode();
    }
    if (getDeleteUserIdsListCount() > 0) {
      hash = (37 * hash) + DELETEUSERIDSLIST_FIELD_NUMBER;
      hash = (53 * hash) + getDeleteUserIdsListList().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage parseFrom(
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
  public static Builder newBuilder(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage prototype) {
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
   * Message related to Chat-moderation?
   *&#64;WebcastImDeleteMessage
   * </pre>
   *
   * Protobuf type {@code TikTok.WebcastImDeleteMessage}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TikTok.WebcastImDeleteMessage)
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessageOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastImDeleteMessage_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastImDeleteMessage_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.Builder.class);
    }

    // Construct using co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.newBuilder()
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
      deleteMsgIdsList_ = emptyLongList();
      deleteUserIdsList_ = emptyLongList();
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.Webcast.internal_static_TikTok_WebcastImDeleteMessage_descriptor;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage getDefaultInstanceForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.getDefaultInstance();
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage build() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage buildPartial() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage result = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage result) {
      int from_bitField0_ = bitField0_;
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.common_ = commonBuilder_ == null
            ? common_
            : commonBuilder_.build();
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        deleteMsgIdsList_.makeImmutable();
        result.deleteMsgIdsList_ = deleteMsgIdsList_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        deleteUserIdsList_.makeImmutable();
        result.deleteUserIdsList_ = deleteUserIdsList_;
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
      if (other instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage) {
        return mergeFrom((co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage other) {
      if (other == co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage.getDefaultInstance()) return this;
      if (other.hasCommon()) {
        mergeCommon(other.getCommon());
      }
      if (!other.deleteMsgIdsList_.isEmpty()) {
        if (deleteMsgIdsList_.isEmpty()) {
          deleteMsgIdsList_ = other.deleteMsgIdsList_;
          deleteMsgIdsList_.makeImmutable();
          bitField0_ |= 0x00000002;
        } else {
          ensureDeleteMsgIdsListIsMutable();
          deleteMsgIdsList_.addAll(other.deleteMsgIdsList_);
        }
        onChanged();
      }
      if (!other.deleteUserIdsList_.isEmpty()) {
        if (deleteUserIdsList_.isEmpty()) {
          deleteUserIdsList_ = other.deleteUserIdsList_;
          deleteUserIdsList_.makeImmutable();
          bitField0_ |= 0x00000004;
        } else {
          ensureDeleteUserIdsListIsMutable();
          deleteUserIdsList_.addAll(other.deleteUserIdsList_);
        }
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
            case 16: {
              long v = input.readInt64();
              ensureDeleteMsgIdsListIsMutable();
              deleteMsgIdsList_.addLong(v);
              break;
            } // case 16
            case 18: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              ensureDeleteMsgIdsListIsMutable();
              while (input.getBytesUntilLimit() > 0) {
                deleteMsgIdsList_.addLong(input.readInt64());
              }
              input.popLimit(limit);
              break;
            } // case 18
            case 24: {
              long v = input.readInt64();
              ensureDeleteUserIdsListIsMutable();
              deleteUserIdsList_.addLong(v);
              break;
            } // case 24
            case 26: {
              int length = input.readRawVarint32();
              int limit = input.pushLimit(length);
              ensureDeleteUserIdsListIsMutable();
              while (input.getBytesUntilLimit() > 0) {
                deleteUserIdsList_.addLong(input.readInt64());
              }
              input.popLimit(limit);
              break;
            } // case 26
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

    private com.google.protobuf.Internal.LongList deleteMsgIdsList_ = emptyLongList();
    private void ensureDeleteMsgIdsListIsMutable() {
      if (!deleteMsgIdsList_.isModifiable()) {
        deleteMsgIdsList_ = makeMutableCopy(deleteMsgIdsList_);
      }
      bitField0_ |= 0x00000002;
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @return A list containing the deleteMsgIdsList.
     */
    public java.util.List<java.lang.Long>
        getDeleteMsgIdsListList() {
      deleteMsgIdsList_.makeImmutable();
      return deleteMsgIdsList_;
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @return The count of deleteMsgIdsList.
     */
    public int getDeleteMsgIdsListCount() {
      return deleteMsgIdsList_.size();
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @param index The index of the element to return.
     * @return The deleteMsgIdsList at the given index.
     */
    public long getDeleteMsgIdsList(int index) {
      return deleteMsgIdsList_.getLong(index);
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @param index The index to set the value at.
     * @param value The deleteMsgIdsList to set.
     * @return This builder for chaining.
     */
    public Builder setDeleteMsgIdsList(
        int index, long value) {

      ensureDeleteMsgIdsListIsMutable();
      deleteMsgIdsList_.setLong(index, value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @param value The deleteMsgIdsList to add.
     * @return This builder for chaining.
     */
    public Builder addDeleteMsgIdsList(long value) {

      ensureDeleteMsgIdsListIsMutable();
      deleteMsgIdsList_.addLong(value);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @param values The deleteMsgIdsList to add.
     * @return This builder for chaining.
     */
    public Builder addAllDeleteMsgIdsList(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureDeleteMsgIdsListIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, deleteMsgIdsList_);
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteMsgIdsList = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearDeleteMsgIdsList() {
      deleteMsgIdsList_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }

    private com.google.protobuf.Internal.LongList deleteUserIdsList_ = emptyLongList();
    private void ensureDeleteUserIdsListIsMutable() {
      if (!deleteUserIdsList_.isModifiable()) {
        deleteUserIdsList_ = makeMutableCopy(deleteUserIdsList_);
      }
      bitField0_ |= 0x00000004;
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @return A list containing the deleteUserIdsList.
     */
    public java.util.List<java.lang.Long>
        getDeleteUserIdsListList() {
      deleteUserIdsList_.makeImmutable();
      return deleteUserIdsList_;
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @return The count of deleteUserIdsList.
     */
    public int getDeleteUserIdsListCount() {
      return deleteUserIdsList_.size();
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @param index The index of the element to return.
     * @return The deleteUserIdsList at the given index.
     */
    public long getDeleteUserIdsList(int index) {
      return deleteUserIdsList_.getLong(index);
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @param index The index to set the value at.
     * @param value The deleteUserIdsList to set.
     * @return This builder for chaining.
     */
    public Builder setDeleteUserIdsList(
        int index, long value) {

      ensureDeleteUserIdsListIsMutable();
      deleteUserIdsList_.setLong(index, value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @param value The deleteUserIdsList to add.
     * @return This builder for chaining.
     */
    public Builder addDeleteUserIdsList(long value) {

      ensureDeleteUserIdsListIsMutable();
      deleteUserIdsList_.addLong(value);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @param values The deleteUserIdsList to add.
     * @return This builder for chaining.
     */
    public Builder addAllDeleteUserIdsList(
        java.lang.Iterable<? extends java.lang.Long> values) {
      ensureDeleteUserIdsListIsMutable();
      com.google.protobuf.AbstractMessageLite.Builder.addAll(
          values, deleteUserIdsList_);
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>repeated int64 deleteUserIdsList = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearDeleteUserIdsList() {
      deleteUserIdsList_ = emptyLongList();
      bitField0_ = (bitField0_ & ~0x00000004);
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


    // @@protoc_insertion_point(builder_scope:TikTok.WebcastImDeleteMessage)
  }

  // @@protoc_insertion_point(class_scope:TikTok.WebcastImDeleteMessage)
  private static final co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage();
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WebcastImDeleteMessage>
      PARSER = new com.google.protobuf.AbstractParser<WebcastImDeleteMessage>() {
    @java.lang.Override
    public WebcastImDeleteMessage parsePartialFrom(
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

  public static com.google.protobuf.Parser<WebcastImDeleteMessage> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WebcastImDeleteMessage> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastImDeleteMessage getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

