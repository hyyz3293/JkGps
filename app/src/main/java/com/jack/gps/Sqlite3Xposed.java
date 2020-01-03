package com.jack.gps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.jack.gps.xposed.http.HttpHook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Sqlite3Xposed implements IXposedHookLoadPackage {

    public final static String RK_PACKAGE = "com.examexp_itpm";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags &
                        (ApplicationInfo.FLAG_SYSTEM |
                                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }
        final String packageName = lpparam.packageName;
        final String processName = lpparam.processName;


        try {
            XposedBridge.log("---------------------- http ----------------------start");
            HttpHook.initAllHooks(lpparam);
        }catch (Throwable e) {
            e.printStackTrace();
            XposedBridge.log(e);
            XposedBridge.log("---------------------- http ---------------------- error");
        }

        if (packageName.equals(RK_PACKAGE)) {

            try {
                XposedHelpers.findAndHookMethod("com.examexp_itpm.MyWrapperProxyApplication", lpparam.classLoader,
                        "onCreate", new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                //获取到Context对象，通过这个对象来获取classloader
//                                final Context context = (Context) param.args[0];
//                                //获取classloader，之后hook加固后的就使用这个classloader
//                                ClassLoader classLoader = context.getClassLoader();00
//                                //下面就是强classloader修改成壳的classloader就可以成功的hook了
                                XposedBridge.log("classLoader-=====start");


                                try {
                                    XposedBridge.log("----------" +  "SQLite3-start");
                                    XposedBridgeHookAllMethods(
                                            XposedHelpers.findClass(
                                                    "SQLite3.Database",
                                                    lpparam.classLoader),
                                            "key_test",
                                            new XC_MethodHook() {
                                                @Override
                                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                                    super.afterHookedMethod(param);
                                                    XposedBridge.log("-----SQLite3-----" + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                                    try {
                                                        Object str = param.args[0];
                                                        XposedBridge.log("-----SQLite3-----" + "KEY==" + str);
                                                    }catch (Throwable e) {
                                                        e.printStackTrace();
                                                        XposedBridge.log("-----SQLite3-----" + "!!!!!!!!!!" + e);
                                                    }
                                                }
                                            });
                                    XposedBridge.log("----------" +  "SQLite3-end");
                                }catch (Throwable e) {
                                    e.printStackTrace();
                                    XposedBridge.log("----------" +  e);
                                }

                                try {
                                    XposedBridge.log("----------" +  "SQLite3 xxxxx-start");
                                    XposedBridgeHookAllMethods(
                                            XposedHelpers.findClass(
                                                    "SQLite3.Database",
                                                    lpparam.classLoader),
                                            "key",
                                            new XC_MethodHook() {
                                                @Override
                                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                                    super.afterHookedMethod(param);
                                                    XposedBridge.log("-----SQLite3-----xxxxx" + "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                                    try {
                                                        Object str = param.args[0];
                                                        XposedBridge.log("-----SQLite3-----xxxxx" + "KEY==" + str);
                                                    }catch (Throwable e) {
                                                        e.printStackTrace();
                                                        XposedBridge.log("-----SQLite3-----xxxxx" + "!!!!!!!!!!" + e);
                                                    }
                                                }
                                            });
                                    XposedBridge.log("----------xxxxx" +  "SQLite3-end");
                                }catch (Throwable e) {
                                    e.printStackTrace();
                                    XposedBridge.log("----------" +  e);
                                }
                            }
                        });
            }catch (Throwable e) {
               e.printStackTrace();
               XposedBridge.log(e);
            }
        }
    }


    private void XposedBridgeHookAllMethods(Class<?> hookClass, String methodName, XC_MethodHook callback) {
        try {
            XposedBridge.hookAllMethods(hookClass, methodName, callback);
        } catch (Exception e) {
            Log.e("1111", e.getLocalizedMessage(), e);
            XposedBridge.log("*************" + e);
        }
    }


}

