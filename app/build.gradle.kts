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
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidJunit)
    androidTestImplementation(Dependencies.espressoCore)
    implementation(project(":robot-selection-feature"))
    implementation(project(":engineer-mobile-control-feature"))
    implementation(project(":pmb2-control-feature"))
    implementation(project(":artbul-control-feature"))
    implementation(project(":avrora-unior-control-feature"))
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
