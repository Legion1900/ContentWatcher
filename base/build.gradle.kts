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
    api(libs.androidx.core)
    api(libs.androidx.appcompat)
    api(libs.androidx.fragment)
    api(libs.androidx.constraintlayout)
    api(libs.google.android.material)

    api(libs.coroutines)
    api(libs.androidx.coroutine.liveData)
    api(libs.koin)

    api(libs.viewBindingDelegate)

    api(project(":navigation"))
}
