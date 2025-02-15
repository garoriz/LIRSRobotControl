plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.garif.lirsrobotcontrol"
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        applicationId = "com.garif.lirsrobotcontrol"
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = ConfigData.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName(ConfigData.RELEASE_BUILD_TYPE_NAME) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(ConfigData.PROGUARD_ANDROID_OPTIMIZE_TXT),
                ConfigData.PROGUARD_RULES_PRO
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    repositories {
        maven(url = ConfigData.JITPACK_IO_URL)
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.activity)
    implementation(Dependencies.constraintlayout)
    implementation(Dependencies.navigationFragmentKtx)
    implementation(Dependencies.navigationUIKtx)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation(project(":robot-selection-feature"))
    implementation(project(":engineer-mobile-control-feature"))
    implementation(project(":pmb2-control-feature"))
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
