plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.shadow)
    alias(libs.plugins.plugin.yml)
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://mvn.lumine.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/EvenMoreFish/")
}

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.evenmorefish)
    compileOnly(libs.mythicmobs)
    
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
            url = uri("https://repo.codemc.io/repository/FireML/")

            val mavenUsername = System.getenv("JENKINS_USERNAME")
            val mavenPassword = System.getenv("JENKINS_PASSWORD")

            if (mavenUsername != null && mavenPassword != null) {
                credentials {
                    username = mavenUsername
                    password = mavenPassword
                }
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

        relocate("org.bstats", "uk.firedev.emfpinata.libs.bstats")
    }
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}
