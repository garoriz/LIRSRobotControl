package com.garif.engineer_mobile_control.model

object PackageManager {

    private val control: PackageRemoteControl

    //Axis 0 - Вращение (P.S. Отрицательное число - влево)
    private val axis0: PackageRemoteControl.Axis

    //Axis 1 - Движение вперёд/назад (P.S. Отрицательное число - вперёд)
    private val axis1: PackageRemoteControl.Axis

    //Axis 4 - Комбинация neck + elbow (P.S. Отрицательное число - наверх)
    private val axis4: PackageRemoteControl.Axis

    //Axis 5 - Поднять флипперыы (P.S. Для работы достаточно значения 1)
    private val axis5: PackageRemoteControl.Axis

    //Button 0 - Отпустить гриппером
    private val button0: PackageRemoteControl.Button

    //Button 1 - Фонарик
    private val button1: PackageRemoteControl.Button

    //Button 3 - Схватить гриппером
    private val button3: PackageRemoteControl.Button

    //Button 5 - Опустить флипперы
    private val button5: PackageRemoteControl.Button

    //Button 9 - Работа с waist/shoulder. Используется в паре с:
    //1) Axis 0 - вращать waist (P.S. Отрицательное число - влево),
    //2) Axis 1 - Поднять/опустить shoulder (P.S. Отрицательное число - вниз)
    private val button9: PackageRemoteControl.Button
    private var axisWaist: PackageRemoteControl.Axis
    private var axisShoulder: PackageRemoteControl.Axis

    //Button 10 - Двигать neck. Используется в паре с:
    //1) Axis 4 - движение вперёд/назад только neck (P.S. Отрицательное число - вперёд)
    private val button10: PackageRemoteControl.Button
    private var axisNeck: PackageRemoteControl.Axis

    init {
        control = PackageRemoteControl()

        axis0 = control.getAxis(0)
        axis1 = control.getAxis(1)
        axis4 = control.getAxis(4)
        axis5 = control.getAxis(5)

        button0 = control.getButton(0)
        button1 = control.getButton(1)
        button3 = control.getButton(3)
        button5 = control.getButton(5)
        button9 = control.getButton(9)
        button10 = control.getButton(10)

        axisNeck = control.getAxis(4)
        axisWaist = control.getAxis(0)
        axisShoulder = control.getAxis(1)

        setDefaultSpeeds()
    }

    /**
     * Activate Joints
     * Direction - направление движения joints:
     * @true - для всех, кроме "Shoulder": вправо/назад/вниз,
     * для "Shoulder" - наверх
     */

    fun turnTorch() = button1.togglePackage(true)

    fun startOpenGripper() = button0.togglePackage(true)

    fun startCloseGripper() = button3.togglePackage(true)

    fun startFlippersDown() = button5.togglePackage(true)

    fun startFlippersUp() = axis5.updatePackage()

    fun startRotate(direction: Boolean) {
        axis0.setDirection(direction)
        axis0.updatePackage()
    }

    fun startMove(direction: Boolean) {
        axis1.setDirection(direction)
        axis1.updatePackage()
    }

    fun startWaist(direction: Boolean) {
        button9.togglePackage(true)
        axisWaist.setDirection(direction)
        axisWaist.updatePackage()
    }

    fun startShoulder(direction: Boolean) {
        button9.togglePackage(true)
        axisShoulder.setDirection(direction)
        axisShoulder.updatePackage()
    }

    fun startElbow(direction: Boolean) {
        axis4.setDirection(direction)
        axis4.updatePackage()
    }

    fun startNeck(direction: Boolean) {
        button10.togglePackage(true)
        axisNeck.setDirection(direction)
        axisNeck.updatePackage()
    }

    /**
     * Deactivate Joints
     */

    fun resetTorchPackage() = button1.togglePackage(false)

    fun stopOpenGripper() = button0.togglePackage(false)

    fun stopCloseGripper() = button3.togglePackage(false)

    fun stopFlippersDown() = button5.togglePackage(false)

    fun stopFlippersUp() = axis5.nullifyPackage()

    fun stopRotate() = axis0.nullifyPackage()

    fun stopMove() = axis1.nullifyPackage()

    fun stopWaist() {
        axisWaist.nullifyPackage()
        button9.togglePackage(false)
    }

    fun stopShoulder() {
        axisShoulder.nullifyPackage()
        button9.togglePackage(false)
    }

    fun stopElbow() = axis4.nullifyPackage()

    fun stopNeck() {
        axisNeck.nullifyPackage()
        button10.togglePackage(false)
    }

    fun stopAll() {
        stopOpenGripper()
        stopCloseGripper()
        stopFlippersDown()
        stopFlippersUp()
        stopRotate()
        stopMove()
        stopWaist()
        stopShoulder()
        stopElbow()
        stopNeck()
    }

    /**
     * Set Joints' speed
     */

    fun setRotateSpeed(speed: Int) = axis0.setSpeed(speedConvert(speed))

    fun setMoveSpeed(speed: Int) = axis1.setSpeed(speedConvert(speed))

    fun setWaistSpeed(speed: Int) = axisWaist.setSpeed(speedConvert(speed))

    fun setShoulderSpeed(speed: Int) = axisShoulder.setSpeed(speedConvert(speed))

    fun setElbowSpeed(speed: Int) = axis4.setSpeed(speedConvert(speed))

    fun setNeckSpeed(speed: Int) = axisNeck.setSpeed(speedConvert(speed))

    private fun setDefaultSpeeds() {
        axis5.setSpeed(1)
        axis0.setSpeed(Constants.SPEED_DEFAULT)
        axis1.setSpeed(Constants.SPEED_DEFAULT)
        axis4.setSpeed(Constants.SPEED_DEFAULT)
        axisNeck.setSpeed(Constants.SPEED_DEFAULT)
        axisWaist.setSpeed(Constants.SPEED_DEFAULT)
        axisShoulder.setSpeed(Constants.SPEED_DEFAULT)
    }

    private fun speedConvert(speed: Int): Short {
        var castable: Int = speed
        castable *= Constants.SPEED_STEP
        return castable.toShort()
    }
}
