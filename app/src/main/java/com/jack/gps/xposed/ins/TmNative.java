package com.jack.gps.xposed.ins;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class TmNative {
    public static native void a_gac(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_gcl(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_gnc(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_gno(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void a_lis(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_gac(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_gcl(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_gnc(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_gno(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void qqmap_gac(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void qqmap_gcl(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void qqmap_gnc(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void qqmap_gno(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);

    public static native void qqmap_lis(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);
}
