# EMFPiñata

A Piñata addon for the EvenMoreFish plugin.

This addon adds a new RewardType to spawn a Piñata. This Piñata can be "filled" with rewards that are given when it is killed by a player.

> [!NOTE]  
> This addon is its own plugin and will not work inside the EvenMoreFish addons folder.

## Download

Release Builds are available on [Modrinth](https://modrinth.com/plugin/emfpinata).

Snapshot/Dev Builds are available on [Jenkins](https://ci.codemc.io/job/EvenMoreFish/job/EMFPinata/).

> [!IMPORTANT]  
> EMFPiñata depends on [EvenMoreFish](https://modrinth.com/plugin/evenmorefish). The plugin will not load without it.

## Wiki

The wiki can be found here: https://github.com/EvenMoreFish/EMFPinata/wiki

## Gradle (Kotlin)

```kotlin
repositories {
    maven("https://repo.codemc.io/repository/EvenMoreFish/")
}

dependencies {
    compileOnly("uk.firedev:EMFPinata:2.0.0")
}
```
