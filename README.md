# EMFPiñata

A Piñata addon for the EvenMoreFish plugin.

## Download

Release Builds are available on [Modrinth](https://modrinth.com/plugin/emfpinata).

Snapshot/Dev Builds are available on [Jenkins](https://ci.codemc.io/job/EvenMoreFish/job/EMFPinata/).

> [!IMPORTANT]  
> EMFPiñata depends on [EvenMoreFish](https://modrinth.com/plugin/evenmorefish). The plugin will not load without it.

## Wiki Pages

Piñata Config: https://github.com/EvenMoreFish/EMFPinata/wiki/Pi%C3%B1ata-Config

## Gradle (Kotlin)

```kotlin
repositories {
    maven("https://repo.codemc.io/repository/EvenMoreFish/")
}

dependencies {
    compileOnly("uk.firedev:EMFPinata:1.0.6")
}
```
