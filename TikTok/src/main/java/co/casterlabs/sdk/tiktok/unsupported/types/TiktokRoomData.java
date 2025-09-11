package co.casterlabs.sdk.tiktok.unsupported.types;

import org.jetbrains.annotations.Nullable;

public interface TiktokRoomData {
    public static final TiktokRoomData OFFLINE = new TiktokRoomData() {
        @Override
        public @Nullable String chatRoomId() {
            return null;
        }

        @Override
        public boolean isLive() {
            return false;
        }

        @Override
        public boolean isAgeRestricted() {
            return false;
        }

        @Override
        public @Nullable String title() {
            return null;
        }

        @Override
        public @Nullable String coverUrl() {
            return null;
        }

        @Override
        public String toString() {
            return "<offline>";
        }
    };

    public @Nullable String chatRoomId();

    public boolean isLive();

    public boolean isAgeRestricted();

    public @Nullable String title();

    public @Nullable String coverUrl();

}
