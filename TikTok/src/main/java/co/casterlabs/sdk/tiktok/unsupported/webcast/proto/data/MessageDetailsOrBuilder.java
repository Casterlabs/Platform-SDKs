// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: data.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data;

public interface MessageDetailsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.MessageDetails)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint32 data1 = 1;</code>
   * @return The data1.
   */
  int getData1();

  /**
   * <code>.TikTok.TikTokColor color = 2;</code>
   * @return Whether the color field is set.
   */
  boolean hasColor();
  /**
   * <code>.TikTok.TikTokColor color = 2;</code>
   * @return The color.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.TikTokColor getColor();
  /**
   * <code>.TikTok.TikTokColor color = 2;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.TikTokColorOrBuilder getColorOrBuilder();

  /**
   * <code>string category = 11;</code>
   * @return The category.
   */
  java.lang.String getCategory();
  /**
   * <code>string category = 11;</code>
   * @return The bytes for category.
   */
  com.google.protobuf.ByteString
      getCategoryBytes();

  /**
   * <code>.TikTok.UserContainer user = 21;</code>
   * @return Whether the user field is set.
   */
  boolean hasUser();
  /**
   * <code>.TikTok.UserContainer user = 21;</code>
   * @return The user.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainer getUser();
  /**
   * <code>.TikTok.UserContainer user = 21;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.UserContainerOrBuilder getUserOrBuilder();
}