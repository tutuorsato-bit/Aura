pluginManagement {
    repositories {
        maven { url = uri("https://maven.fabricmc.net/") }
        gradlePluginPortal()
    }
}

plugins {
    java
    id("fabric-loom") version "1.7.1"
}

version = "1.0.0"
group = "com.example"

repositories {
    mavenCentral()
    maven { url = uri("https://maven.fabricmc.net/") }
    maven { url = uri("https://maven.terraformersmc.com/releases/") }
}

dependencies {
    minecraft("com.mojang:minecraft:1.21.1")
    mappings("net.fabricmc:yarn:1.21.1+build.3:v2")
    modImplementation("net.fabricmc:fabric-loader:0.16.5")
    
    // O compileOnly evita que o robô tente baixar o Meteor da rede agora
    compileOnly("meteordevelopment:meteor-client:0.5.8")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
