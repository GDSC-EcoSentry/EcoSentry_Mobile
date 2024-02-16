plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.observers.ecosentry_mobile"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.observers.ecosentry_mobile"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
    sourceSets {
        getByName("main") {
            res {
                srcDirs(
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\authentication",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\toolbar",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\drawer",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\home",
                    "src\\main\\res",
                    "src\\main\\res\\layouts\\profile",
                    "src\\main\\res",
                    "src\\main\\res\\dashboard", "src\\main\\res", "src\\main\\res\\layouts\\dashboard"
                )
            }
        }
    }
}

dependencies {
// Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}