package com.jack.gps;

import android.content.pm.ApplicationInfo;

import com.jack.gps.xposed.gps.GpsHook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Gps定位
 */
public class GpsXposedMain implements IXposedHookLoadPackage {

    private String PACKGE = "com.jack.gps";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags &
                        (ApplicationInfo.FLAG_SYSTEM |
                                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }

        if (lpparam.packageName.contains(PACKGE))
            return;

        GpsHook.hook(lpparam);
    }
}
