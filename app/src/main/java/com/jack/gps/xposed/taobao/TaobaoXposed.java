package com.jack.gps.xposed.taobao;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class TaobaoXposed implements IXposedHookLoadPackage {

    private String TAOBAO = "com.taobao.taobao";

    private Handler mHandlerMain;
    private Runnable mRunnableMain;
    private int number = 0;

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

        XposedBridge.log("---------------------------start--------------------" + packageName);
        if (TAOBAO.equals(packageName)) {
            try {
                XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (param.hasThrowable()) return;
                        Class<?> clazz = (Class<?>) param.getResult();
                        //判断类名
                        //XposedBridge.log("---------------------------afterHookedMethod--------------------");
                        XposedBridge.log("------------------" + clazz.getName());
                        if (clazz.getName().equals("com.taobao.tao.welcome.Welcome")) {
                            XposedHelpers.findAndHookMethod(clazz, "onResume", new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    XposedBridge.log("hook-------------------------------------->" + "成功");
                                }
                            });
                        }

                        try {
                            if (clazz.getName().equals("com.taobao.android.trade.cart.CartActivity")) {
                                XposedHelpers.findAndHookMethod(clazz, "onResume",
                                        new XC_MethodHook() {
                                            @Override
                                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                                super.afterHookedMethod(param);
                                                XposedBridge.log("hook---------CartActivity----------------------------->" + "成功");
                                            }
                                        });
                            } else {
                                //XposedBridge.log("hook---------TBMainActivity----------------------------->");
                            }
                        }catch (Throwable e) {
                            e.printStackTrace();
                            XposedBridge.log("hook------------------CartActivity-------------------->" + "购物车--失败");
                        }

                        try {
                            if (clazz.getName().equals("com.taobao.android.purchase.TBPurchaseActivity")) {
                                XposedHelpers.findAndHookMethod(clazz, "onResume",
                                        new XC_MethodHook() {
                                            @Override
                                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                                super.afterHookedMethod(param);
                                                XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "成功");
                                                Activity activity = (Activity) param.thisObject;
                                                if (activity != null) {
                                                    XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "activity != null");
                                                } else {
                                                    XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "activity == null");
                                                }
                                            }
                                        });

                                XposedHelpers.findAndHookMethod(clazz, "onResume",
                                        new XC_MethodHook() {
                                            @Override
                                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                                super.afterHookedMethod(param);
                                                XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "成功");
                                                Activity activity = (Activity) param.thisObject;
                                                if (activity != null) {
                                                    XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "activity != null");

                                                    if (mHandlerMain == null) {
                                                        mHandlerMain = new Handler();
                                                        mRunnableMain = () -> {
                                                            XposedBridge.log("*****  TBPurchaseActivity-------refush");


                                                            long time = System.currentTimeMillis();

                                                            if (time >= 1578916800000L && clazz.getName().equals("com.taobao.android.purchase.TBPurchaseActivity") && number < 2) {
                                                                mHandlerMain.postDelayed(mRunnableMain,2000);
                                                                number++;
                                                                XposedBridge.log("*****  xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx------------------------------------");
                                                                activity.recreate();
                                                            } else {
                                                                mHandlerMain.postDelayed(mRunnableMain,10);
                                                            }



                                                        };
                                                        mHandlerMain.postDelayed(mRunnableMain, 100);
                                                    }
                                                } else {
                                                    XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "activity == null");
                                                }
                                            }
                                        });

                                try {
                                    XposedHelpers.findAndHookMethod(clazz, "q", new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);
                                            XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "=========q");
                                        }
                                    });
                                }catch (Throwable e) {
                                    XposedBridge.log("refush+q" + e);
                                }

                                try {
                                    XposedHelpers.findAndHookMethod(clazz, "q", new XC_MethodHook() {
                                        @Override
                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                            super.afterHookedMethod(param);
                                            XposedBridge.log("hook---------TBPurchaseActivity----------------------------->" + "=========q");
                                        }
                                    });
                                }catch (Throwable e) {
                                    XposedBridge.log("refush+" + e);
                                }

                            } else {
                                //XposedBridge.log("hook---------TBMainActivity----------------------------->");
                            }
                        }catch (Throwable e) {
                            e.printStackTrace();
                            XposedBridge.log("hook------------------TBPurchaseActivity-------------------->" + "购物车--失败");
                        }

                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
                XposedBridge.log(e);
                XposedBridge.log("---------------------------error--------------------");
            }
            XposedBridge.log("---------------------------end--------------------");
        }

    }
}
