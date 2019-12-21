package com.jack.gps.xposed.gps;

import com.jack.gps.xposed.ins.W;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class WMapHook {

    private static void wp(
    XC_LoadPackage.LoadPackageParam paramLoadPackageParam) { W.tencent_map_is(paramLoadPackageParam, new a()); }

    public static void Whook(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) { wp(paramLoadPackageParam); }

    static final class a extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { W.a_is(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { W.b_is(param1MethodHookParam); }
    }

}
