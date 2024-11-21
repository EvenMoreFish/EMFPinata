plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.firedev.uk/repository/maven-public/") {
        metadataSources { artifact() }
    }
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly("com.oheers:EvenMoreFish:-SNAPSHOT")
    //compileOnly(files("$projectDir/libs/even-more-fish-1.7.4-140.jar"))
    compileOnly(libs.mythicmobs)

    implementation(libs.commandapi)
    implementation(libs.bstats)
}

group = "uk.firedev"
version = "1.0.7-SNAPSHOT"
description = "A Piñata addon for the EvenMoreFish plugin."
java.sourceCompatibility = JavaVersion.VERSION_17

bukkit {
    name = project.name
    version = project.version.toString()
    main = "uk.firedev.emfpinata.EMFPinata"
    apiVersion = "1.18"
    author = "FireML"
    description = project.description.toString()
    foliaSupported = true

    depend = listOf(
        "EvenMoreFish"
    )

    softDepend = listOf(
        "MythicMobs"
    )
}

publishing {
    repositories {
        maven {
            name = "firedevRepo"

            // Repository settings
            var repoUrlString = "https://repo.firedev.uk/repository/maven-"
            repoUrlString += if (project.version.toString().endsWith("-SNAPSHOT")) {
                "snapshots/"
            } else {
                "releases/"
            }
            url = uri(repoUrlString)

            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        archiveBaseName.set(project.name)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        relocate("dev.jorel.commandapi", "uk.firedev.emfpinata.libs.commandapi")
        relocate("org.bstats", "uk.firedev.emfpinata.libs.bstats")

        minimize()
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
