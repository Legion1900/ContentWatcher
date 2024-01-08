plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.realm)
}

android {
    namespace = "com.legion1900.game_list_feature"
    compileSdk = 33

    buildFeatures.viewBinding = true

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
    implementation(project(":base"))
    implementation(project(":network"))
    implementation(project(":database"))
    implementation(libs.androidx.paging)
    implementation(libs.google.android.material)
    implementation(libs.glide)
}
