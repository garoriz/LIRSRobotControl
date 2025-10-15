plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.garif.core"
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigData.MIN_SDK

        testInstrumentationRunner = ConfigData.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(ConfigData.CONSUMER_RULES_PRO)
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espressoCore)
}
