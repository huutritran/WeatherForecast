object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val hilt by lazy { "com.google.dagger:hilt-android-gradle-plugin:2.38.1" }
    val navigation by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}" }
}

object Deps {
    // Room Database
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomVersion}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomVersion}" }
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.roomVersion}" }

    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }

    //Navigation
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUiKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }

    //Support Typed Functional Programming in Kotlin
    val arrowCore by lazy { "io.arrow-kt:arrow-core:${Versions.arrow}" }

    // Dependency injection
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    val ktx by lazy { "androidx.core:core-ktx:${Versions.ktx}" }
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }

    val androidLifeCycleExt by lazy { "androidx.lifecycle:lifecycle-extensions:${Versions.androidLifeCycleExt}" }

    // Coroutine
    val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}" }
    val androidCoroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.androidCoroutine}" }

    // Retrofit - RESTFul client
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val retrofitCoroutineAdapter by lazy { "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitCoroutineAdapter}" }

    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val material by lazy { "com.google.android.material:material:${Versions.material}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }

    // Test
    val jUnit by lazy { "junit:junit:${Versions.jUnit}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val mockkCommon by lazy { "io.mockk:mockk-common:${Versions.mockk}" }
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}" }
    val kotest by lazy { "io.kotest:kotest-assertions-core:${Versions.kotest}" }
    val archCoreTest by lazy { "androidx.arch.core:core-testing:${Versions.archCoreTest}" }
    val androidJUnit by lazy { "androidx.test.ext:junit:${Versions.androidJUnit}" }
    val espresso by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
}

