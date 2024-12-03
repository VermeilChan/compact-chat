object Constants {
    const val MINECRAFT_VERSION = "1.21.3"
    const val YARN_VERSION = "1.21.3+build.2"
    const val LOADER_VERSION = "0.16.9"

    const val FABRIC_API_VERSION = "0.110.0+1.21.3"
    const val MOD_MENU_VERSION = "12.0.0-beta.1"
    const val CLOTH_CONFIG_VERSION = "16.0.141"
}

plugins {
    id("fabric-loom") version "1.9-SNAPSHOT"
}

group = "dev.caoimhe.compactchat"
version = "2.1.2"
base.archivesName = "compact-chat"

loom {
    runs {
        remove(getByName("server"))
    }
}

repositories {
    maven("https://maven.terraformersmc.com/releases/")
    maven("https://maven.shedaniel.me/")
}

dependencies {
    minecraft("com.mojang:minecraft:${Constants.MINECRAFT_VERSION}")
    mappings("net.fabricmc:yarn:${Constants.YARN_VERSION}:v2")
    modImplementation("net.fabricmc:fabric-loader:${Constants.LOADER_VERSION}")

    modApi("com.terraformersmc:modmenu:${Constants.MOD_MENU_VERSION}")
    modApi("me.shedaniel.cloth:cloth-config-fabric:${Constants.CLOTH_CONFIG_VERSION}") {
        exclude(group = "net.fabricmc.fabric-api")
    }

    modRuntimeOnly("net.fabricmc.fabric-api:fabric-api:${Constants.FABRIC_API_VERSION}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName}" }
        }
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching("fabric.mod.json") {
            expand(mapOf("version" to project.version))
        }
    }
}
