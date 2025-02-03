package com.garif.engineer_mobile_control.model;

public class PackageRemoteControl {

    static volatile byte[] packet;

    static {
        packet = createPackage();
    }

    public Axis getAxis(int number) {
        return new Axis(number);
    }

    public Button getButton(int number) {
        return new Button(number);
    }

    private static byte[] createPackage() {
        byte[] newPacket = new byte[Constants.REMOTE_CONTROL_PACKET_SIZE];
        newPacket[Constants.FRAME_TYPE_POSITION] = Constants.REMOTE_CONTROL_PACKET_ID;
        return newPacket;
    }

    class Button {

        private final int position;

        private Button(int number) {
            position = number + 33;
        }

        /**
         * Принимаемый параметр - значения 0 или 1
         */

        public void togglePackage(boolean toggle) {
            packet[position] = toggle ? (byte) 1 : (byte) 0;
        }
    }

    class Axis {

        private final int position;
        private byte valueFirst, valueSecond;
        private short speed;

        private Axis(int number) {
            position = (number + 1) * 2;
        }

        /**
         * @param speed - скорость (от -32768 до +32767)
         */
        public void setSpeed(short speed) {
            this.speed = speed;
            setDirection(true);
        }

        public void setDirection(boolean direction) {
            short speed = this.speed;
            if (!direction)
                speed *= -1;
            valueFirst = (byte) (speed & 0xFF);
            valueSecond = (byte) ((speed >> 8) & 0xFF);
        }

        public void updatePackage() {
            packet[position - 1] = valueFirst;
            packet[position] = valueSecond;
        }

        public void nullifyPackage() {
            packet[position - 1] = 0;
            packet[position] = 0;
        }
    }
}
