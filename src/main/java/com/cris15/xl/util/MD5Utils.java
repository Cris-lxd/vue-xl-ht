package com.cris15.xl.util;

import java.security.MessageDigest;

/**
 * Author: Cris_liuxd
 * Date: 2021/06/28
 * Time: 15:47
 * Project: demotest
 **/
public class MD5Utils {
    /**
     * <一>
     * @param str
     * @return
     */
    public static String string2Md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            char[] charArray = str.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md5.digest(byteArray);

            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



    public static void main(String[] args) {
        String s = string2Md5("123");
        System.out.println(s);
    }
}