// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: webcast.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast;

public interface WebcastOecLiveShoppingMessageOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.WebcastOecLiveShoppingMessage)
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
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingData shopData = 4;</code>
   * @return Whether the shopData field is set.
   */
  boolean hasShopData();
  /**
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingData shopData = 4;</code>
   * @return The shopData.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastOecLiveShoppingMessage.LiveShoppingData getShopData();
  /**
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingData shopData = 4;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastOecLiveShoppingMessage.LiveShoppingDataOrBuilder getShopDataOrBuilder();

  /**
   * <pre>
   * Uses index 1, 2 &amp; 3
   * </pre>
   *
   * <code>.TikTok.TimeStampContainer shopTimings = 5;</code>
   * @return Whether the shopTimings field is set.
   */
  boolean hasShopTimings();
  /**
   * <pre>
   * Uses index 1, 2 &amp; 3
   * </pre>
   *
   * <code>.TikTok.TimeStampContainer shopTimings = 5;</code>
   * @return The shopTimings.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.TimeStampContainer getShopTimings();
  /**
   * <pre>
   * Uses index 1, 2 &amp; 3
   * </pre>
   *
   * <code>.TikTok.TimeStampContainer shopTimings = 5;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.TimeStampContainerOrBuilder getShopTimingsOrBuilder();

  /**
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingDetails details = 9;</code>
   * @return Whether the details field is set.
   */
  boolean hasDetails();
  /**
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingDetails details = 9;</code>
   * @return The details.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastOecLiveShoppingMessage.LiveShoppingDetails getDetails();
  /**
   * <code>.TikTok.WebcastOecLiveShoppingMessage.LiveShoppingDetails details = 9;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.webcast.WebcastOecLiveShoppingMessage.LiveShoppingDetailsOrBuilder getDetailsOrBuilder();
}
