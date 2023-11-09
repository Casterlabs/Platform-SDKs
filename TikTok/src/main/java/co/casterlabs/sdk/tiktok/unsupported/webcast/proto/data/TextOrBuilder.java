// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: data.proto

package co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data;

public interface TextOrBuilder extends
    // @@protoc_insertion_point(interface_extends:TikTok.Text)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string key = 1;</code>
   * @return The key.
   */
  java.lang.String getKey();
  /**
   * <code>string key = 1;</code>
   * @return The bytes for key.
   */
  com.google.protobuf.ByteString
      getKeyBytes();

  /**
   * <code>string defaultPattern = 2;</code>
   * @return The defaultPattern.
   */
  java.lang.String getDefaultPattern();
  /**
   * <code>string defaultPattern = 2;</code>
   * @return The bytes for defaultPattern.
   */
  com.google.protobuf.ByteString
      getDefaultPatternBytes();

  /**
   * <code>.TikTok.Text.TextFormat defaultFormat = 3;</code>
   * @return Whether the defaultFormat field is set.
   */
  boolean hasDefaultFormat();
  /**
   * <code>.TikTok.Text.TextFormat defaultFormat = 3;</code>
   * @return The defaultFormat.
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextFormat getDefaultFormat();
  /**
   * <code>.TikTok.Text.TextFormat defaultFormat = 3;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextFormatOrBuilder getDefaultFormatOrBuilder();

  /**
   * <code>repeated .TikTok.Text.TextPiece piecesList = 4;</code>
   */
  java.util.List<co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextPiece> 
      getPiecesListList();
  /**
   * <code>repeated .TikTok.Text.TextPiece piecesList = 4;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextPiece getPiecesList(int index);
  /**
   * <code>repeated .TikTok.Text.TextPiece piecesList = 4;</code>
   */
  int getPiecesListCount();
  /**
   * <code>repeated .TikTok.Text.TextPiece piecesList = 4;</code>
   */
  java.util.List<? extends co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextPieceOrBuilder> 
      getPiecesListOrBuilderList();
  /**
   * <code>repeated .TikTok.Text.TextPiece piecesList = 4;</code>
   */
  co.casterlabs.sdk.tiktok.unsupported.webcast.proto.data.Text.TextPieceOrBuilder getPiecesListOrBuilder(
      int index);
}
