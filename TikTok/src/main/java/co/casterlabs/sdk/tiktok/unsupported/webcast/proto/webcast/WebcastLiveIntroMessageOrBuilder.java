// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

public interface WebcastLiveIntroMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.WebcastLiveIntroMessage)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.TikTok.Common common = 1;</code>
   * @return Whether the common field is set.
   */
  boolean hasCommon();
  /**
   * <code>.TikTok.Common common = 1;</code>
   * @return The common.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Common getCommon();
  /**
   * <code>.TikTok.Common common = 1;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.CommonOrBuilder getCommonOrBuilder();

  /**
   * <code>int64 roomId = 2;</code>
   * @return The roomId.
   */
  long getRoomId();

  /**
   * <code>.TikTok.AuditStatus auditStatus = 3;</code>
   * @return The enum numeric value on the wire for auditStatus.
   */
  int getAuditStatusValue();
  /**
   * <code>.TikTok.AuditStatus auditStatus = 3;</code>
   * @return The auditStatus.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.enums.AuditStatus getAuditStatus();

  /**
   * <code>string content = 4;</code>
   * @return The content.
   */
  java.lang.String getContent();
  /**
   * <code>string content = 4;</code>
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <code>.TikTok.User host = 5;</code>
   * @return Whether the host field is set.
   */
  boolean hasHost();
  /**
   * <code>.TikTok.User host = 5;</code>
   * @return The host.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.User getHost();
  /**
   * <code>.TikTok.User host = 5;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserOrBuilder getHostOrBuilder();

  /**
   * <code>int32 introMode = 6;</code>
   * @return The introMode.
   */
  int getIntroMode();

  /**
   * <code>repeated .TikTok.BadgeStruct badges = 7;</code>
   */
  java.util.List<co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.BadgeStruct> 
      getBadgesList();
  /**
   * <code>repeated .TikTok.BadgeStruct badges = 7;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.BadgeStruct getBadges(int index);
  /**
   * <code>repeated .TikTok.BadgeStruct badges = 7;</code>
   */
  int getBadgesCount();
  /**
   * <code>repeated .TikTok.BadgeStruct badges = 7;</code>
   */
  java.util.List<? extends co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.BadgeStructOrBuilder> 
      getBadgesOrBuilderList();
  /**
   * <code>repeated .TikTok.BadgeStruct badges = 7;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.BadgeStructOrBuilder getBadgesOrBuilder(
      int index);

  /**
   * <code>string language = 8;</code>
   * @return The language.
   */
  java.lang.String getLanguage();
  /**
   * <code>string language = 8;</code>
   * @return The bytes for language.
   */
  com.google.protobuf.ByteString
      getLanguageBytes();
}
