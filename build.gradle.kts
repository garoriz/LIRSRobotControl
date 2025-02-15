// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    apply(from = "https://github.com/rosjava/android_core/raw/kinetic/buildscript.gradle")

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        classpath("com.android.tools.build:gradle:4.2.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

subprojects {
    apply(plugin = "ros-android")

    afterEvaluate {
        extensions.findByType<com.android.build.gradle.BaseExtension>()?.apply {
            packagingOptions {
                excludes.add("META-INF/LICENSE.txt")
                excludes.add("META-INF/NOTICE.txt")
            }
        }
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}
