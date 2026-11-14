plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android { namespace = "org.example.rootcommander"; compileSdk = 35
 defaultConfig { applicationId = "org.example.rootcommander"; minSdk = 26; targetSdk = 34; versionCode = 13; versionName = "1.3" }
 compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
 kotlinOptions { jvmTarget = "17" }
}