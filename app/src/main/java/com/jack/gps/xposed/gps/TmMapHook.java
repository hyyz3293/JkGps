package com.jack.gps.xposed.gps;

import com.jack.gps.xposed.ins.TmNative;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class TmMapHook {
    private static void tm(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) {
        TmNative.qqmap_gcl(paramLoadPackageParam, new a());
        TmNative.qqmap_gnc(paramLoadPackageParam, new b());
        TmNative.qqmap_gac(paramLoadPackageParam, new c());
        TmNative.qqmap_gno(paramLoadPackageParam, new d());
        TmNative.qqmap_lis(paramLoadPackageParam, new e());
    }

    public static void TmHook(
    XC_LoadPackage.LoadPackageParam paramLoadPackageParam) {
        tm(paramLoadPackageParam);
    }

    static final class a extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.a_gcl(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.b_gcl(param1MethodHookParam); }
    }

    static final class b extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.a_gnc(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.b_gnc(param1MethodHookParam); }
    }

    static final class c extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.a_gac(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.b_gac(param1MethodHookParam); }
    }

    static final class d extends XC_MethodHook {
        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.a_gno(param1MethodHookParam); }

        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) { TmNative.b_gno(param1MethodHookParam); }
    }

    static final class e extends XC_MethodReplacement {
        protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param1MethodHookParam) {
            TmNative.a_lis(param1MethodHookParam);
            return null;
        }
    }
}
