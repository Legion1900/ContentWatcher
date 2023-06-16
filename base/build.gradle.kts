plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "com.legion1900.base"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    api(libs.androidx.core)
    api(libs.androidx.appcompat)

    api(libs.coroutines)
    api(libs.koin)

    api(libs.viewBindingDelegate)

    api(project(":navigation"))
}
