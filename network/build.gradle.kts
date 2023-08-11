import java.util.Properties

plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.android.kotlin)
}

val props by lazy { loadProps() }
val twitchClientId: String by lazy { "\"${props.getProperty("twitch_client_id")}\"" }
val twitchClientSecret: String by lazy { "\"${props.getProperty("twitch_client_secret")}\"" }

android {
    namespace = "com.legion1900.network"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes.all {
        buildConfigField(type = "String", value = twitchClientId, name = "TWITCH_CLIENT_ID")
        buildConfigField(type = "String", value = twitchClientSecret, name = "TWITCH_CLIENT_SECRET")
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
    implementation(project(":database"))
    implementation(project(":base"))

    implementation(libs.koin)
    implementation(libs.retrofit)
    implementation(libs.retrofit.logging)
    implementation(libs.retrofit.moshiAdapter)
    implementation(libs.retrofit.scalarAdapter)
    implementation(libs.moshi)

    debugImplementation(libs.chucker.debug)
    releaseImplementation(libs.chucker.release)
}
