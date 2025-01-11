# EMFPiñata

A Piñata addon for the EvenMoreFish plugin.

Requires at least Paper 1.18.2 and Java 17 to work!

[![CodeFactor](https://www.codefactor.io/repository/github/fireml-dev/emfpinata/badge)](https://www.codefactor.io/repository/github/fireml-dev/emfpinata)

## Download

Release Builds are available on [Modrinth](https://modrinth.com/plugin/emfpinata).

Snapshot/Dev Builds are available on [Jenkins](https://ci.firedev.uk/job/EMFPinata/).

> [!IMPORTANT]  
> EMFPiñata depends on [EvenMoreFish](https://modrinth.com/plugin/evenmorefish). The plugin will not load without it.

## Wiki Pages

Piñata Config: https://github.com/FireML-Dev/EMFPinata/wiki/Pi%C3%B1ata-Config

## Gradle (Kotlin)

```kotlin
repositories {
    maven("https://repo.firedev.uk/repository/maven-public/")
}

dependencies {
    compileOnly("uk.firedev:EMFPinata:1.0.6")
}
```
