package com.jack.gps.xposed.gps;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;

import com.jack.gps.xposed.ins.BdNative;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class GpsHook {

    private static Context getContext() {
        Context context = (Context) XposedHelpers.callMethod(XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null), "currentActivityThread", new Object[0]), "getSystemContext", new Object[0]);
        Context application =  context;
        if (context == null)
            application = (Context) AndroidAppHelper.currentApplication();
        return application;
    }

    public static void hook(XC_LoadPackage.LoadPackageParam paramLoadPackageParam) {
        if (getContext() != null) {
            try {
                BdMapHook.BdGpsx(paramLoadPackageParam);
            } catch (Throwable e) {
                e.printStackTrace();
                XposedBridge.log(e);
            }
            try {
                TmMapHook.TmHook(paramLoadPackageParam);
            } catch (Throwable e) {
                e.printStackTrace();
                XposedBridge.log(e);
            }
            try {
                WMapHook.Whook(paramLoadPackageParam);
            } catch (Throwable e) {
                XposedBridge.log(e);
            }

            try {
                WmMapHook.Wmhook(paramLoadPackageParam);
            } catch (Throwable e) {
                XposedBridge.log(e);
            }
        }

    }

    public static void log(String paramString) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("xposed_:");
        stringBuilder.append(paramString);
        XposedBridge.log(stringBuilder.toString());
    }
}
