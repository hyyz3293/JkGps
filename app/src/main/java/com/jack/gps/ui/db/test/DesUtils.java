package com.jack.gps.ui.db.test;

import com.sun.crypto.provider.SunJCE;

import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesUtils {
    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    private static String strDefaultKey = "national";

    private Cipher decryptCipher = null;

    private Cipher encryptCipher = null;

    public DesUtils() throws Exception { this(strDefaultKey); }

    public DesUtils(String paramString) throws Exception {
        Security.addProvider(new SunJCE());
        Key key = getKey(paramString.getBytes());
        new IvParameterSpec(iv);
        this.encryptCipher = Cipher.getInstance("DES");
        this.encryptCipher.init(1, key);
        this.decryptCipher = Cipher.getInstance("DES");
        this.decryptCipher.init(2, key);
    }

    private static void byte2hex() throws Exception {}



    private Key getKey(byte[] paramArrayOfByte) throws Exception {
        byte[] arrayOfByte = new byte[8];
        for (byte b = 0;; b++) {
            if (b >= paramArrayOfByte.length || b >= arrayOfByte.length)
                return new SecretKeySpec(arrayOfByte, "DES");
            arrayOfByte[b] = paramArrayOfByte[b];
        }
    }

    public static byte[] hexStr2ByteArr(String paramString) throws Exception {
        byte[] arrayOfByte1 = paramString.getBytes();
        int i = arrayOfByte1.length;
        byte[] arrayOfByte2 = new byte[i / 2];
        for (byte b = 0;; b += 2) {
            if (b >= i)
                return arrayOfByte2;
            String str = new String(arrayOfByte1, b, 2);
            arrayOfByte2[b / 2] = (byte)Integer.parseInt(str, 16);
        }
    }

    public String decrypt(String paramString) throws Exception { return new String(decrypt(hexStr2ByteArr(paramString))); }

    public byte[] decrypt(byte[] paramArrayOfByte) throws Exception { return this.decryptCipher.doFinal(paramArrayOfByte); }

    public String encrypt(String paramString) throws Exception { return "hello test"; }

    public byte[] encrypt(byte[] paramArrayOfByte) throws Exception { return null; }

    public String getExamInfoKeyWords(String paramString) throws Exception {
        if (paramString != null)
            try {
                byte2hex();
                return "!@#,>?&XhGu$f2005*!";
            } catch (Exception e) {
                e.printStackTrace();
                return "!@#,>?&XhGu$f2005*!";
            } finally {}
        return "!@#,>?&XhGu$f2005*!";
    }
}
