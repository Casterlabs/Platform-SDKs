// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: data.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data;

/**
 * <pre>
 * Container for uint-Data
 * </pre>
 *
 * Protobuf type {@code TikTok.DataContainer}
 */
public final class DataContainer extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:TikTok.DataContainer)
    DataContainerOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DataContainer.newBuilder() to construct.
  private DataContainer(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DataContainer() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DataContainer();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_DataContainer_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_DataContainer_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.Builder.class);
  }

  public static final int DATA1_FIELD_NUMBER = 1;
  private long data1_ = 0L;
  /**
   * <code>uint64 data1 = 1;</code>
   * @return The data1.
   */
  @java.lang.Override
  public long getData1() {
    return data1_;
  }

  public static final int DATA2_FIELD_NUMBER = 2;
  private int data2_ = 0;
  /**
   * <code>uint32 data2 = 2;</code>
   * @return The data2.
   */
  @java.lang.Override
  public int getData2() {
    return data2_;
  }

  public static final int DATA3_FIELD_NUMBER = 3;
  private int data3_ = 0;
  /**
   * <code>uint32 data3 = 3;</code>
   * @return The data3.
   */
  @java.lang.Override
  public int getData3() {
    return data3_;
  }

  public static final int DATA4_FIELD_NUMBER = 4;
  private int data4_ = 0;
  /**
   * <code>uint32 data4 = 4;</code>
   * @return The data4.
   */
  @java.lang.Override
  public int getData4() {
    return data4_;
  }

  public static final int DATA5_FIELD_NUMBER = 5;
  private int data5_ = 0;
  /**
   * <code>uint32 data5 = 5;</code>
   * @return The data5.
   */
  @java.lang.Override
  public int getData5() {
    return data5_;
  }

  public static final int DATA6_FIELD_NUMBER = 6;
  private int data6_ = 0;
  /**
   * <code>uint32 data6 = 6;</code>
   * @return The data6.
   */
  @java.lang.Override
  public int getData6() {
    return data6_;
  }

  public static final int DATA7_FIELD_NUMBER = 7;
  private int data7_ = 0;
  /**
   * <code>uint32 data7 = 7;</code>
   * @return The data7.
   */
  @java.lang.Override
  public int getData7() {
    return data7_;
  }

  public static final int DATA8_FIELD_NUMBER = 8;
  private int data8_ = 0;
  /**
   * <code>uint32 data8 = 8;</code>
   * @return The data8.
   */
  @java.lang.Override
  public int getData8() {
    return data8_;
  }

  public static final int DATA9_FIELD_NUMBER = 9;
  private int data9_ = 0;
  /**
   * <code>uint32 data9 = 9;</code>
   * @return The data9.
   */
  @java.lang.Override
  public int getData9() {
    return data9_;
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
    if (data1_ != 0L) {
      output.writeUInt64(1, data1_);
    }
    if (data2_ != 0) {
      output.writeUInt32(2, data2_);
    }
    if (data3_ != 0) {
      output.writeUInt32(3, data3_);
    }
    if (data4_ != 0) {
      output.writeUInt32(4, data4_);
    }
    if (data5_ != 0) {
      output.writeUInt32(5, data5_);
    }
    if (data6_ != 0) {
      output.writeUInt32(6, data6_);
    }
    if (data7_ != 0) {
      output.writeUInt32(7, data7_);
    }
    if (data8_ != 0) {
      output.writeUInt32(8, data8_);
    }
    if (data9_ != 0) {
      output.writeUInt32(9, data9_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (data1_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(1, data1_);
    }
    if (data2_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(2, data2_);
    }
    if (data3_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(3, data3_);
    }
    if (data4_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(4, data4_);
    }
    if (data5_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(5, data5_);
    }
    if (data6_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(6, data6_);
    }
    if (data7_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(7, data7_);
    }
    if (data8_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(8, data8_);
    }
    if (data9_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt32Size(9, data9_);
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
    if (!(obj instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer)) {
      return super.equals(obj);
    }
    co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer other = (co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer) obj;

    if (getData1()
        != other.getData1()) return false;
    if (getData2()
        != other.getData2()) return false;
    if (getData3()
        != other.getData3()) return false;
    if (getData4()
        != other.getData4()) return false;
    if (getData5()
        != other.getData5()) return false;
    if (getData6()
        != other.getData6()) return false;
    if (getData7()
        != other.getData7()) return false;
    if (getData8()
        != other.getData8()) return false;
    if (getData9()
        != other.getData9()) return false;
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
    hash = (37 * hash) + DATA1_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getData1());
    hash = (37 * hash) + DATA2_FIELD_NUMBER;
    hash = (53 * hash) + getData2();
    hash = (37 * hash) + DATA3_FIELD_NUMBER;
    hash = (53 * hash) + getData3();
    hash = (37 * hash) + DATA4_FIELD_NUMBER;
    hash = (53 * hash) + getData4();
    hash = (37 * hash) + DATA5_FIELD_NUMBER;
    hash = (53 * hash) + getData5();
    hash = (37 * hash) + DATA6_FIELD_NUMBER;
    hash = (53 * hash) + getData6();
    hash = (37 * hash) + DATA7_FIELD_NUMBER;
    hash = (53 * hash) + getData7();
    hash = (37 * hash) + DATA8_FIELD_NUMBER;
    hash = (53 * hash) + getData8();
    hash = (37 * hash) + DATA9_FIELD_NUMBER;
    hash = (53 * hash) + getData9();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer parseFrom(
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
  public static Builder newBuilder(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer prototype) {
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
   * Container for uint-Data
   * </pre>
   *
   * Protobuf type {@code TikTok.DataContainer}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:TikTok.DataContainer)
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainerOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_DataContainer_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_DataContainer_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.class, co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.Builder.class);
    }

    // Construct using co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      data1_ = 0L;
      data2_ = 0;
      data3_ = 0;
      data4_ = 0;
      data5_ = 0;
      data6_ = 0;
      data7_ = 0;
      data8_ = 0;
      data9_ = 0;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Data.internal_static_TikTok_DataContainer_descriptor;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer getDefaultInstanceForType() {
      return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.getDefaultInstance();
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer build() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer buildPartial() {
      co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer result = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.data1_ = data1_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.data2_ = data2_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.data3_ = data3_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.data4_ = data4_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.data5_ = data5_;
      }
      if (((from_bitField0_ & 0x00000020) != 0)) {
        result.data6_ = data6_;
      }
      if (((from_bitField0_ & 0x00000040) != 0)) {
        result.data7_ = data7_;
      }
      if (((from_bitField0_ & 0x00000080) != 0)) {
        result.data8_ = data8_;
      }
      if (((from_bitField0_ & 0x00000100) != 0)) {
        result.data9_ = data9_;
      }
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
      if (other instanceof co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer) {
        return mergeFrom((co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer other) {
      if (other == co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer.getDefaultInstance()) return this;
      if (other.getData1() != 0L) {
        setData1(other.getData1());
      }
      if (other.getData2() != 0) {
        setData2(other.getData2());
      }
      if (other.getData3() != 0) {
        setData3(other.getData3());
      }
      if (other.getData4() != 0) {
        setData4(other.getData4());
      }
      if (other.getData5() != 0) {
        setData5(other.getData5());
      }
      if (other.getData6() != 0) {
        setData6(other.getData6());
      }
      if (other.getData7() != 0) {
        setData7(other.getData7());
      }
      if (other.getData8() != 0) {
        setData8(other.getData8());
      }
      if (other.getData9() != 0) {
        setData9(other.getData9());
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
            case 8: {
              data1_ = input.readUInt64();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              data2_ = input.readUInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              data3_ = input.readUInt32();
              bitField0_ |= 0x00000004;
              break;
            } // case 24
            case 32: {
              data4_ = input.readUInt32();
              bitField0_ |= 0x00000008;
              break;
            } // case 32
            case 40: {
              data5_ = input.readUInt32();
              bitField0_ |= 0x00000010;
              break;
            } // case 40
            case 48: {
              data6_ = input.readUInt32();
              bitField0_ |= 0x00000020;
              break;
            } // case 48
            case 56: {
              data7_ = input.readUInt32();
              bitField0_ |= 0x00000040;
              break;
            } // case 56
            case 64: {
              data8_ = input.readUInt32();
              bitField0_ |= 0x00000080;
              break;
            } // case 64
            case 72: {
              data9_ = input.readUInt32();
              bitField0_ |= 0x00000100;
              break;
            } // case 72
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

    private long data1_ ;
    /**
     * <code>uint64 data1 = 1;</code>
     * @return The data1.
     */
    @java.lang.Override
    public long getData1() {
      return data1_;
    }
    /**
     * <code>uint64 data1 = 1;</code>
     * @param value The data1 to set.
     * @return This builder for chaining.
     */
    public Builder setData1(long value) {

      data1_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>uint64 data1 = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearData1() {
      bitField0_ = (bitField0_ & ~0x00000001);
      data1_ = 0L;
      onChanged();
      return this;
    }

    private int data2_ ;
    /**
     * <code>uint32 data2 = 2;</code>
     * @return The data2.
     */
    @java.lang.Override
    public int getData2() {
      return data2_;
    }
    /**
     * <code>uint32 data2 = 2;</code>
     * @param value The data2 to set.
     * @return This builder for chaining.
     */
    public Builder setData2(int value) {

      data2_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data2 = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearData2() {
      bitField0_ = (bitField0_ & ~0x00000002);
      data2_ = 0;
      onChanged();
      return this;
    }

    private int data3_ ;
    /**
     * <code>uint32 data3 = 3;</code>
     * @return The data3.
     */
    @java.lang.Override
    public int getData3() {
      return data3_;
    }
    /**
     * <code>uint32 data3 = 3;</code>
     * @param value The data3 to set.
     * @return This builder for chaining.
     */
    public Builder setData3(int value) {

      data3_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data3 = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearData3() {
      bitField0_ = (bitField0_ & ~0x00000004);
      data3_ = 0;
      onChanged();
      return this;
    }

    private int data4_ ;
    /**
     * <code>uint32 data4 = 4;</code>
     * @return The data4.
     */
    @java.lang.Override
    public int getData4() {
      return data4_;
    }
    /**
     * <code>uint32 data4 = 4;</code>
     * @param value The data4 to set.
     * @return This builder for chaining.
     */
    public Builder setData4(int value) {

      data4_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data4 = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearData4() {
      bitField0_ = (bitField0_ & ~0x00000008);
      data4_ = 0;
      onChanged();
      return this;
    }

    private int data5_ ;
    /**
     * <code>uint32 data5 = 5;</code>
     * @return The data5.
     */
    @java.lang.Override
    public int getData5() {
      return data5_;
    }
    /**
     * <code>uint32 data5 = 5;</code>
     * @param value The data5 to set.
     * @return This builder for chaining.
     */
    public Builder setData5(int value) {

      data5_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data5 = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearData5() {
      bitField0_ = (bitField0_ & ~0x00000010);
      data5_ = 0;
      onChanged();
      return this;
    }

    private int data6_ ;
    /**
     * <code>uint32 data6 = 6;</code>
     * @return The data6.
     */
    @java.lang.Override
    public int getData6() {
      return data6_;
    }
    /**
     * <code>uint32 data6 = 6;</code>
     * @param value The data6 to set.
     * @return This builder for chaining.
     */
    public Builder setData6(int value) {

      data6_ = value;
      bitField0_ |= 0x00000020;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data6 = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearData6() {
      bitField0_ = (bitField0_ & ~0x00000020);
      data6_ = 0;
      onChanged();
      return this;
    }

    private int data7_ ;
    /**
     * <code>uint32 data7 = 7;</code>
     * @return The data7.
     */
    @java.lang.Override
    public int getData7() {
      return data7_;
    }
    /**
     * <code>uint32 data7 = 7;</code>
     * @param value The data7 to set.
     * @return This builder for chaining.
     */
    public Builder setData7(int value) {

      data7_ = value;
      bitField0_ |= 0x00000040;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data7 = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearData7() {
      bitField0_ = (bitField0_ & ~0x00000040);
      data7_ = 0;
      onChanged();
      return this;
    }

    private int data8_ ;
    /**
     * <code>uint32 data8 = 8;</code>
     * @return The data8.
     */
    @java.lang.Override
    public int getData8() {
      return data8_;
    }
    /**
     * <code>uint32 data8 = 8;</code>
     * @param value The data8 to set.
     * @return This builder for chaining.
     */
    public Builder setData8(int value) {

      data8_ = value;
      bitField0_ |= 0x00000080;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data8 = 8;</code>
     * @return This builder for chaining.
     */
    public Builder clearData8() {
      bitField0_ = (bitField0_ & ~0x00000080);
      data8_ = 0;
      onChanged();
      return this;
    }

    private int data9_ ;
    /**
     * <code>uint32 data9 = 9;</code>
     * @return The data9.
     */
    @java.lang.Override
    public int getData9() {
      return data9_;
    }
    /**
     * <code>uint32 data9 = 9;</code>
     * @param value The data9 to set.
     * @return This builder for chaining.
     */
    public Builder setData9(int value) {

      data9_ = value;
      bitField0_ |= 0x00000100;
      onChanged();
      return this;
    }
    /**
     * <code>uint32 data9 = 9;</code>
     * @return This builder for chaining.
     */
    public Builder clearData9() {
      bitField0_ = (bitField0_ & ~0x00000100);
      data9_ = 0;
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


    // @@protoc_insertion_point(builder_scope:TikTok.DataContainer)
  }

  // @@protoc_insertion_point(class_scope:TikTok.DataContainer)
  private static final co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer();
  }

  public static co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DataContainer>
      PARSER = new com.google.protobuf.AbstractParser<DataContainer>() {
    @java.lang.Override
    public DataContainer parsePartialFrom(
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

  public static com.google.protobuf.Parser<DataContainer> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DataContainer> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.DataContainer getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

