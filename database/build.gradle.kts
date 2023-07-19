import java.util.Properties

plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.realm)
}

android {
    namespace = "com.legion1900.database"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun loadProps(): Properties {
    val props = Properties()
    val propsFile = project.file("local.properties")
    props.load(propsFile.inputStream())
    return props
}

dependencies {
    implementation(libs.koin)
    api(libs.realm)
}
