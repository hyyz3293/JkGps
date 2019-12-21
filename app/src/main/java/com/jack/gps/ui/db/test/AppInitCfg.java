package com.jack.gps.ui.db.test;


public class AppInitCfg {
    public static int SUBJ_TYPE_FREE = 0;

    public static int SUBJ_TYPE_ITPM_PUBLIC = 0;

    private static final int[] appClassType;

    private static final boolean[] appClassicFrameFlag;

    private static final int[] appExamDBType_arr;

    private static final String[] appFileDir;

    private static final String[] appName_arr = { "����������������", "����������������", "����������������", "��������������", "����������", "����������", "����������������", "����������", "��������������", "PMP����������" };

    private static final String[] appNetRegFile_Black;

    private static final String[] appNetRegFile_White;

    private static final String[] appPackNameList;

    private static final int appPos = 1;

    private static final String[] appUserCheckFlag;

    private static final int[] appUserCheckType;

    private static final String[] appUserReqFlag;

    private static final String[] buglyID_arr;

    private static final int[] highSelYearCnt_arr;

    private static final int[] midSelYearCnt_arr;

    private static final String[] share_QQID_arr;

    private static final String[] share_QQKey_arr;

    static  {
        appClassType = new int[] { 4, 1, 10, 20, 30, 40, 50, 60, 100, 200 };
        appExamDBType_arr = new int[] { 100, 100, 100, 200, 300, 100, 200, 400, 200, 500 };
        midSelYearCnt_arr = new int[] { 19, 19, 21, 19, 10, 17, 10, 14, 5, 6 };
        int[] arrayOfInt = new int[10];
        arrayOfInt[0] = 19;
        arrayOfInt[1] = 19;
        arrayOfInt[2] = 11;
        arrayOfInt[3] = 17;
        arrayOfInt[4] = 10;
        arrayOfInt[5] = 10;
        arrayOfInt[9] = 10;
        highSelYearCnt_arr = arrayOfInt;
        appUserCheckType = new int[] { 101, 101, 102, 211, 262, 310, 323, 281, 801, 501 };
        appUserCheckFlag = new String[] { "ITPM", "ITPM", "ITNET", "IT_JianLi", "IT_XiFengf", "IT_DS", "IT_DB", "IT_CD", "IT_XA", "IT_PMP" };
        appUserReqFlag = new String[] { "", "", "net", "JL", "XF", "DS", "DB", "CD", "XA", "PMP" };
        appNetRegFile_White = new String[] { "hello_world_pm_white.json", "hello_world_pm_white.json", "it_white_sec.json", "it_white_three.json", "it_white_three.json", "it_white_three.json", "it_white_three.json", "it_white_three.json", "it_white_three.json", "it_white_pmp.json" };
        appNetRegFile_Black = new String[] { "hello_world_pm_black.json", "hello_world_pm_black.json", "it_black_sec.json", "it_black_three.json", "it_black_three.json", "it_black_three.json", "it_black_three.json", "it_black_three.json", "it_black_three.json", "it_black_pmp.json" };
        appClassicFrameFlag = new boolean[] { true, true, true, true, true, true, true, true, true, true };
        appPackNameList = new String[] { "com.examexp", "com.examexp_itpm", "com.examexp_itnet", "com.examexp_itjl", "com.examexp_itxf", "com.examexp_itdesign", "com.examexp.itdb", "com.examexp_coder", "com.examexp_itxa", "com.examexp_itpmp" };
        appFileDir = new String[] { "/sdcard/examexp/", "/sdcard/examexp_itpm/", "/sdcard/examexp_itnet/", "/sdcard/examexp_itjl/", "/sdcard/examexp_itxf/", "/sdcard/examexp_itdesign/", "/sdcard/examexp.itdb/", "/sdcard/examexp_coder/", "/sdcard/examexp_itxa/", "/sdcard/examexp_itpmp/" };
        share_QQID_arr = new String[] { "1104498037", "1104665831", "1105463537", "1105936018", "1105965236", "1105989928", "1105991433", "1106225197", "1106594811", "1106742120" };
        share_QQKey_arr = new String[] { "sOjAYqNWge3DRj6G", "V6B9LUIbL5sN4D4w", "Y8UJdW7HibwqVTGF", "IVRhVlQyxQTwHpuT", "grQbdmVsceUtwh1w", "n8Pvyr7vGPvmVZ0s", "6VM1wMtHVzRBNAZp", "5XVu2RWDMktHNhYt", "yuGTR64mlYmVpwH9", "gdt3ctPPv4TZahO9" };
        buglyID_arr = new String[] { "900034415", "900035160", "900037720", "c0bb30f9d1", "1c9d84990f", "824f6c3ef2", "da52289c46", "af6323f0e8", "466faa11ff", "1fd2831042" };
        SUBJ_TYPE_FREE = 3;
        SUBJ_TYPE_ITPM_PUBLIC = 6;
    }

    public static String getAppBuglyID() { return (1 < buglyID_arr.length) ? buglyID_arr[1] : buglyID_arr[0]; }

    public static int getAppClassVideoType() { return appClassType[1]; }

    public static int getAppExamDBType() { return appExamDBType_arr[1]; }

    public static String getAppFileDir() { return appFileDir[1]; }

    public static int getAppListPos() { return 1; }

    public static String getAppName() { return appName_arr[1]; }

    public static String getAppNetRegFile_Black() { return appNetRegFile_Black[1]; }

    public static String getAppNetRegFile_White() { return appNetRegFile_White[1]; }

    public static String getAppPackName() { return appPackNameList[1]; }

    public static String getAppShareName(String paramString) {
        for (byte b = 0;; b++) {
            if (b >= appPackNameList.length)
                return null;
            if (appPackNameList[b].equals(paramString))
                return (b <= 1) ? "����������������������" : appName_arr[b];
        }
    }

    public static String getAppShare_QQID() { return (1 < share_QQID_arr.length) ? share_QQID_arr[1] : share_QQID_arr[0]; }

    public static String getAppShare_QQKey() { return (1 < share_QQKey_arr.length) ? share_QQKey_arr[1] : share_QQKey_arr[0]; }

    public static String getAppUserCheckFlag() { return appUserCheckFlag[1]; }

    public static int getAppUserCheckType() { return appUserCheckType[1]; }

    public static String getAppUserReqFlag() { return appUserReqFlag[1]; }

    public static int getExamCnt_highPmpNum() { return highSelYearCnt_arr[1] * 200; }

    public static int getExamCnt_highSelNum() { return highSelYearCnt_arr[1] * 75; }

    public static int getExamCnt_highSelYearNum() { return highSelYearCnt_arr[1]; }

    public static int getExamCnt_midSelNum() { return midSelYearCnt_arr[1] * 75; }

    public static int getExamCnt_midSelYearNum() { return midSelYearCnt_arr[1]; }

    public static boolean isNeedClassicFrame() { return appClassicFrameFlag[1]; }
}
