package com.jack.gps.xposed.ins;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Wm {
    public static native void a_sc(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void b_sc(XC_MethodHook.MethodHookParam paramMethodHookParam);

    public static native void gaode_map_sc(XC_LoadPackage.LoadPackageParam paramLoadPackageParam, XC_MethodHook paramXC_MethodHook);
}

