// Top-level build file where you can add configuration options common to all sub-projects/modules.
tasks.register("runAllTests") {
    group = "verification"
    description = "Runs all tests in the project."

    subprojects.forEach { subproject ->
        subproject.tasks.matching { it.name == "testDebugUnitTest" || it.name == "test" }.forEach { task ->
            dependsOn(task)
        }
    }
}

tasks.register("count_kotlin_lines") {
    group = "reporting"
    description = "Counts the number of lines of Kotlin code in the project."

    doLast {
        val kotlinFiles = fileTree(".") {
            include("**/*.kt", "**/*.kts")
            exclude("**/build/**", "**/.gradle/**", "**/node_modules/**")
        }

        val totalLines = kotlinFiles.sumOf { it.readLines().size }
        println("Total lines of Kotlin code: $totalLines")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}