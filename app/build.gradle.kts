import com.luminate.luminatelauncher.build_helpers.GenerateReleaseBundleTask

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

tasks.register<GenerateReleaseBundleTask>("generateReleaseBundle") {
    group = "build"
    description = "Generates a release AAB and attach the version reference to the filename."
    dependsOn(":app:bundleRelease")
    versionReference.set(project.findProperty("VERSION_REF") as? String ?: "no-version-ref")
}

android {
    namespace = "com.onemorenerd.githubactionsdemo"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.onemorenerd.githubactionsdemo"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        register("release") {
            val hasSigning = providers.gradleProperty("RELEASE_STORE_PASSWORD").isPresent
            val keyFilePath = providers.gradleProperty("RELEASE_STORE_FILE").get()
            val _storePass = providers.gradleProperty("RELEASE_STORE_PASSWORD").get()
            val _keyAlias = providers.gradleProperty("RELEASE_KEY_ALIAS").get()
            val _keyPass = providers.gradleProperty("RELEASE_KEY_PASSWORD").get()
            println("Key file path: $keyFilePath")
            println("Has signing: $hasSigning")
            println("Store password: ${_storePass}")
            println("Key alias: ${_keyAlias}")
            println("Key password: ${_keyPass}")
            if (hasSigning) {
                storeFile = rootProject.file(keyFilePath)
                storePassword = _storePass
                keyAlias = _keyAlias
                keyPassword = _keyPass
            }
        }
    }

    buildTypes {
        release {
            if (providers.gradleProperty("RELEASE_STORE_PASSWORD").isPresent) {
                signingConfig = signingConfigs.getByName("release")
            }
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}