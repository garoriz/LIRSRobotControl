package com.garif.engineer_mobile_control.utils.other

import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.garif.engineer_mobile_control.R
import com.garif.engineer_mobile_control.model.PackageManager

class DrawerHandler {

    private lateinit var menu: Menu

    private var viewMove: View? = null
    private var viewRotate: View? = null
    private var viewNeck: View? = null
    private var viewShoulder: View? = null
    private var viewElbow: View? = null
    private var viewWaist: View? = null

    private var tvMove: TextView? = null
    private var tvRotate: TextView? = null
    private var tvNeck: TextView? = null
    private var tvShoulder: TextView? = null
    private var tvElbow: TextView? = null
    private var tvWaist: TextView? = null

    private var btnMoveMax: Button? = null
    private var btnMoveMin: Button? = null
    private var btnRotateMax: Button? = null
    private var btnRotateMin: Button? = null
    private var btnNeckMax: Button? = null
    private var btnNeckMin: Button? = null
    private var btnShoulderMax: Button? = null
    private var btnShoulderMin: Button? = null
    private var btnElbowMax: Button? = null
    private var btnElbowMin: Button? = null
    private var btnWaistMax: Button? = null
    private var btnWaistMin: Button? = null

    private var seekBarMove: SeekBar? = null
    private var seekBarRotate: SeekBar? = null
    private var seekBarNeck: SeekBar? = null
    private var seekBarShoulder: SeekBar? = null
    private var seekBarElbow: SeekBar? = null
    private var seekBarWaist: SeekBar? = null

    private lateinit var seekBarListener: SeekBar.OnSeekBarChangeListener

    fun handle(menu: Menu) {
        initMenu(menu)
        initViews()
        initListeners()
    }

    private fun initMenu(menu: Menu) {
        this.menu = menu
    }

    private fun initViews() {
        initActionViews()
        initTextViews()
        initButtons()
        initSeekBars()
    }

    private fun initListeners() {
        initButtonListeners()
        initSeekBarListeners()
    }

    private fun initActionViews() {
        viewMove = menu.findItem(R.id.item_drawer_nav_movement).actionView
        viewRotate = menu.findItem(R.id.item_drawer_nav_rotation).actionView
        viewNeck = menu.findItem(R.id.item_drawer_nav_neck).actionView
        viewShoulder = menu.findItem(R.id.item_drawer_nav_shoulder).actionView
        viewElbow = menu.findItem(R.id.item_drawer_nav_elbow).actionView
        viewWaist = menu.findItem(R.id.item_drawer_nav_waist).actionView
    }

    private fun initTextViews() {
        tvMove = viewMove?.findViewById(R.id.tv_value_current)
        tvRotate = viewRotate?.findViewById(R.id.tv_value_current)
        tvNeck = viewNeck?.findViewById(R.id.tv_value_current)
        tvShoulder = viewShoulder?.findViewById(R.id.tv_value_current)
        tvElbow = viewElbow?.findViewById(R.id.tv_value_current)
        tvWaist = viewWaist?.findViewById(R.id.tv_value_current)
    }

    private fun initButtons() {
        btnMoveMin = viewMove?.findViewById(R.id.btn_value_min)
        btnMoveMax = viewMove?.findViewById(R.id.btn_value_max)
        btnRotateMin = viewRotate?.findViewById(R.id.btn_value_min)
        btnRotateMax = viewRotate?.findViewById(R.id.btn_value_max)
        btnNeckMin = viewNeck?.findViewById(R.id.btn_value_min)
        btnNeckMax = viewNeck?.findViewById(R.id.btn_value_max)
        btnShoulderMin = viewShoulder?.findViewById(R.id.btn_value_min)
        btnShoulderMax = viewShoulder?.findViewById(R.id.btn_value_max)
        btnElbowMin = viewElbow?.findViewById(R.id.btn_value_min)
        btnElbowMax = viewElbow?.findViewById(R.id.btn_value_max)
        btnWaistMin = viewWaist?.findViewById(R.id.btn_value_min)
        btnWaistMax = viewWaist?.findViewById(R.id.btn_value_max)
    }

    private fun initSeekBars() {
        seekBarMove = viewMove?.findViewById(R.id.seekBar)
        seekBarRotate = viewRotate?.findViewById(R.id.seekBar)
        seekBarNeck = viewNeck?.findViewById(R.id.seekBar)
        seekBarShoulder = viewShoulder?.findViewById(R.id.seekBar)
        seekBarElbow = viewElbow?.findViewById(R.id.seekBar)
        seekBarWaist = viewWaist?.findViewById(R.id.seekBar)
    }

    private fun initButtonListeners() {
        btnMoveMin?.setOnClickListener {
            seekBarMove?.progress = seekBarMove?.progress?.minus(1) ?: 0
        }
        btnMoveMax?.setOnClickListener {
            seekBarMove?.progress = seekBarMove?.progress?.plus(1) ?: 0
        }
        btnRotateMin?.setOnClickListener {
            seekBarRotate?.progress = seekBarRotate?.progress?.minus(1) ?: 0
        }
        btnRotateMax?.setOnClickListener {
            seekBarRotate?.progress = seekBarRotate?.progress?.plus(1) ?: 0
        }
        btnNeckMin?.setOnClickListener {
            seekBarNeck?.progress = seekBarNeck?.progress?.minus(1) ?: 0
        }
        btnNeckMax?.setOnClickListener {
            seekBarNeck?.progress = seekBarNeck?.progress?.plus(1) ?: 0
        }
        btnShoulderMin?.setOnClickListener {
            seekBarShoulder?.progress = seekBarShoulder?.progress?.minus(1) ?: 0
        }
        btnShoulderMax?.setOnClickListener {
            seekBarShoulder?.progress = seekBarShoulder?.progress?.plus(1) ?: 0
        }
        btnElbowMin?.setOnClickListener {
            seekBarElbow?.progress = seekBarElbow?.progress?.minus(1) ?: 0
        }
        btnElbowMax?.setOnClickListener {
            seekBarElbow?.progress = seekBarElbow?.progress?.plus(1) ?: 0
        }
        btnWaistMin?.setOnClickListener {
            seekBarWaist?.progress = seekBarWaist?.progress?.minus(1) ?: 0
        }
        btnWaistMax?.setOnClickListener {
            seekBarWaist?.progress = seekBarWaist?.progress?.plus(1) ?: 0
        }
    }

    private fun initSeekBarListeners() {
        seekBarListener = SeekBarChangeListener()
        seekBarMove?.setOnSeekBarChangeListener(seekBarListener)
        seekBarRotate?.setOnSeekBarChangeListener(seekBarListener)
        seekBarNeck?.setOnSeekBarChangeListener(seekBarListener)
        seekBarShoulder?.setOnSeekBarChangeListener(seekBarListener)
        seekBarElbow?.setOnSeekBarChangeListener(seekBarListener)
        seekBarWaist?.setOnSeekBarChangeListener(seekBarListener)
    }

    private inner class SeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            when (seekBar) {
                seekBarMove -> {
                    tvMove?.text = "$progress"
                    PackageManager.setMoveSpeed(progress)
                }

                seekBarRotate -> {
                    tvRotate?.text = "$progress"
                    PackageManager.setRotateSpeed(progress)
                }

                seekBarNeck -> {
                    tvNeck?.text = "$progress"
                    PackageManager.setNeckSpeed(progress)
                }

                seekBarShoulder -> {
                    tvShoulder?.text = "$progress"
                    PackageManager.setShoulderSpeed(progress)
                }

                seekBarElbow -> {
                    tvElbow?.text = "$progress"
                    PackageManager.setElbowSpeed(progress)
                }

                seekBarWaist -> {
                    tvWaist?.text = "$progress"
                    PackageManager.setWaistSpeed(progress)
                }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {}

        override fun onStopTrackingTouch(seekBar: SeekBar) {}
    }
}
