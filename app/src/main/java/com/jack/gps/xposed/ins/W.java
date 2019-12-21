package com.jack.gps.xposed.ins;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class W {
    public static native void a_is(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_is(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void tencent_map_is(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);
}

