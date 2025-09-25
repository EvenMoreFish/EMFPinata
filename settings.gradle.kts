rootProject.name = "EMFPinata"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
            library("bstats", "org.bstats:bstats-bukkit:3.1.0")
            library("mythicmobs", "io.lumine:Mythic-Dist:5.6.2")

            library("evenmorefish", "com.oheers.evenmorefish:even-more-fish-api:2.0.13")

            library("commandapi", "dev.jorel:commandapi-bukkit-shade:10.1.2")
            library("boostedyaml", "dev.dejvokep:boosted-yaml:1.3.7")
            library("messagelib", "uk.firedev:MessageLib:1.0.1")

            plugin("shadow", "com.gradleup.shadow").version("9.2.1")
            plugin("plugin-yml", "de.eldoria.plugin-yml.bukkit").version("0.8.0")
        }
    }
}