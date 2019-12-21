package com.jack.gps.ui.db.test;

import com.jack.gps.xposed.ins.S;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public  class EptData {
    private  IvParameterSpec IvParameters;

    private  Cipher cipher;

    private  byte[] encryptKey;

    private  byte[] encryptKey_bak;

    private  byte[] encryptKey_new;

      SecretKeyFactory keyFactory;

     DESedeKeySpec spec;

     SecretKey theKey;

    public EptData() {
        try {
            this.encryptKey_bak = "*_# hello,666,this is $ a Key of gzf @ xlc,888".getBytes();
            try {
                Cipher.getInstance("DESede");
            } catch (Exception exception) {}
            this.encryptKey_new = "*_# hello,888,this is $ a Key of gzf @ xlc,999".getBytes();
            String str = "";
            Des des = new Des();
            DesUtils desUtils = new DesUtils();
            if (2 == AppInitCfg.getAppListPos()) {
                String str1 = "^1&GuX$f87*!@Xue*1Lc#2016$06!01**(3)Hello 2016 !@#,>? World ^_^";
            } else if (3 == AppInitCfg.getAppListPos()) {
                String str1 = "%M~!dlE9?}*|^{2|*e+*20(@16_-09=257$JKw3?jianli,nihao";
            } else if (4 == AppInitCfg.getAppListPos()) {
                String str1 = "@>~!q6Gi?}*|^{6|*e+*17(@gu_-20+g7$temJKw3?sys,godli";
            } else if (5 == AppInitCfg.getAppListPos()) {
                String str1 = ">|~!4p1.?}*|*<1}*]+*i(@ao_-fl^cldest$JKw3?,gnpwd";
            } else if (6 == AppInitCfg.getAppListPos()) {
                String str1 = "+1P<uf':m|@dqgc9yS%,aY/1E7@}9DqkbaseP6wO{$>?q2^,";
            } else if (7 == AppInitCfg.getAppListPos()) {
                String str1 = "^{2|*%M~!f':m|<1}*]hjS%,-f6|*eqdi?mJKwur,de3k~+f<#q";
            } else if (8 == AppInitCfg.getAppListPos()) {
                String str1 = "^QdO*%M~!f':m|<TmW]hjS%,-f8|*eqdi?&,2wur,de3k~+f<#q";
            } else if (9 == AppInitCfg.getAppListPos()) {
                str = "tk8on%M~!f':P|^2,B%:jS%,-f1|*emdi?*1'ur,dp3k~+f<(e";
            } else {
                str = String.valueOf(desUtils.getExamInfoKeyWords("just_test_key")) + des.getDesKeyWord("just_func") + des.getEnCrptKey("Just_key_Just_key_Just_key");
            }
            this.encryptKey = str.getBytes();
            this.spec = new DESedeKeySpec(this.encryptKey);
            this.keyFactory = SecretKeyFactory.getInstance("DESede");
            this.theKey = this.keyFactory.generateSecret(this.spec);
            this.cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            this.IvParameters = new IvParameterSpec(new byte[] { 12, 34, 56, 78, 90, 87, 65, 43 });
            return;
        } catch (Exception exception) {
            return;
        }
    }




    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
    }



    public static byte[] hexStr2ByteArr(String paramString) throws Exception {
        //byte[] arrayOfByte1 = paramString.getBytes();
//        int i = arrayOfByte1.length;
//        byte[] arrayOfByte2 = new byte[i / 2];
//        for (byte b = 0;; b += 2) {
//            if (b >= i)
//                return arrayOfByte2;
//            String str = new String(arrayOfByte1, b, 2);
//            arrayOfByte2[b / 2] = (byte)Integer.parseInt(str, 16);
//        }

        String ss = bytesToHexString(paramString.getBytes());

        return ss.getBytes();
    }

    public String decrypt3(String paramString) {
        try {
            cipher.init(2, theKey, IvParameters);
            byte[] arrayOfByte = hexStr2ByteArr(paramString);
            return new String(cipher.doFinal(arrayOfByte), "gb2312");
        } catch (Exception e) {
            return null;
        }
    }
}


