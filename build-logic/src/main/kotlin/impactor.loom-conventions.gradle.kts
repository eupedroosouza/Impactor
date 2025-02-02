plugins {
    id("impactor.base-conventions")
    id("impactor.shadow-conventions")

    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    minecraft = rootProject.property("minecraft").toString()
}

loom {
    silentMojangMappingsLicense()

    mixin {
        defaultRefmapName.set("mixins.impactor.${project.name}.refmap.json")
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:${rootProject.property("minecraft")}")
    mappings(loom.officialMojangMappings())

    listOf(
        "net.kyori:examination-api:1.3.0",
        "net.kyori:examination-string:1.3.0",
        "net.kyori:adventure-api:4.14.0",
        "net.kyori:adventure-key:4.14.0",
        "net.kyori:adventure-nbt:4.14.0",
        "net.kyori:adventure-text-serializer-plain:4.14.0",
        "net.kyori:adventure-text-serializer-legacy:4.14.0",
        "net.kyori:adventure-text-serializer-gson:4.14.0",
        "net.kyori:adventure-text-serializer-json:4.14.0",
        "net.kyori:adventure-text-minimessage:4.14.0",
        "net.kyori:adventure-text-logger-slf4j:4.14.0",
        "net.kyori:event-api:5.0.0-SNAPSHOT",
    ).forEach { include(it) }
}

tasks {
    val minecraft = rootProject.property("minecraft")

    shadowJar {
        dependencies {
            listOf(
                "com.zaxxer:HikariCP:.*",
                "com.h2database:h2:.*",
                "mysql:mysql-connector-java:.*",
                "org.mariadb.jdbc:mariadb-java-client:.*",
                "org.mongodb:mongo-java-driver:.*",
                "com.github.ben-manes.caffeine:caffeine:.*",
                "io.github.classgraph:classgraph:.*",
                "com.squareup.okhttp3:okhttp:.*",
                "com.squareup.okio:okio-jvm:.*",
                "com.typesafe:config:.*",
                "org.spongepowered:configurate-core:.*",
                "org.spongepowered:configurate-gson:.*",
                "org.spongepowered:configurate-hocon:.*",
                "org.spongepowered:configurate-yaml:.*",
                "org.spongepowered:math:.*",
                "org.jetbrains.kotlin:kotlin-stdlib:1.7.10"
            ).forEach { include(dependency(it)) }
        }

        val prefix = "net.impactdev.impactor.relocations"
        listOf(
            "com.typesafe.config",
            "com.zaxxer.hikari",
            "org.h2",
            "com.github.benmanes.caffeine",
            "io.github.classgraph",
            "com.mysql",
            "org.mariadb.jdbc",
            "com.mongodb",
            "org.bson",
            "nonapi.io.github.classgraph",
            "okhttp3",
            "okio",
            "org.spongepowered.configurate",
            "org.spongepowered.math",
            "kotlin"
        ).forEach { relocate(it, "$prefix.$it") }
    }

    remapJar {
        archiveBaseName.set("Impactor-${project.name.capitalize()}")
        archiveVersion.set("${minecraft}-${rootProject.version}")
    }
}