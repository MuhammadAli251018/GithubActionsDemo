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

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}