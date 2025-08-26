package com.luminate.luminatelauncher.build_helpers

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class GenerateReleaseBundleTask : DefaultTask() {
    @get:Input
    abstract val versionReference: Property<String>

    @TaskAction
    fun generate() {
        val bundleDir = project.layout.buildDirectory.dir("outputs/bundle/release").get().asFile
        val originalBundle = File(bundleDir, "app-release.aab")
        val renamedBundle = File(bundleDir, "app-release-${versionReference.get()}.aab")

        if (originalBundle.exists()) {
            originalBundle.renameTo(renamedBundle)
            println("Renamed bundle to: ${renamedBundle.absolutePath}")
        } else {
            println("Release bundle not found: ${originalBundle.absolutePath}")
        }
    }
}

