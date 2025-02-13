plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7"
}

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
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")

    // FastBoard
    implementation("fr.mrmicky:fastboard:2.1.3")

    // Command Api
    implementation("dev.jorel:commandapi-bukkit-shade-mojang-mapped:9.7.0")

    // Papi
    compileOnly("me.clip:placeholderapi:2.11.6")
}

// Where this builds on the server
val serverLocation = "1-21-4"
// Version of SurvivalPlus
val projectVersion = "1.0.0"

java.sourceCompatibility = JavaVersion.VERSION_21

tasks {
    register("server", Copy::class) {
        dependsOn("shadowJar")
        from("build/libs") {
            include("SurvivalPlus-*.jar")
            destinationDir = file("/Users/ShaneBee/Desktop/Server/${serverLocation}/plugins/")
        }

    }
    processResources {
        expand("version" to projectVersion)
    }
    compileJava {
        options.release = 21
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        exclude("com/shanebeestudios/core/plugin")
        (options as StandardJavadocDocletOptions).links(
            "https://jd.papermc.io/paper/1.21.1/",
            "https://jd.advntr.dev/api/4.17.0/"
        )

    }
    shadowJar {
        relocate("fr.mrmicky.fastboard", "com.shanebeestudios.survival.api.fastboard")
        relocate("dev.jorel.commandapi", "com.shanebeestudios.survival.api.commandapi")
        archiveFileName = "SurvivalPlus-${projectVersion}.jar"
    }
    jar {
        dependsOn(shadowJar)
        archiveFileName.set("SurvivalPlus.jar")
    }
}
