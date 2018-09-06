package com.qmx.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;

/**
 * Created by zcl on 2018/8/19.
 */
public class AmountUtil {
    public AmountUtil() {
    }

    public static String convertDollar2Cent(String str) {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuffer sb = df.format(Double.parseDouble(str), new StringBuffer(), new FieldPosition(0));
        int idx = sb.toString().indexOf(".");
        sb.deleteCharAt(idx);

        while(sb.length() != 1 && sb.charAt(0) == 48) {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    public static String convertCent2Dollar(String s) {
        if(!"".equals(s) && s != null) {
            if(s.length() != 0) {
                if(s.charAt(0) == 43) {
                    s = s.substring(1);
                }

                long l = Long.parseLong(s);
                boolean negative = false;
                if(l < 0L) {
                    negative = true;
                    l = Math.abs(l);
                }

                s = Long.toString(l);
                return s.length() == 1?(negative?"-0.0" + s:"0.0" + s):(s.length() == 2?(negative?"-0." + s:"0." + s):(negative?"-" + s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2):s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2)));
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String convertCent2DollarShort(String s) {
        String ss = convertCent2Dollar(s);
        ss = "" + Double.parseDouble(ss);
        return ss.endsWith(".0")?ss.substring(0, ss.length() - 2):(ss.endsWith(".00")?ss.substring(0, ss.length() - 3):ss);
    }
}
