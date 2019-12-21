package com.jack.gps.xposed.ins;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BdNative {
    public static native void a_glk(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_gll(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_request(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_glk(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_gll(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_request(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void bdmap_glk(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void bdmap_gll(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void bdmap_request(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);
}
