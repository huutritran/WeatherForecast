import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
}

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "apiKey.properties")))
}
val apiKey:String = prop.getProperty("API_KEY").toString()

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        applicationId = "com.example.weatherforecast"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        testInstrumentationRunner = ConfigData.testInstrumentationRunner

        buildConfigField("String","BASE_URL","\"https://api.openweathermap.org\"")
        buildConfigField("String","API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(Deps.roomRuntime)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt(Deps.roomCompiler)
    implementation(Deps.roomKtx)

    implementation(Deps.kotlin)

    implementation(Deps.navigationFragment)
    implementation(Deps.navigationUiKtx)

    implementation(Deps.arrowCore)

    implementation(Deps.hilt)
    kapt(Deps.hiltCompiler)

    implementation(Deps.ktx)
    implementation(Deps.fragmentKtx)
    implementation(Deps.androidLifeCycleExt)

    implementation(Deps.coroutine)
    implementation(Deps.androidCoroutine)

    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonConverter)
    implementation(Deps.retrofitCoroutineAdapter)

    implementation(Deps.appCompat)
    implementation(Deps.material)
    implementation(Deps.constraintLayout)

    testImplementation(Deps.jUnit)
    testImplementation(Deps.mockk)
    testImplementation(Deps.mockkCommon)
    testImplementation(Deps.coroutinesTest)
    testImplementation(Deps.kotest)
    testImplementation(Deps.archCoreTest)
    androidTestImplementation(Deps.androidJUnit)
    androidTestImplementation(Deps.espresso)
}

kapt {
    correctErrorTypes = true
}