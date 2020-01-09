package com.jack.gps.auxiliary;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;


import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import com.jack.gps.utils.AppUtil;

import java.util.Iterator;
import java.util.List;


/**
 * Created by minggo on 16/5/30.
 */
public class AuxiliaryClickService extends AccessibilityService {

    private ClickReceiver receiver;
    private static AuxiliaryClickService service;
    public static final String BROARD_ACIOTN_CLICK = "broard_action_click";

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("mService", "onCreate");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //接收事件,如触发了通知栏变化、界面变化等
        Log.i("mService", "AccessibilityEvent按钮点击变化");
        //performClick();
    }

    @Override
    protected boolean onKeyEvent(KeyEvent event) {
        Log.i("mService", "按钮点击变化");
        //接收按键事件
        return super.onKeyEvent(event);
    }

    @Override
    public void onInterrupt() {
        Log.i("mService", "授权中断");
        //服务中断，如授权关闭或者将服务杀死
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.i("mService", "service授权成功");
        service = this;
        //连接服务后,一般是在授权成功后会接收到
        if (receiver == null) {
            receiver = new ClickReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(BROARD_ACIOTN_CLICK);
            registerReceiver(receiver, intentFilter);
        }

//        mHandler = new Handler();
//        mRunnable = () -> {
//            LogUtils.e("模拟点击中", StringUtils.getNewTime());
//            //performClick2("com.hybunion.shouyintai:id/ll_main_scan"); //收款码
//            //com.hybunion.shouyintai:id/ll_main_scan-------------com.hybunion.shouyintai:id/ll_be_scan----com.hybunion.shouyintai:id/iv_home_notice
//            performClick2("com.hybunion.shouyintai:id/ll_main_scan","重新获取二维码并保存相册");
//            if (isStartClick)
//                mHandler.postDelayed(mRunnable,2000);
//        };
//        mHandler.postDelayed(mRunnable, 2000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        if (mRunnable != null && mHandler != null)
            mHandler.removeCallbacks(mRunnable);

    }

    //执行返回
    public void performBack() {
        Log.i("mService", "执行返回");
        this.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
    }

    //执行点击
    private void performClick() {

        Log.i("mService", "点击执行");

        AccessibilityNodeInfo nodeInfo = this.getRootInActiveWindow();
        AccessibilityNodeInfo targetNode = null;
        //通过名字获取
        //targetNode = findNodeInfosByText(nodeInfo,"广告");
        //通过id获取
        targetNode = findNodeInfosById(nodeInfo, "com.haozhi.projectb:id/bt_browser");
        if (targetNode.isClickable()) {
            targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    //执行点击
    private void performClick(String resourceId) {
        Log.i("mService", "点击执行");
        try {
            AccessibilityNodeInfo nodeInfo = AuxiliaryClickService.this.getRootInActiveWindow();
            AccessibilityNodeInfo targetNode = null;
            targetNode = findNodeInfosById(nodeInfo, "com.yk.android:id/" + resourceId);
            if (targetNode.isClickable()) {
                targetNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //执行点击
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void performClickIdBack(String id) {
        Log.i("mService", "点击执行");
        try {
            List<AccessibilityWindowInfo> accessList = this.getWindows();
            if (accessList != null && accessList.size() > 0) {
                for (int i = 0; i < accessList.size(); i++) {
                    if (accessList.get(i) != null && accessList.get(i).getRoot() != null) {
                        AccessibilityNodeInfo nodeInfo = accessList.get(i).getRoot();
                        AccessibilityNodeInfo targetNodeByID = null;
                        if (nodeInfo != null) {
                            targetNodeByID = findNodeInfosById(nodeInfo, id);
                            if (targetNodeByID != null && targetNodeByID.isClickable()) {
                                if ("com.hybunion.shouyintai:id/ll_titlebar_back".equals(id)) {
                                    targetNodeByID.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }

    }


    //执行点击
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void performClickId(String id) {
        Log.i("mService", "点击执行");
        try {
            List<AccessibilityWindowInfo> accessList = this.getWindows();
            if (accessList != null && accessList.size() > 0) {
                for (int i = 0; i < accessList.size(); i++) {
                    if (accessList.get(i) != null && accessList.get(i).getRoot() != null) {
                        AccessibilityNodeInfo nodeInfo = accessList.get(i).getRoot();
                        AccessibilityNodeInfo targetNodeByID = null;
                        if (nodeInfo != null) {
                            targetNodeByID = findNodeInfosById(nodeInfo, id);
                            if (targetNodeByID != null && targetNodeByID.isClickable()) {
                                targetNodeByID.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }
    }

    //执行点击
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void performClickName(String name) {
        Log.i("mService", "点击执行");
        try {
            List<AccessibilityWindowInfo> accessList = this.getWindows();
            if (accessList != null && accessList.size() > 0) {

                AccessibilityNodeInfo taSureBack = null, noBuyAccess = null, mSurePay = null;
                for (int i = 0; i < accessList.size(); i++) {
                    if (accessList.get(i) != null && accessList.get(i).getRoot() != null) {
                        AccessibilityNodeInfo nodeInfo = accessList.get(i).getRoot();
                        if (nodeInfo != null) {
                            taSureBack = findNodeInfosByText(nodeInfo, "确认订单");
                            if (taSureBack != null && taSureBack.isClickable()) {
                                Log.e("****** back ******", "确认订单");
                            }
                            noBuyAccess = findNodeInfosByText(nodeInfo, "商品不能购买");
                            if (noBuyAccess != null) {
                                Log.e("****** --------- ******", "商品不能购买");
                            }

                            mSurePay = findNodeInfosByText(nodeInfo, "确认付款");
                            if (mSurePay != null) {

                            }

                        }
                    }
                }

                for (int i = 0; i < accessList.size(); i++) {
                    if (accessList.get(i) != null && accessList.get(i).getRoot() != null) {
                        AccessibilityNodeInfo nodeInfo = accessList.get(i).getRoot();
                        AccessibilityNodeInfo targetNodeByName = null;
                        if (nodeInfo != null) {
                            targetNodeByName = findNodeInfosByText(nodeInfo, name);
                            if (targetNodeByName != null && targetNodeByName.isClickable()) {
                                if (noBuyAccess == null)
                                    targetNodeByName.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                else {
                                    if (taSureBack != null)
                                        taSureBack.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.getMessage());
        }

    }


    //通过id查找
    public static AccessibilityNodeInfo findNodeInfosById(AccessibilityNodeInfo nodeInfo, String resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId(resId);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }

    //通过文本查找
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText(text);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    /**
     * 判断当前服务是否正在运行
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean isRunning() {
        if (service == null) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) service.getSystemService(Context.ACCESSIBILITY_SERVICE);
        AccessibilityServiceInfo info = service.getServiceInfo();
        if (info == null) {
            return false;
        }
        List<AccessibilityServiceInfo> list = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_GENERIC);
        Iterator<AccessibilityServiceInfo> iterator = list.iterator();

        boolean isConnect = false;
        while (iterator.hasNext()) {
            AccessibilityServiceInfo i = iterator.next();
            if (i.getId().equals(info.getId())) {
                isConnect = true;
                break;
            }
        }
        if (!isConnect) {
            return false;
        }
        return true;
    }

    public class ClickReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int i = intent.getIntExtra("flag", 0);
            if (mHandler == null)
                mHandler = new Handler();
            if (i == 1) {
                mRunnable = () -> {
                    Log.e("模拟点击中--1", AppUtil.getNewTime());
                    performClickId("com.taobao.taobao:id/button_cart_charge");
                    performClickName("提交订单");
                    performClickId("com.taobao.taobao:id/purchase_new_dialog_right_btn");
                    mHandler.postDelayed(mRunnable, 50);
                };
                mHandler.postDelayed(mRunnable, 10);
            }
        }
    }

}
