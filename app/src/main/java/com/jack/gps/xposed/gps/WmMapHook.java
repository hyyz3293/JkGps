package com.jack.gps.xposed.gps;

import com.jack.gps.xposed.ins.Wm;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WmMapHook {
    private static void hook(
    XC_LoadPackage.LoadPackageParam paramLoadPackageParam) { Wm.gaode_map_sc(paramLoadPackageParam, new a()); }

    public static void Wmhook(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) { hook(paramLoadPackageParam); }

    static final class a extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { Wm.a_sc(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { Wm.b_sc(param1MethodHookParam); }
    }
}
