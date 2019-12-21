package com.jack.gps.xposed;

import android.app.Notification;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;

import java.util.Arrays;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XT implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags &
                        (ApplicationInfo.FLAG_SYSTEM |
                                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }
        //if (lpparam.packageName.equals("com.eg.android.AlipayGphone")) {
            //        if (!lpparam.packageName.equals("com.tencent.mobileqq")
            //                && !lpparam.processName.equals("com.tencent.mobileqq"))
            //            return;
            //        if (lpparam.packageName.equals("com.tencent.mobileqq")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                XposedHelpers.findAndHookMethod("android.app.NotificationManager"
                        , lpparam.classLoader, "notify"
                        , String.class, int.class, Notification.class
                        , new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                XposedBridge.log("methodHookParam.args:  " + Arrays.toString(param.args));
                                //通过param拿到第三个入参notification对象
                                Notification notification = (Notification) param.args[2];
                                //获得包名
                                String aPackage = notification.contentView.getPackage();
                                String title = "--";
                                String text = "--";
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    Bundle bundle = notification.extras;
                                    title = (String) bundle.get("android.title");
                                    text = (String) bundle.get("android.text");
                                }
                                //    if ("com.tencent.mobileqq".equals(aPackage)) {
                                XposedBridge.log("methodHookParam.args:  " + title + "--------------" + text);

                                super.beforeHookedMethod(param);
                            }
                        });
            }

        //}
    }
}
