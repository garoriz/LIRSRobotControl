object Plugins {
    val androidApplication by lazy { "com.android.application" }
    val kotlinAndroid by lazy { "kotlin-android" }
}

object Dependencies {
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.CORE_KTX}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.APPCOMPAT}" }
    val material by lazy { "com.google.android.material:material:${Versions.MATERIAL}" }
    val activity by lazy { "androidx.activity:activity:${Versions.ACTIVITY}" }
    val constraintlayout by lazy {
        "androidx.constraintlayout:constraintlayout:" +
                Versions.CONSTRAINTLAYOUT
    }
    val navigationFragmentKtx by lazy {
        "androidx.navigation:navigation-fragment-ktx:" +
                Versions.NAVIGATION_FRAGMENT_KTX
    }
    val navigationUIKtx by lazy {
        "androidx.navigation:navigation-ui-ktx:" +
                Versions.NAVIGATION_UI_KTX
    }
    val AndroidCoreComponents by lazy { "androidx.activity:activity:${Versions.ACTIVITY}" }
}
