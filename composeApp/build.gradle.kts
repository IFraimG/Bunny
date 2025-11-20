import org.gradle.kotlin.dsl.implementation
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            // default
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // tink
            implementation("com.google.crypto.tink:tink-android:1.19.0")

            // sqllite
            implementation(libs.androidx.room.sqlite.wrapper)
        }
        commonMain.dependencies {
            // default
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

            // koin
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0"))
            implementation("io.insert-koin:koin-androidx-compose:4.1.0")
            implementation(libs.koin.core)

            // navigation compose
            implementation(libs.navigation.compose)

            // datastore
            implementation("androidx.datastore:datastore-preferences:1.1.7")
            implementation("androidx.datastore:datastore:1.1.7")

            // room, sqllite
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)

            // sqlcipher
            implementation("net.zetetic:sqlcipher-android:4.11.0")

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.xmis.bunny"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.xmis.bunny"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {
    debugImplementation(compose.uiTooling)

    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
//    add("kspIosX64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
}

