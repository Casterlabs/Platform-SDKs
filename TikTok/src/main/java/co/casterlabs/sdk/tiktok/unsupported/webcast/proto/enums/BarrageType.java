// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: enums.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums;

/**
 * Protobuf enum {@code TikTok.BarrageType}
 */
public enum BarrageType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>BarrageType_Unknown = 0;</code>
   */
  BarrageType_Unknown(0),
  /**
   * <code>EComOrdering = 1;</code>
   */
  EComOrdering(1),
  /**
   * <code>EComBuying = 2;</code>
   */
  EComBuying(2),
  /**
   * <code>Normal = 3;</code>
   */
  Normal(3),
  /**
   * <code>Subscribe = 4;</code>
   */
  Subscribe(4),
  /**
   * <code>EventView = 5;</code>
   */
  EventView(5),
  /**
   * <code>EventRegistered = 6;</code>
   */
  EventRegistered(6),
  /**
   * <code>SubscribeGift = 7;</code>
   */
  SubscribeGift(7),
  /**
   * <code>UserUpgrade = 8;</code>
   */
  UserUpgrade(8),
  /**
   * <code>GradeUserEntranceNotification = 9;</code>
   */
  GradeUserEntranceNotification(9),
  /**
   * <code>FansLevelUpgrade = 10;</code>
   */
  FansLevelUpgrade(10),
  /**
   * <code>FansLevelEntrance = 11;</code>
   */
  FansLevelEntrance(11),
  /**
   * <code>GamePartnership = 12;</code>
   */
  GamePartnership(12),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>BarrageType_Unknown = 0;</code>
   */
  public static final int BarrageType_Unknown_VALUE = 0;
  /**
   * <code>EComOrdering = 1;</code>
   */
  public static final int EComOrdering_VALUE = 1;
  /**
   * <code>EComBuying = 2;</code>
   */
  public static final int EComBuying_VALUE = 2;
  /**
   * <code>Normal = 3;</code>
   */
  public static final int Normal_VALUE = 3;
  /**
   * <code>Subscribe = 4;</code>
   */
  public static final int Subscribe_VALUE = 4;
  /**
   * <code>EventView = 5;</code>
   */
  public static final int EventView_VALUE = 5;
  /**
   * <code>EventRegistered = 6;</code>
   */
  public static final int EventRegistered_VALUE = 6;
  /**
   * <code>SubscribeGift = 7;</code>
   */
  public static final int SubscribeGift_VALUE = 7;
  /**
   * <code>UserUpgrade = 8;</code>
   */
  public static final int UserUpgrade_VALUE = 8;
  /**
   * <code>GradeUserEntranceNotification = 9;</code>
   */
  public static final int GradeUserEntranceNotification_VALUE = 9;
  /**
   * <code>FansLevelUpgrade = 10;</code>
   */
  public static final int FansLevelUpgrade_VALUE = 10;
  /**
   * <code>FansLevelEntrance = 11;</code>
   */
  public static final int FansLevelEntrance_VALUE = 11;
  /**
   * <code>GamePartnership = 12;</code>
   */
  public static final int GamePartnership_VALUE = 12;


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
  public static BarrageType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static BarrageType forNumber(int value) {
    switch (value) {
      case 0: return BarrageType_Unknown;
      case 1: return EComOrdering;
      case 2: return EComBuying;
      case 3: return Normal;
      case 4: return Subscribe;
      case 5: return EventView;
      case 6: return EventRegistered;
      case 7: return SubscribeGift;
      case 8: return UserUpgrade;
      case 9: return GradeUserEntranceNotification;
      case 10: return FansLevelUpgrade;
      case 11: return FansLevelEntrance;
      case 12: return GamePartnership;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<BarrageType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      BarrageType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<BarrageType>() {
          public BarrageType findValueByNumber(int number) {
            return BarrageType.forNumber(number);
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
    return co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums.Enums.getDescriptor().getEnumTypes().get(11);
  }

  private static final BarrageType[] VALUES = values();

  public static BarrageType valueOf(
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

  private BarrageType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:TikTok.BarrageType)
}

