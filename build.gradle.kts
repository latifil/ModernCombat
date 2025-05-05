import io.papermc.paperweight.userdev.ReobfArtifactConfiguration
import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

group = "me.latifil.modernCombat"
version = "0.2.1"
description = "Turns modern pvp into pre-1.9 combat."

plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

paperweight.reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION

dependencies {
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

tasks {
    compileJava {
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }
}

// Configure plugin.yml generation
// - name, version, and description are inherited from the Gradle project.
bukkitPluginYaml {
    main = "me.latifil.modernCombat.ModernCombat"
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    authors.add("Latifil")
    apiVersion = "1.21"
    description = properties["description"] as String
    foliaSupported = false
}