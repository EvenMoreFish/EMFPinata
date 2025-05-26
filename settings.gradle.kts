rootProject.name = "EMFPinata"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("paper-api", "io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
            library("bstats", "org.bstats:bstats-bukkit:3.1.0")
            library("mythicmobs", "io.lumine:Mythic-Dist:5.6.2")

            library("evenmorefish", "com.oheers.evenmorefish:even-more-fish-plugin:2.0.0")

            plugin("shadow", "com.gradleup.shadow").version("8.3.5")
            plugin("plugin-yml", "net.minecrell.plugin-yml.bukkit").version("0.6.0")
        }
    }
}