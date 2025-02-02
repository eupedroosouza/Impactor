import extensions.isRelease
import extensions.writeVersion
import net.fabricmc.loom.task.RemapJarTask
import java.nio.file.Files

plugins {
    id("impactor.loom-conventions")
    id("com.modrinth.minotaur")
}

tasks {
    val remapProductionJar by registering(RemapJarTask::class) {
        listOf(shadowJar, remapJar).forEach {
            dependsOn(it)
            mustRunAfter(it)
        }

        inputFile.set(shadowJar.flatMap { it.archiveFile })

        archiveBaseName.set("Impactor-${project.name.capitalize()}")
        archiveVersion.set(writeVersion())
    }
}

tasks.withType<PublishToMavenRepository> {
    dependsOn(tasks["remapProductionJar"])
}

tasks.withType<GenerateModuleMetadata> {
    dependsOn(tasks["remapProductionJar"])
}

modrinth {
    token.set(System.getenv("MODRINTH_GRADLE_TOKEN"))
    projectId.set("Impactor")
    versionNumber.set(writeVersion())
    versionName.set("Impactor ${writeVersion()}")

    versionType.set(if(!isRelease()) "beta" else "release")
    uploadFile.set(tasks["remapProductionJar"])

    gameVersions.set(listOf(rootProject.property("minecraft").toString()))

    // https://github.com/modrinth/minotaur
    // TODO - Project Body Sync
    changelog.set(readChangelog())
    debugMode.set(true)
}

fun readChangelog(): String {
    val plugin = rootProject.property("plugin")
    val contents = rootProject.buildDir.toPath()
        .resolve("deploy")
        .resolve("$plugin.md")

    if(!Files.exists(contents)) {
        return "No changelog notes available..."
    }

    return contents.toFile().readLines().joinToString(separator = "\n")
}