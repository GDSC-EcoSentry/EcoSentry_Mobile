plugins {
    id("com.android.application")
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

    // Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}