plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.garif.aurora_unior_control_feature"
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
    repositories {
        maven(url = ConfigData.JITPACK_IO_URL)
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.material)
    implementation(Dependencies.activity)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espressoCore)
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == Dependencies.apacheCommonsGroup) {
            if (requested.name.contains(Dependencies.apacheCommonsLogging)) {
                useTarget(Dependencies.commonsLogging)
            }

            if (requested.name.contains(Dependencies.apacheCommonsNet)) {
                useTarget(Dependencies.commonsNet)
            }

            if (requested.name.contains(Dependencies.apacheCommonsCodec)) {
                useTarget(Dependencies.commonsCodec)
            }

            if (requested.name.contains(Dependencies.apacheCommonsIO)) {
                useTarget(Dependencies.commonsIO)
            }

            if (requested.name.contains(Dependencies.apacheCommonsLang)) {
                useTarget(Dependencies.commonsLang)
            }

            if (requested.name.contains(Dependencies.apacheCommonsHttpClient)) {
                useTarget(Dependencies.commonsHttpclient)
            }
        }
    }
}
