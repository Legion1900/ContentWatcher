plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "com.legion1900.contentwatcher"
    compileSdk = 34

    buildFeatures.viewBinding = true

    defaultConfig {
        applicationId = "com.legion1900.contentwatcher"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.google.android.material)
    implementation(libs.androidx.constraintlayout)

    implementation(project(":base"))
    implementation(project(":routes"))
    implementation(project(":network"))
    implementation(project(":database"))
    implementation(project(":game_list_feature"))
}
