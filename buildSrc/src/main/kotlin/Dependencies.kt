object Plugins {
    val androidApplication by lazy { "com.android.application" }
    val kotlinAndroid by lazy { "kotlin-android" }
    val androidLibrary by lazy { "com.android.library" }
    val kotlinKapt by lazy { "kotlin-kapt" }
}

object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.APPCOMPAT}" }
    val material by lazy { "com.google.android.material:material:${Versions.MATERIAL}" }
    val activity by lazy { "androidx.activity:activity:${Versions.ACTIVITY}" }
    val constraintlayout by lazy {
        "androidx.constraintlayout:constraintlayout:" + Versions.CONSTRAINTLAYOUT
    }
    val navigationFragmentKtx by lazy {
        "androidx.navigation:navigation-fragment-ktx:" + Versions.NAVIGATION_FRAGMENT_KTX
    }
    val navigationUIKtx by lazy {
        "androidx.navigation:navigation-ui-ktx:" + Versions.NAVIGATION_UI_KTX
    }
    val junit by lazy { "junit:junit:${Versions.JUNIT}" }
    val androidJunit by lazy { "androidx.test.ext:junit:${Versions.ANDROID_JUNIT}" }
    val espressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.ESPRESSO_CORE}" }
    val commonsLogging by lazy { "commons-logging:commons-logging:${Versions.COMMONS_LOGGING}" }
    val commonsNet by lazy { "commons-net:commons-net:${Versions.COMMONS_NET}" }
    val commonsCodec by lazy { "commons-codec:commons-codec:${Versions.COMMONS_CODEC}" }
    val commonsIO by lazy { "commons-io:commons-io:${Versions.COMMONS_IO}" }
    val commonsLang by lazy { "commons-lang:commons-lang:${Versions.COMMONS_LANG}" }
    val commonsHttpclient by lazy {
        "commons-httpclient:commons-httpclient:" + Versions.COMMONS_HTTPCLIENT
    }
    val fragmentKtx by lazy {
        "androidx.navigation:navigation-fragment-ktx:" + Versions.FRAGMENT_KTX
    }
    val parcelerApi by lazy { "org.parceler:parceler-api:${Versions.PARCELER_API}" }
    val parceler by lazy { "org.parceler:parceler:${Versions.PARCELER}" }
    val androidCoreComponents by lazy {
        "org.ros.android_core:android_core_components:" + Versions.ANDROID_CORE_COMPONENTS
    }
    val androidRemoconsCommonTools by lazy {
        "com.github.rosjava.android_remocons:common_tools:" + Versions.ANDROID_REMOCONS_COMMON_TOOLS
    }
    val rosjavaGeometry by lazy {
        "org.ros.rosjava_core:rosjava_geometry:" + Versions.ROSJAVA_GEOMETRY
    }
    val actionlibMsgs by lazy {
        "org.ros.rosjava_messages:actionlib_msgs:" + Versions.ACTIONLIB_MSGS
    }
    val geometryMsgs by lazy { "org.ros.rosjava_messages:geometry_msgs:${Versions.GEOMETRY_MSGS}" }
    val stdMsgs by lazy { "org.ros.rosjava_messages:std_msgs:${Versions.STD_MSGS}" }
    val mapStore by lazy { "org.ros.rosjava_messages:map_store:${Versions.MAP_STORE}" }
    val moveBaseMsgs by lazy {
        "org.ros.rosjava_messages:move_base_msgs:${Versions.MOVE_BASE_MSGS}"
    }
    val worldCanvasMsgs by lazy {
        "org.ros.rosjava_messages:world_canvas_msgs:${Versions.WORLD_CANVAS_MSGS}"
    }
    val virtualJoystickAndroid by lazy {
        "com.github.controlwear:virtual-joystick-android:${Versions.VIRTUAL_JOYSTICK_ANDROID}"
    }
    val guava by lazy { "com.google.guava:guava:${Versions.GUAVA}" }
    val coil by lazy { "io.coil-kt:coil:${Versions.COIL}" }

    val apacheCommonsGroup by lazy { "org.apache.commons" }
    val apacheCommonsLogging by lazy { "com.springsource.org.apache.commons.logging" }
    val apacheCommonsNet by lazy { "com.springsource.org.apache.commons.net" }
    val apacheCommonsCodec by lazy { "com.springsource.org.apache.commons.codec" }
    val apacheCommonsIO by lazy { "com.springsource.org.apache.commons.io" }
    val apacheCommonsLang by lazy { "com.springsource.org.apache.commons.lang" }
    val apacheCommonsHttpClient by lazy { "com.springsource.org.apache.commons.httpclient" }
}
