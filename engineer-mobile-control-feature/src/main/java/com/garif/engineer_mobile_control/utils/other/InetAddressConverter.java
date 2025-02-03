package com.garif.engineer_mobile_control.utils.other;

import java.util.Locale;

public class InetAddressConverter {

    public String fromIntToString(int inetValue) {
        return String
                .format(
                        Locale.ENGLISH,
                        "%d.%d.%d.%d",
                        (inetValue & 0xff),
                        (inetValue >> 8 & 0xff),
                        (inetValue >> 16 & 0xff),
                        (inetValue >> 24 & 0xff));
    }
}
