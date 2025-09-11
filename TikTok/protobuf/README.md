# TikTok Protobuf Definitions

Source: https://github.com/zerodytrash/TikTok-Live-Connector/tree/ts-rewrite/.proto/src

Be sure to add the following to each file when you update the .proto:
```
option java_multiple_files = true;
option java_package = "co.casterlabs.sdk.tiktok.unsupported.types.protobuf.<filename>";
```