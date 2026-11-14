plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android { namespace = "org.example.rootcommander"; compileSdk = 35
 defaultConfig { applicationId = "org.example.rootcommander"; minSdk = 24; targetSdk = 34; versionCode = 14; versionName = "1.4" }
 compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
 kotlinOptions { jvmTarget = "17" }
}