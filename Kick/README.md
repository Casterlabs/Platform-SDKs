# Platform-SDKs / Kick

## Basic Usage

### Get a channel's account

```java
KickApi.init(); // Always call init() !

KickChannel channel = new KickGetChannelRequest()
    .setSlug("casterlabs")
    .send();

System.out.println("Profile Picture Url: " + channel.getUser().getProfilePicture());
```

### Get a list of emotes (both global and the current channel's)

```java
KickApi.init(); // Always call init() !

List<KickEmote> emotes = new KickGetChannelEmotesRequest()
    .setSlug(channel.getSlug()) // Use the above channel example or just pass a string (e.g "casterlabs")
    .send()

System.out.println("Channel Emotes: " + emotes); // [KickEmote(id=39260, name=DanceDanceDance, subscribersOnly=false), ...]

// Every emote also has a getImage() method which will generate an image url for the emote. (e.g https://files.kick.com/emotes/39260/fullsize)
// There's also getAsChatEmote() which will give you the text format to put in chat.
```

### Connect to the realtime service and get notified when a streamer goes live

```java
//  FastLoggingFramework.setDefaultLevel(LogLevel.ALL); // Uncomment this line to debug any connection issues.

KickApi.init(); // Always call init() !

KickChannel channel = new KickGetChannelRequest()
    .setSlug("casterlabs")
    .send();

System.out.println("Connecting to " + channel.getSlug());

new KickChannelRealtime(
    channel.getId(), // !!Use the channel ID!!
    new KickChannelListener() {

        @Override
        public void onOpen() {
            System.out.println("Connected successfully to the realtime service.");
        }

        @Override
        public void onClose() {
            System.out.println("Disconnected from the realtime service.");
        }

        @Override
        public void onChannelLive(boolean isLive) {
            if (isLive) {
                System.out.println(channel.getSlug() + " went live!");
            } else {
                System.out.println(channel.getSlug() + " went offline :(");
            }
        }

    }
)
    .connect();
```

### Connect to the realtime service and receive chat messages

```java
//  FastLoggingFramework.setDefaultLevel(LogLevel.ALL); // Uncomment this line to debug any connection issues.

KickApi.init(); // Always call init() !

KickChannel channel = new KickGetChannelRequest()
    .setSlug("casterlabs")
    .send();

System.out.println("Connecting to " + channel.getSlug());

new KickChatRealtime(
    channel.getChatRoomId(), // !!Use the chat room ID!!
    new KickChatListener() {
        @Override
        public void onOpen() {
            System.out.println("Connected successfully to the realtime service.");
        }
        @Override
        public void onClose() {
            System.out.println("Disconnected from the realtime service.");
        }
        @Override
        public void onChat(KickChatEvent event) {
            System.out.println(event.getUser().getUsername() + " > " + event.getMessage().getMessage());
        }
        @Override
        public void onReaction(KickReactionEvent event) {
            // You'll need to track existing chat events by their ID as this will not give
            // you the message that was reacted to.
            System.out.println("Someone reacted to a message with: " + event.getReaction()); // ✅
        }
    }
)
    .connect();
```

## Repository

We use Jitpack for our deployment and hosting.

<details>
  <summary>Maven</summary>
  
  ```xml
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
  ```
</details>

<details>
  <summary>Gradle</summary>
  
  ```gradle
    allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
  ```
</details>

<details>
  <summary>SBT</summary>
  
  ```
resolvers += "jitpack" at "https://jitpack.io"
  ```
</details>

<details>
  <summary>Leiningen</summary>
  
  ```
:repositories [["jitpack" "https://jitpack.io"]]
  ```
</details>

## Adding to your project

Replace `_VERSION` with the latest commit in this repo and make sure to add the above repository to your build system.

<details>
  <summary>Maven</summary>
  
  ```xml
    <dependency>
        <groupId>co.casterlabs.Platform-SDKs</groupId>
        <artifactId>Kick</artifactId>
        <version>_VERSION</version>
    </dependency>
  ```
</details>

<details>
  <summary>Gradle</summary>
  
  ```gradle
	dependencies {
        implementation 'co.casterlabs:Platform-SDKs.Kick:_VERSION'
	}
  ```
</details>

<details>
  <summary>SBT</summary>
  
  ```
libraryDependencies += "co.casterlabs.Platform-SDKs" % "Kick" % "_VERSION"
  ```
</details>

<details>
  <summary>Leiningen</summary>
  
  ```
:dependencies [[co.casterlabs.Platform-SDKs/Kick "_VERSION"]]	
  ```
</details>

## Used by

- Us :^)

_Want your project included here? Open an issue and we'll add you ❤._

## Development

This project utilizes Lombok for code generation (e.g Getters, Setters, Constructors), in order for your IDE to properly detect this, you'll need to install the Lombok extension. Instructions can be found [here](https://projectlombok.org/setup/) under "IDEs".

### Java Version

This project is compatible with Java 8 and up.
