plugins {
    /*alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)*/
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.garif.pmb2_control_feature"
    compileSdk = 30

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    /*kotlinOptions {
        jvmTarget = "1.8"
    }*/
    repositories {
        maven(url = "https://jitpack.io")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("org.ros.android_core:android_core_components:0.4.0")
    implementation("com.github.rosjava.android_remocons:common_tools:0.3.0")
    implementation("org.ros.rosjava_core:rosjava_geometry:0.3.6")
    implementation("org.ros.rosjava_messages:actionlib_msgs:1.12.7")
    implementation("org.ros.rosjava_messages:geometry_msgs:1.12.7")
    implementation("org.ros.rosjava_messages:std_msgs:0.5.11")
    implementation("org.ros.rosjava_messages:map_store:0.3.1")
    implementation("org.ros.rosjava_messages:move_base_msgs:[1.12,1.13)")
    implementation("org.ros.rosjava_messages:world_canvas_msgs:0.2.0")
    implementation("com.github.controlwear:virtual-joystick-android:master")
    implementation("com.google.guava:guava:31.1-jre")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.logging")
        ) {
            useTarget("commons-logging:commons-logging:1.2")
        }
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.net")
        ) {
            useTarget("commons-net:commons-net:3.9.0")
        }
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.codec")
        ) {
            useTarget("commons-codec:commons-codec:1.15")
        }
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.io")
        ) {
            useTarget("commons-io:commons-io:2.8.0")
        }
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.lang")
        ) {
            useTarget("commons-lang:commons-lang:2.6")
        }
        if (requested.group == "org.apache.commons" &&
            requested.name.contains("com.springsource.org.apache.commons.httpclient")
        ) {
            useTarget("commons-httpclient:commons-httpclient:3.1")
        }
    }
}
