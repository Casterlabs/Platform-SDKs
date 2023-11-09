// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums;

/**
 * Protobuf enum {@code TikTok.OldSubscribeStatus}
 */
public enum OldSubscribeStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>OLDSUBSCRIBESTATUS_FIRST = 0;</code>
   */
  OLDSUBSCRIBESTATUS_FIRST(0),
  /**
   * <code>OLDSUBSCRIBESTATUS_RESUB = 1;</code>
   */
  OLDSUBSCRIBESTATUS_RESUB(1),
  /**
   * <code>OLDSUBSCRIBESTATUS_SUBINGRACEPERIOD = 2;</code>
   */
  OLDSUBSCRIBESTATUS_SUBINGRACEPERIOD(2),
  /**
   * <code>OLDSUBSCRIBESTATUS_SUBNOTINGRACEPERIOD = 3;</code>
   */
  OLDSUBSCRIBESTATUS_SUBNOTINGRACEPERIOD(3),
  /**
   * <code>OLDSUBSCRIBESTATUS_DEFAULT = 100;</code>
   */
  OLDSUBSCRIBESTATUS_DEFAULT(100),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>OLDSUBSCRIBESTATUS_FIRST = 0;</code>
   */
  public static final int OLDSUBSCRIBESTATUS_FIRST_VALUE = 0;
  /**
   * <code>OLDSUBSCRIBESTATUS_RESUB = 1;</code>
   */
  public static final int OLDSUBSCRIBESTATUS_RESUB_VALUE = 1;
  /**
   * <code>OLDSUBSCRIBESTATUS_SUBINGRACEPERIOD = 2;</code>
   */
  public static final int OLDSUBSCRIBESTATUS_SUBINGRACEPERIOD_VALUE = 2;
  /**
   * <code>OLDSUBSCRIBESTATUS_SUBNOTINGRACEPERIOD = 3;</code>
   */
  public static final int OLDSUBSCRIBESTATUS_SUBNOTINGRACEPERIOD_VALUE = 3;
  /**
   * <code>OLDSUBSCRIBESTATUS_DEFAULT = 100;</code>
   */
  public static final int OLDSUBSCRIBESTATUS_DEFAULT_VALUE = 100;


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
  public static OldSubscribeStatus valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static OldSubscribeStatus forNumber(int value) {
    switch (value) {
      case 0: return OLDSUBSCRIBESTATUS_FIRST;
      case 1: return OLDSUBSCRIBESTATUS_RESUB;
      case 2: return OLDSUBSCRIBESTATUS_SUBINGRACEPERIOD;
      case 3: return OLDSUBSCRIBESTATUS_SUBNOTINGRACEPERIOD;
      case 100: return OLDSUBSCRIBESTATUS_DEFAULT;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<OldSubscribeStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      OldSubscribeStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<OldSubscribeStatus>() {
          public OldSubscribeStatus findValueByNumber(int number) {
            return OldSubscribeStatus.forNumber(number);
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
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums.Enums.getDescriptor().getEnumTypes().get(5);
  }

  private static final OldSubscribeStatus[] VALUES = values();

  public static OldSubscribeStatus valueOf(
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

  private OldSubscribeStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:TikTok.OldSubscribeStatus)
}

