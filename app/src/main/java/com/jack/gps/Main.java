package com.jack.gps;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;

import com.jack.gps.ui.api.ApiFactory;
import com.jack.gps.utils.AppUtil;
import com.jack.gps.xposed.GPSHooker2;
import com.jack.gps.xposed.LocationHook2;
import com.jack.gps.xposed.hook.EmojiGameHook;
import com.jack.gps.xposed.hook.ExdeviceRankHook;
import com.jack.gps.xposed.hook.ExtDeviceWXLoginUIHook;
import com.jack.gps.xposed.hook.LauncherUIHook;
import com.jack.gps.xposed.hook.RevokeMsgHook;
import com.jack.gps.xposed.hook.StepHook;
import com.jack.gps.xposed.hook.TencentLocationManagerHook;
import com.jack.gps.xposed.hook.WalletHook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
  *  
  * @ProjectName:
  * @Package:        
  * @ClassName:      
  * @Description:    java类作用描述
  * @Author:         jack
  * @CreateDate:      
  * @UpdateUser:     更新者
  * @UpdateDate:      
  * @UpdateRemark:   更新内容
  * @Version:        1.0
  */
public class Main implements IXposedHookLoadPackage {

    public final static String WECHAT_PACKAGE = "com.tencent.mm";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.appInfo == null ||
                (lpparam.appInfo.flags &
                        (ApplicationInfo.FLAG_SYSTEM |
                                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }
        final String packageName = lpparam.packageName;
        final String processName = lpparam.processName;

        if (WECHAT_PACKAGE.equals(packageName)) {
            try {
                XposedHelpers.findAndHookMethod(Application.class,
                        "attach",
                        Context.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                                super.afterHookedMethod(param);
                                Context context = (Context) param.args[0];
                                ClassLoader appClassLoader = context.getClassLoader();
                                StepHook.hook(appClassLoader);
                            }
                        });
            } catch (Throwable e) {
                XposedBridge.log(e);
            }
            if (WECHAT_PACKAGE.equals(processName)) {
                try {
                    XposedHelpers.findAndHookMethod(ContextWrapper.class,
                            "attachBaseContext", Context.class, new XC_MethodHook() {
                                @Override
                                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                                    super.afterHookedMethod(param);
                                    Context context = (Context) param.args[0];
                                    ClassLoader appClassLoader = context.getClassLoader();
                                    handleHook(appClassLoader, AppUtil.getVersionName(context, WECHAT_PACKAGE));
                                }
                            });
                } catch (Throwable e) {
                    XposedBridge.log(e);
                }
            }

//            XposedBridge.hookAllConstructors(LocationManager.class,new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    super.afterHookedMethod(param);
//                    if (param.args.length==2) {
//                        Context context = (Context) param.args[0]; //这里的 context
//                        XposedBridge.log(" 对 "+ getProgramNameByPackageName(context)+" 模拟位置");
//                        //把权限的检查 hook掉
//                        XposedHelpers.findAndHookMethod(context.getClass(), "checkCallingOrSelfPermission", String.class, new XC_MethodHook() {
//                            @Override
//                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                super.afterHookedMethod(param);
//                                if (param.args[0].toString().contains("INSTALL_LOCATION_PROVIDER")){
//                                    param.setResult(PackageManager.PERMISSION_GRANTED);
//                                }
//                            }
//                        });
//                        XposedBridge.log("LocationManager : " + context.getPackageName() + " class:= " + param.args[1].getClass().toString());
//                        //获取到  locationManagerService 主动调用 对象的 reportLocation 方法  可以去模拟提供位置信息
//                        //这里代码中并没有涉及到主动调用
//                        Object locationManagerService = param.args[1];
//                    }
//                }
//            });


            try {
                //        latitude = "29.772904";
                //        longitude = "105.426788";
                //105.1744,29.67856
                //HookUtils.HookAndChange(lpparam.classLoader, 29.772904, 105.1744, 500112, 132);
                //new GPSHooker2().hook(lpparam.classLoader);
            }catch (Throwable e) {
                XposedBridge.log(e);
            }

        }
    }

    private void handleHook(ClassLoader classLoader, String versionName) {
        ApiFactory.initApi(versionName);
        TencentLocationManagerHook.hook(classLoader);
        EmojiGameHook.getInstance().init(classLoader, versionName);
        WalletHook.getInstance().init(classLoader, versionName);
        LauncherUIHook.getInstance().init(classLoader, versionName);
        ExdeviceRankHook.getInstance().init(classLoader, versionName);
        RevokeMsgHook.getInstance().init(classLoader);
        ExtDeviceWXLoginUIHook.getInstance().init(classLoader, versionName);
    }
}
