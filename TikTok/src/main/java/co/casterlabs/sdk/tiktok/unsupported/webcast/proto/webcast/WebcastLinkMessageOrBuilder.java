// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

public interface WebcastLinkMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.WebcastLinkMessage)
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
   * <code>uint32 data1 = 2;</code>
   * @return The data1.
   */
  int getData1();

  /**
   * <code>uint64 data2 = 3;</code>
   * @return The data2.
   */
  long getData2();

  /**
   * <code>uint32 data3 = 4;</code>
   * @return The data3.
   */
  int getData3();

  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageData data = 18;</code>
   * @return Whether the data field is set.
   */
  boolean hasData();
  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageData data = 18;</code>
   * @return The data.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastLinkMessage.LinkMessageData getData();
  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageData data = 18;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastLinkMessage.LinkMessageDataOrBuilder getDataOrBuilder();

  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageUserContainer user = 20;</code>
   * @return Whether the user field is set.
   */
  boolean hasUser();
  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageUserContainer user = 20;</code>
   * @return The user.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastLinkMessage.LinkMessageUserContainer getUser();
  /**
   * <code>.TikTok.WebcastLinkMessage.LinkMessageUserContainer user = 20;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastLinkMessage.LinkMessageUserContainerOrBuilder getUserOrBuilder();

  /**
   * <code>string token = 200;</code>
   * @return The token.
   */
  java.lang.String getToken();
  /**
   * <code>string token = 200;</code>
   * @return The bytes for token.
   */
  com.google.protobuf.ByteString
      getTokenBytes();
}
