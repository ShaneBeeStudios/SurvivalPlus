plugins {
    `java-library`
    `maven-publish`
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

group = "com.shanebeestudios"
version = "3.15.0"
description = "SurvivalPlus"
java.sourceCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
