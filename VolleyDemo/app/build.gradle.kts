plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.dipak.volleydemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dipak.volleydemo"
        minSdk = 21
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("com.android.volley:volley:1.2.1")

//    convert json to class
    implementation ("com.google.code.gson:gson:2.8.9")

    //load image from url
    implementation ("com.squareup.picasso:picasso:2.71828")

    //guide dep1
    implementation ("com.getkeepsafe.taptargetview:taptargetview:1.13.3")

    //guide dep2
    implementation ("com.github.amlcurran.showcaseview:library:5.4.3")

    implementation ("jp.wasabeef:blurry:4.0.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //firebase notification dep
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging:23.4.1")

    implementation("com.pubnub:pubnub-gson:6.4.5")




}