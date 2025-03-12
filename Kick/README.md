# Platform-SDKs / Kick

## Basic Usage

TODO!

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

This project is compatible with Java 17 and up.
