package com.garif.engineer_mobile_control.ui.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.databinding.ActivityEngineerMobileControlBinding
import com.garif.engineer_mobile_control.databinding.AppBarMainBinding
import com.garif.engineer_mobile_control.model.Client
import com.garif.engineer_mobile_control.model.ClientService
import com.garif.engineer_mobile_control.model.Constants.ADDRESS_CLIENT_DHCP
import com.garif.engineer_mobile_control.ui.fragments.ConnectionDialogFragment
import com.garif.engineer_mobile_control.ui.fragments.InfoFragment
import com.garif.engineer_mobile_control.ui.fragments.JointsFragment
import com.garif.engineer_mobile_control.ui.fragments.MovementFragment
import com.garif.engineer_mobile_control.utils.listeners.OneActionCheckedChangeListener
import com.garif.engineer_mobile_control.utils.listeners.OneDiscloseCheckedChangeListener
import com.garif.engineer_mobile_control.utils.other.DrawerHandler
import com.garif.engineer_mobile_control.utils.other.InetAddressConverter
import com.garif.engineer_mobile_control.utils.permissions.PermissionCallback
import com.garif.engineer_mobile_control.utils.permissions.PermissionCheckerUtils
import com.garif.engineer_mobile_control.utils.permissions.PermissionCheckerUtils.RuntimePermissions.PERMISSION_REQUEST_ACCESS_WIFI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.parceler.Parcels
import veg.mediaplayer.sdk.MediaPlayer
import veg.mediaplayer.sdk.MediaPlayerConfig
import java.net.InetAddress
import java.nio.ByteBuffer

class EngineerMobileControlActivity : AppCompatActivity(), MediaPlayer.MediaPlayerCallback, PermissionCallback {

    private lateinit var serviceIntent: Intent
    private lateinit var fragmentInfo: Fragment
    private lateinit var fragmentJoints: Fragment
    private lateinit var fragmentMovement: Fragment
    private lateinit var drawerHandler: DrawerHandler
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var mediaConfig: MediaPlayerConfig
    private lateinit var connectionDialog: DialogFragment
    private lateinit var wifiManager: WifiManager
    private lateinit var wifiInfo: WifiInfo
    private lateinit var permissionChecker: PermissionCheckerUtils
    private var toolbar: Toolbar? = null
    private var bottomNavigation: BottomNavigationView? = null
    private var video: MediaPlayer? = null
    private var drawerNavigation: DrawerLayout? = null
    private var navigation: NavigationView? = null
    private var activityMainBinding: ActivityEngineerMobileControlBinding? = null
    private var appBarMainBinding: AppBarMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityEngineerMobileControlBinding.inflate(layoutInflater)
        appBarMainBinding = AppBarMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_engineer_mobile_control)
        setViews()
        setSupportActionBar(toolbar)
        init()

        if (checkConnection()) bind()
        else showDialog()

        context = applicationContext
        actionListener = OneActionCheckedChangeListener()
        discloseListener = OneDiscloseCheckedChangeListener()
    }

    override fun onRestart() {
        super.onRestart()
        if (checkConnection()) bind()
        Log.d("Logs", "Activity is restarted")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Logs", "Activity is started")
    }

    override fun onResume() {
        super.onResume()
        //Если сервис уже запущен, то ничего не делать
        Log.d("Logs", "Activity is resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Logs", "Activity is paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Logs", "Activity is stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        unbind()
        Log.d("Logs", "Activity is destroyed")
    }

    override fun permissionGranted(permission: PermissionCheckerUtils.RuntimePermissions) {
        Log.i("Logs", "Разрешения получены")
        if (checkConnection()) bind()
        else showDialog()
    }

    override fun permissionDenied(permission: PermissionCheckerUtils.RuntimePermissions) {
        Log.i("Logs", "Разрешения отклонены")
    }

    override fun onBackPressed() {
        if (activityMainBinding?.drawerNavigation?.isDrawerOpen(GravityCompat.START) == true) {
            activityMainBinding?.drawerNavigation?.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.connection -> showDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_ACCESS_WIFI.value)
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                permissionGranted(PERMISSION_REQUEST_ACCESS_WIFI)
            else
                permissionDenied(PERMISSION_REQUEST_ACCESS_WIFI)
    }

    override fun OnReceiveData(p0: ByteBuffer, p1: Int, p2: Long): Int = 0

    override fun Status(p0: Int): Int = 0

    private fun setViews() {
        toolbar = findViewById(R.id.toolbar)
        video = findViewById(R.id.video)
        drawerNavigation = findViewById(R.id.drawer_navigation)
        navigation = findViewById(R.id.navigation)
        bottomNavigation = findViewById(R.id.bottom_navigation)
    }

    private fun init() {
        initContentViews()
        initTools()
    }

    private fun initTools() {
        initService()
        initMediaPlayer()
        initPermissionChecker()
    }

    private fun initService() {
        serviceIntent = Intent(this, ClientService::class.java)
        serviceIntent.putExtra("client", Parcels.wrap(Client()))
    }

    private fun initMediaPlayer() {
        mediaConfig = MediaPlayerConfig()
        mediaConfig.connectionUrl = "rtsp://10.42.0.1:8554/zoom"
        mediaConfig.decodingType = 0
    }

    private fun initPermissionChecker() {
        permissionChecker = PermissionCheckerUtils()
    }

    private fun initContentViews() {
        initDrawerViews()
        initDrawerActionBar()
        initBottomNavigation()
        initFragments()
        initDialogs()
    }

    private fun initDrawerViews() {
        drawerHandler = DrawerHandler()
        activityMainBinding?.navigation?.let { drawerHandler.handle(it.menu) }
    }

    private fun initDrawerActionBar() {
        toggle = ActionBarDrawerToggle(
            this,
            drawerNavigation,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerNavigation?.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initBottomNavigation() {
        bottomNavigation
            ?.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.item_bottom_nav_joints -> {
                        loadFragment(fragmentJoints)
                        video?.visibility = View.VISIBLE
                    }

                    R.id.item_bottom_nav_movement -> {
                        loadFragment(fragmentMovement)
                        video?.visibility = View.VISIBLE
                    }

                    R.id.item_bottom_nav_info -> {
                        video?.visibility = View.GONE
                        loadFragment(fragmentInfo)
                    }
                }
                true
            }
    }

    private fun initDialogs() {
        connectionDialog = ConnectionDialogFragment()
    }

    private fun initFragments() {
        fragmentInfo = InfoFragment()
        fragmentJoints = JointsFragment()
        fragmentMovement = MovementFragment()
        loadFragment(fragmentMovement)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
    }

    private fun bind() {
        Log.d("Logs", "connection is using")
        startService(serviceIntent)
        video?.Open(mediaConfig, this)
        video?.Play()
    }

    private fun unbind() {
        stopService(serviceIntent)
        video?.Close()
        video?.onDestroy()
    }

    private fun showDialog() =
        connectionDialog.show(supportFragmentManager, "connection")

    private fun checkConnection(): Boolean {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager.isWifiEnabled) {
            wifiInfo = wifiManager.connectionInfo
            if (wifiInfo.networkId != -1) {
                val inetConverter = InetAddressConverter()
                val ipString = inetConverter.fromIntToString(wifiInfo.ipAddress)
                ADDRESS_CLIENT_DHCP = InetAddress.getByName(ipString)
                Log.d("Logs", wifiInfo.ssid)
                Log.d("Logs", ipString)
                return true
            }
        }
        return false
    }

    companion object {
        lateinit var actionListener: OneActionCheckedChangeListener
        lateinit var discloseListener: OneDiscloseCheckedChangeListener
        lateinit var context: Context
    }
}
