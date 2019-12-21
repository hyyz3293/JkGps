package com.jack.gps.xposed.gps;

import com.jack.gps.xposed.ins.BdNative;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class BdMapHook {

    private static void BdGpsHk(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) {
        BdNative.bdmap_request(paramLoadPackageParam, new a());
        new b();
        BdNative.bdmap_glk(paramLoadPackageParam, new c());
    }

    public static void BdGpsx(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) { BdGpsHk(paramLoadPackageParam); }

    static final class a extends XC_MethodReplacement {
        protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) {
            BdNative.a_request(param1MethodHookParam);
            return null;
        }
    }

    static final class b extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { BdNative.a_gll(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { BdNative.b_gll(param1MethodHookParam); }
    }

    static final class c extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { BdNative.a_glk(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { BdNative.b_glk(param1MethodHookParam); }
    }
}
