// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums;

/**
 * Protobuf enum {@code TikTok.LinkmicStatus}
 */
public enum LinkmicStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>Disable = 0;</code>
   */
  Disable(0),
  /**
   * <code>Enable = 1;</code>
   */
  Enable(1),
  /**
   * <code>Just_Following = 2;</code>
   */
  Just_Following(2),
  /**
   * <code>Multi_Linking = 3;</code>
   */
  Multi_Linking(3),
  /**
   * <code>Multi_Linking_Only_Following = 4;</code>
   */
  Multi_Linking_Only_Following(4),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>Disable = 0;</code>
   */
  public static final int Disable_VALUE = 0;
  /**
   * <code>Enable = 1;</code>
   */
  public static final int Enable_VALUE = 1;
  /**
   * <code>Just_Following = 2;</code>
   */
  public static final int Just_Following_VALUE = 2;
  /**
   * <code>Multi_Linking = 3;</code>
   */
  public static final int Multi_Linking_VALUE = 3;
  /**
   * <code>Multi_Linking_Only_Following = 4;</code>
   */
  public static final int Multi_Linking_Only_Following_VALUE = 4;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static LinkmicStatus valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static LinkmicStatus forNumber(int value) {
    switch (value) {
      case 0: return Disable;
      case 1: return Enable;
      case 2: return Just_Following;
      case 3: return Multi_Linking;
      case 4: return Multi_Linking_Only_Following;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<LinkmicStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      LinkmicStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<LinkmicStatus>() {
          public LinkmicStatus findValueByNumber(int number) {
            return LinkmicStatus.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums.Enums.getDescriptor().getEnumTypes().get(7);
  }

  private static final LinkmicStatus[] VALUES = values();

  public static LinkmicStatus valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private LinkmicStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:TikTok.LinkmicStatus)
}

