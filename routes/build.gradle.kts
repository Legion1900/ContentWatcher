plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "com.legion1900.routes"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
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

dependencies {
    implementation(libs.androidx.core)
    implementation(project(":navigation"))
}
