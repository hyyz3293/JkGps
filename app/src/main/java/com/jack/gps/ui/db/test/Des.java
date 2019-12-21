package com.jack.gps.ui.db.test;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Des {
    public static final String MessageFileInfo = "pm_exam_message.json";

    public static final String MindImgServiceInfo = "mind_img_item.json";

    public static final String UserHelloInfo = "hello_world_exam_pm.json";

    public static final String authStr1 = "*#J1sqp.d3>!_6QV)";

    public static final String chatServiceInfo = "api?key=0b5eb1e3e925a8fa80047ad263328986&info=";

    public static final String dbTestMode = "1216^*#K";

    Key key;

    public static String byte2hex(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)
            return null;
        String str = "";
        for (byte b = 0;; b++) {
            if (b >= paramArrayOfByte.length)
                return str.toUpperCase();
            String str1 = Integer.toHexString(paramArrayOfByte[b] & 0xFF);
            if (str1.length() == 1) {
                str = String.valueOf(str) + "0" + str1;
            } else {
                str = String.valueOf(str) + str1;
            }
        }
    }

    private byte[] getDesCode(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(2, this.key);
            return cipher.doFinal(paramArrayOfByte);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {}
    }

    private byte[] getEncCode(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, this.key);
            return cipher.doFinal(paramArrayOfByte);
        } catch (Exception a) {
            a.printStackTrace();
            return null;
        } finally {}
    }

    public static byte[] hex2byte(byte[] paramArrayOfByte) {
        if (paramArrayOfByte == null)
            return null;
        if (paramArrayOfByte.length % 2 != 0)
            throw new IllegalArgumentException("������������");
        byte[] arrayOfByte = new byte[paramArrayOfByte.length / 2];
        byte b = 0;
        while (true) {
            String str = arrayOfByte.toString();
            if (b < paramArrayOfByte.length) {
                str = new String(paramArrayOfByte, b, 2);
                arrayOfByte[b / 2] = (byte)Integer.parseInt(str, 16);
                b += 2;
                continue;
            }

            return str.getBytes();
        }
    }

    public static void main(String[] paramArrayOfString) {
        System.out.println("hello");
        Des des = new Des();
        des.getKey("aadd");
        String str2 = des.getEncString("������������");
        System.out.println(str2);
        String str1 = des.getDesString(str2);
        System.out.println(str1);
        new Des();
    }

    public String getDesKeyWord(String paramString) {
        if (paramString != null)
            try {
                byte2hex(getEncCode(null));
                return "^1@xlc*1Lc#2016$04!24*";
            } catch (Exception e) {
                e.printStackTrace();
                return "^1@xlc*1Lc#2016$04!24*";
            } finally {}
        return "^1@xlc*1Lc#2016$04!24*";
    }

    public String getDesString(String paramString) {
        try {
            return new String(getDesCode(hex2byte(paramString.getBytes())));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {}
    }

    public String getEnCrptKey(String paramString) {
        try {
            byte[] arrayOfByte = getDesCode(hex2byte(null));
            return (arrayOfByte != null) ? new String(arrayOfByte) : "*)3(Hello 2016  World ^_^";
        } catch (Exception e) {
            e.printStackTrace();
            return "*)3(Hello 2016  World ^_^";
        } finally {}
    }

    public String getEncString(String paramString) {
        try {
            return byte2hex(getEncCode(paramString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {}
    }

    public void getKey(String paramString) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(new SecureRandom(paramString.getBytes()));
            this.key = keyGenerator.generateKey();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
