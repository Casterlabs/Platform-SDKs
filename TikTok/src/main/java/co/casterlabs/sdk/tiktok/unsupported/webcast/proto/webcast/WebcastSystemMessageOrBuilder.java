// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

public interface WebcastSystemMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.WebcastSystemMessage)
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
   * <code>string message = 2;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 2;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}
