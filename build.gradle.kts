plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

// Where this builds on the server
val serverLocation = "Skript/1-21-4"
// Version of SurvivalPlus
val projectVersion = "1.0.0"
// Minecraft version to build against
val minecraftVersion = "1.21.4"

java.sourceCompatibility = JavaVersion.VERSION_21

repositories {
    mavenCentral()
    mavenLocal()

    // Paper
    maven("https://repo.papermc.io/repository/maven-public/")

    // Command Api Snapshots
    maven("https://s01.oss.sonatype.org/content/repositories")

    // JitPack repo
    maven("https://jitpack.io")

    // Papi
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // Paper
    compileOnly("io.papermc.paper:paper-api:${minecraftVersion}-R0.1-SNAPSHOT")

    // FastBoard
    implementation("fr.mrmicky:fastboard:2.1.3")

    // Command Api
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.7.0")

    // Papi
    compileOnly("me.clip:placeholderapi:2.11.6")

    // bStats
    implementation("org.bstats:bstats-bukkit:3.0.2")

}

tasks {
    register("server", Copy::class) {
        dependsOn("shadowJar")
        from("build/libs") {
            include("SurvivalPlus-*.jar")
            destinationDir = file("/Users/ShaneBee/Desktop/Server/${serverLocation}/plugins/")
        }

    }
    register("resourcepack", Zip::class) {
        archiveFileName = "SurvivalPlusResourcePack-${projectVersion}.zip"
        from("src/main/resources/resource-pack") {
            exclude("**/.DS_Store")
            destinationDirectory = file("build/libs/")
        }
    }
    processResources {
        expand("version" to projectVersion)
        exclude("resource-pack/*")
    }
    compileJava {
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        exclude("com/shanebeestudios/survival/SurvivalBootstrap.java")
        exclude("com/shanebeestudios/survival/commands")
        exclude("com/shanebeestudios/survival/listeners")
        exclude("com/shanebeestudios/survival/tasks")
        (options as CoreJavadocOptions).addBooleanOption("Xdoclint:none", true)
        (options as StandardJavadocDocletOptions).links(
            "https://jd.papermc.io/paper/${minecraftVersion}/",
            "https://jd.advntr.dev/api/4.18.0/"
        )

    }
    shadowJar {
        relocate("fr.mrmicky.fastboard", "com.shanebeestudios.survival.api.fastboard")
        relocate("dev.jorel.commandapi", "com.shanebeestudios.survival.api.commandapi")
        relocate("org.bstats", "com.shanebeestudios.survival.api.metrics.bstats")
        archiveFileName = "SurvivalPlus-${projectVersion}.jar"
    }
    jar {
        dependsOn(shadowJar)
        archiveFileName.set("SurvivalPlus.jar")
    }
}
