// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums;

/**
 * Protobuf enum {@code TikTok.SubscribingStatus}
 */
public enum SubscribingStatus
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>SUBSCRIBINGSTATUS_UNKNOWN = 0;</code>
   */
  SUBSCRIBINGSTATUS_UNKNOWN(0),
  /**
   * <code>SUBSCRIBINGSTATUS_ONCE = 1;</code>
   */
  SUBSCRIBINGSTATUS_ONCE(1),
  /**
   * <code>SUBSCRIBINGSTATUS_CIRCLE = 2;</code>
   */
  SUBSCRIBINGSTATUS_CIRCLE(2),
  /**
   * <code>SUBSCRIBINGSTATUS_CIRCLECANCEL = 3;</code>
   */
  SUBSCRIBINGSTATUS_CIRCLECANCEL(3),
  /**
   * <code>SUBSCRIBINGSTATUS_REFUND = 4;</code>
   */
  SUBSCRIBINGSTATUS_REFUND(4),
  /**
   * <code>SUBSCRIBINGSTATUS_INGRACEPERIOD = 5;</code>
   */
  SUBSCRIBINGSTATUS_INGRACEPERIOD(5),
  /**
   * <code>SUBSCRIBINGSTATUS_NOTINGRACEPERIOD = 6;</code>
   */
  SUBSCRIBINGSTATUS_NOTINGRACEPERIOD(6),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>SUBSCRIBINGSTATUS_UNKNOWN = 0;</code>
   */
  public static final int SUBSCRIBINGSTATUS_UNKNOWN_VALUE = 0;
  /**
   * <code>SUBSCRIBINGSTATUS_ONCE = 1;</code>
   */
  public static final int SUBSCRIBINGSTATUS_ONCE_VALUE = 1;
  /**
   * <code>SUBSCRIBINGSTATUS_CIRCLE = 2;</code>
   */
  public static final int SUBSCRIBINGSTATUS_CIRCLE_VALUE = 2;
  /**
   * <code>SUBSCRIBINGSTATUS_CIRCLECANCEL = 3;</code>
   */
  public static final int SUBSCRIBINGSTATUS_CIRCLECANCEL_VALUE = 3;
  /**
   * <code>SUBSCRIBINGSTATUS_REFUND = 4;</code>
   */
  public static final int SUBSCRIBINGSTATUS_REFUND_VALUE = 4;
  /**
   * <code>SUBSCRIBINGSTATUS_INGRACEPERIOD = 5;</code>
   */
  public static final int SUBSCRIBINGSTATUS_INGRACEPERIOD_VALUE = 5;
  /**
   * <code>SUBSCRIBINGSTATUS_NOTINGRACEPERIOD = 6;</code>
   */
  public static final int SUBSCRIBINGSTATUS_NOTINGRACEPERIOD_VALUE = 6;


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
  public static SubscribingStatus valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static SubscribingStatus forNumber(int value) {
    switch (value) {
      case 0: return SUBSCRIBINGSTATUS_UNKNOWN;
      case 1: return SUBSCRIBINGSTATUS_ONCE;
      case 2: return SUBSCRIBINGSTATUS_CIRCLE;
      case 3: return SUBSCRIBINGSTATUS_CIRCLECANCEL;
      case 4: return SUBSCRIBINGSTATUS_REFUND;
      case 5: return SUBSCRIBINGSTATUS_INGRACEPERIOD;
      case 6: return SUBSCRIBINGSTATUS_NOTINGRACEPERIOD;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<SubscribingStatus>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      SubscribingStatus> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<SubscribingStatus>() {
          public SubscribingStatus findValueByNumber(int number) {
            return SubscribingStatus.forNumber(number);
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
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums.Enums.getDescriptor().getEnumTypes().get(6);
  }

  private static final SubscribingStatus[] VALUES = values();

  public static SubscribingStatus valueOf(
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

  private SubscribingStatus(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:TikTok.SubscribingStatus)
}
