// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: data.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data;

public interface LinkMicArmiesItemsOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.LinkMicArmiesItems)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>uint64 hostUserId = 1;</code>
   * @return The hostUserId.
   */
  long getHostUserId();

  /**
   * <code>repeated .TikTok.LinkMicArmiesItems.LinkMicArmiesGroup battleGroups = 2;</code>
   */
  java.util.List<co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.LinkMicArmiesItems.LinkMicArmiesGroup> 
      getBattleGroupsList();
  /**
   * <code>repeated .TikTok.LinkMicArmiesItems.LinkMicArmiesGroup battleGroups = 2;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.LinkMicArmiesItems.LinkMicArmiesGroup getBattleGroups(int index);
  /**
   * <code>repeated .TikTok.LinkMicArmiesItems.LinkMicArmiesGroup battleGroups = 2;</code>
   */
  int getBattleGroupsCount();
  /**
   * <code>repeated .TikTok.LinkMicArmiesItems.LinkMicArmiesGroup battleGroups = 2;</code>
   */
  java.util.List<? extends co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.LinkMicArmiesItems.LinkMicArmiesGroupOrBuilder> 
      getBattleGroupsOrBuilderList();
  /**
   * <code>repeated .TikTok.LinkMicArmiesItems.LinkMicArmiesGroup battleGroups = 2;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.LinkMicArmiesItems.LinkMicArmiesGroupOrBuilder getBattleGroupsOrBuilder(
      int index);
}
