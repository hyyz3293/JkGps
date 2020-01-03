package com.jack.gps.xposed.http;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;


import android.app.Activity;
import android.app.Application;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookConstructor;

//web.loadUrl("http://1.1.1.0", param);web.loadUrl("http://1.1.1.1/");web.postUrl("","");

//DatagramSocket.send(packet) 发送
//DatagramSocket DatagramSocket.bind DatagramSocket.createSocket 监听

//startupSocket对应多个socket构造函数  sock.connect
//ServerSocket ServerSocket.bind

//SocketChannel.open(addr)  ?SocketChannel.connect?

//outputstream.write(byte[],int,int)

//httpclient.execute(post);
//urls.openConnection(); urls.openConnection(proxy);  ?urls.setRequestProperty?


//辅助记录，避免出现调用其他包进行监听 logcat | grep xuhu >>/sdcard/a.txt

public class HttpHook2 implements IXposedHookLoadPackage{
    private String TARGET_APP = "com.baidu.BaiduMap";
    private String[] TARGET_APPS = new String[]{
            "com.liulishuo.vir",
            "com.yek.lafaso","com.vipshop.vswxk","com.baidu.BaiduMap","com.example.t1","com.baidu.fb","com.tencent.mm","com.tencent.mtt","com.nq.mdm"};
    private SharedPreferences msp = null;
    private Application mApp = null;
    private String LOG_FILENAME = "_test_network";
    private boolean NETWORK = true;
    private boolean HTTP_DATA = true;
    private boolean SOCKET_DATA = true;
    private boolean HTTP_RESPONSE = false;
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        //if(!Arrays.asList(TARGET_APPS).contains(lpparam.packageName))return;

        TARGET_APP = lpparam.packageName;
        if(lpparam.appInfo == null ||
                (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) !=0){
            return;
        }else if(true){//lpparam.isFirstApplication

            hookOncreate(lpparam);//
            if(mApp != null){
                msp = mApp.getSharedPreferences(LOG_FILENAME, Activity.MODE_PRIVATE);
            }
            mLog("target", lpparam.packageName);

            //网络监控开始
            if(NETWORK){
                findAndHookConstructor(InetSocketAddress.class, String.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("网络地址", param.args[0]+":"+param.args[1]);
                        super.beforeHookedMethod(param);
                    }
                });

                //
                findAndHookMethod("java.net.DatagramSocket", lpparam.classLoader, "send", DatagramPacket.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        DatagramPacket d = (DatagramPacket)param.args[0];
                        if(SOCKET_DATA){
                            mLog("udp发送", d.getAddress()+":"+d.getPort()+":"+new String(d.getData()));
                        }else{
                            mLog("udp发送", d.getAddress()+":"+d.getPort());
                        }
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("java.net.DatagramSocket", lpparam.classLoader, "createSocket", int.class, InetAddress.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("udp监听", ((InetAddress)param.args[1]).toString()+":"+(Integer)param.args[0]);
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("java.net.DatagramSocket", lpparam.classLoader, "bind", SocketAddress.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("udp监听", ((SocketAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookConstructor(DatagramSocket.class, SocketAddress.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("udp监听", ((SocketAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });

                //
                findAndHookMethod("android.webkit.WebView", lpparam.classLoader, "loadUrl", String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String d = (String)param.args[0];
                        mLog("webview", d);
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("android.webkit.WebView", lpparam.classLoader, "loadUrl", String.class, Map.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String d = (String)param.args[0];
                        if(HTTP_DATA){
                            Map d1 = (Map)param.args[1];
                            mLog("webview", d+":"+d1.toString());
                        }else{
                            mLog("webview", d);
                        }

                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("android.webkit.WebView", lpparam.classLoader, "postUrl", String.class, byte[].class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        String d = (String)param.args[0];
                        if(HTTP_DATA){
                            String d1 = new String((byte[])param.args[1]);
                            mLog("webview", d+":"+d1);
                        }else{
                            mLog("webview", d);
                        }

                        super.beforeHookedMethod(param);
                    }
                });

                //
                if(SOCKET_DATA){
                    findAndHookMethod("java.io.OutputStream", lpparam.classLoader, "write", byte[].class, int.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            byte[] d = (byte[])param.args[0];
                            mLog("socketdata", new String(d));
                            super.beforeHookedMethod(param);
                        }
                    });
                    findAndHookMethod("java.io.OutputStream", lpparam.classLoader, "write", byte[].class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            byte[] d = (byte[])param.args[0];
                            mLog("socketdata1", new String(d));
                            super.beforeHookedMethod(param);
                        }
                    });
                }
                findAndHookMethod("java.nio.channels.SocketChannel", lpparam.classLoader, "open", SocketAddress.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("tcp连接", ((SocketAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });

                findAndHookMethod("java.net.Socket", lpparam.classLoader, "startupSocket", InetAddress.class, int.class, InetAddress.class, int.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("tcp连接", ((InetAddress)param.args[0]).toString()+":"+(Integer)param.args[1]);
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("java.net.Socket", lpparam.classLoader, "connect", SocketAddress.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("tcp连接", ((SocketAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookConstructor(ServerSocket.class, int.class, int.class, InetAddress.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("tcp监听", ((InetAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });
                findAndHookMethod("java.net.ServerSocket", lpparam.classLoader, "bind", SocketAddress.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        mLog("tcp监听", ((SocketAddress)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });

                //
                findAndHookMethod("java.net.URL", lpparam.classLoader, "openConnection", java.net.Proxy.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        URL url = (URL) param.thisObject;
                        mLog("urlconnp", url.toString()+":"+((Proxy)param.args[0]).toString());
                        super.beforeHookedMethod(param);
                    }
                });
                if(HTTP_DATA){
                    findAndHookMethod("java.net.URLConnection", lpparam.classLoader, "setRequestProperty", String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            mLog("urlconnheader", (String)param.args[0]+":"+(String)param.args[1]);
                            super.beforeHookedMethod(param);
                        }

                    });
                    findAndHookMethod("java.net.URLConnection", lpparam.classLoader, "addRequestProperty", String.class, String.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param)
                                throws Throwable {
                            mLog("urlconnheader", (String)param.args[0]+":"+(String)param.args[1]);
                            super.beforeHookedMethod(param);
                        }

                    });
                }
                findAndHookMethod("java.net.URL", lpparam.classLoader, "openConnection", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        URL url = (URL) param.thisObject;
                        mLog("urlconn", url.toString());
                        super.beforeHookedMethod(param);
                    }
                });
                //
                hookHttpClient(lpparam);

            }//网络监控结束
        }
    }

    public void mLog(String tag, String text){
        Log.i(TARGET_APP, "xuhu"+tag+":"+text);
        if(msp != null){
            if(HTTP_DATA){
                mSharePrefer(text,tag);
            }else{
                int i = text.indexOf("?");
                if(i > 0)
                    mSharePrefer(text.substring(0, i), tag);
                else
                    mSharePrefer(text, tag);
            }
        }
    }
    public void mSharePrefer(String key, String value){
        SharedPreferences.Editor editor = msp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void hookHttpClient(LoadPackageParam lpparam){
        findAndHookMethod("org.apache.http.impl.client.AbstractHttpClient", lpparam.classLoader,
                "execute", HttpHost.class, HttpRequest.class, HttpContext.class, new XC_MethodHook() {

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param)
                            throws Throwable {
                        //HttpHost host = (HttpHost) param.args[0];
                        HttpRequest request = (HttpRequest) param.args[1];
                        if (request instanceof HttpGet) {
                            HttpGet httpGet = (HttpGet) request;
                            mLog("httpclientGet", httpGet.getURI().toString());
                            if(HTTP_DATA){
                                Header[] headers = request.getAllHeaders();
                                if (headers != null) {
                                    for (int i = 0; i < headers.length; i++) {
                                        mLog("getHeader", headers[i].getName() + ": " + headers[i].getValue());
                                    }
                                }}
                        } else if (request instanceof HttpPost) {
                            HttpPost httpPost = (HttpPost) request;
                            mLog("httpclientPost", httpPost.getURI().toString());
                            if(HTTP_DATA){// until get header
                                Header[] headers = request.getAllHeaders();
                                if (headers != null) {
                                    for (int i = 0; i < headers.length; i++) {
                                        mLog("postHeader", headers[i].getName() + ":" + headers[i].getValue());
                                    }
                                }
                                HttpEntity entity = httpPost.getEntity();
                                String contentType = null;
                                if (entity.getContentType() != null) {
                                    contentType = entity.getContentType().getValue();
                                    if (URLEncodedUtils.CONTENT_TYPE.equals(contentType)) {
                                        try {
                                            byte[] data = new byte[(int) entity.getContentLength()];
                                            entity.getContent().read(data);
                                            String content = new String(data, HTTP.DEFAULT_CONTENT_CHARSET);
                                            mLog("postcontent",content);
                                        } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
// TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                    } else if (contentType.startsWith(HTTP.DEFAULT_CONTENT_TYPE)) {
                                        try {
                                            byte[] data = new byte[(int) entity.getContentLength()];
                                            entity.getContent().read(data);
                                            String content = new String(data, contentType.substring(contentType.lastIndexOf("=") + 1));
                                            mLog("postcontent",content);
                                        } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }else{
                                    byte[] data = new byte[(int) entity.getContentLength()];
                                    try {
                                        entity.getContent().read(data);
                                        String content = new String(data, HTTP.DEFAULT_CONTENT_CHARSET);
                                        mLog("postcontent",content);
                                    } catch (IllegalStateException e) {
// TODO Auto-generated catch block
                                        e.printStackTrace();
                                    } catch (IOException e) {
// TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }//get header
                        }else{
                            HttpEntityEnclosingRequestBase get = (HttpEntityEnclosingRequestBase)request;
                            HttpEntity entity = get.getEntity();
                            mLog("Android-async-http", get.getURI().toString());
                            if(HTTP_DATA){
                                Header[] headers = request.getAllHeaders();
                                if (headers != null) {
                                    for (int i = 0; i < headers.length; i++) {
                                        mLog("Android-async-httpHeader", headers[i].getName() + ":" + headers[i].getValue());
                                    }
                                }
                                if(entity!= null){
                                    String content = EntityUtils.toString(entity);
                                    mLog("Android-async-httpcontent", content);
                                }
                            }
                        }
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param)
                            throws Throwable {
                        HttpResponse resp = (HttpResponse) param.getResult();
                        if (resp != null && HTTP_RESPONSE) {
                            mLog("Status Code", ""+resp.getStatusLine().getStatusCode());
                            Header[] headers = resp.getAllHeaders();
                            if (headers != null) {
                                for (int i = 0; i < headers.length; i++) {
                                    mLog("response", headers[i].getName() + ":" + headers[i].getValue());
                                }
                            }

                        }
                        super.afterHookedMethod(param);
                    }
                });
    }

    public void hookOncreate(LoadPackageParam lpparam){
        String appClassName = lpparam.appInfo.className;
        if (appClassName == null) {
            Method hookOncreateMethod = null;
            try {
                hookOncreateMethod = Application.class.getDeclaredMethod("onCreate", new Class[] {});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                mLog("target0", "target");
            }
            XposedBridge.hookMethod(hookOncreateMethod, new ApplicationOnCreateHook("target0",lpparam));
        }else{
            Class<?> hook_application_class = null;
            try {
                hook_application_class = lpparam.classLoader.loadClass(appClassName);
                if (hook_application_class != null) {
                    Method hookOncreateMethod = hook_application_class.getDeclaredMethod("onCreate", new Class[] {});
                    if (hookOncreateMethod != null) {
                        XposedBridge.hookMethod(hookOncreateMethod, new ApplicationOnCreateHook("target1",lpparam));
                    }
                }
                mLog("target11", "target");
            } catch (Exception e) {
// TODO Auto-generated catch block
                Method hookOncreateMethod;
                try {
                    hookOncreateMethod = Application.class.getDeclaredMethod("onCreate", new Class[] {});
                    if (hookOncreateMethod != null) {
                        XposedBridge.hookMethod(hookOncreateMethod, new ApplicationOnCreateHook("target2",lpparam));
                    }
                    mLog("target21", "target");
                } catch (NoSuchMethodException e1) {
// TODO Auto-generated catch block
                    mLog("target3", "target");
                    e1.printStackTrace();
                }

            }
        }
    }
    private class ApplicationOnCreateHook extends XC_MethodHook {
        public String which;
        public LoadPackageParam lpparam;
        public ApplicationOnCreateHook(String which, LoadPackageParam lpparam){
            this.which = which;
            this.lpparam = lpparam;
        }
        @Override
        public void afterHookedMethod(MethodHookParam param) throws FileNotFoundException {
            mLog("", which);
            if (!HAS_REGISTER_LISENER) {
                mApp = (lpparam.isFirstApplication)?(Application) param.thisObject:mApp;
                IntentFilter filter = new IntentFilter("xuhuafeng");
                mApp.registerReceiver(new CommandBroadcastReceiver(), filter);
                msp = mApp.getSharedPreferences(LOG_FILENAME, Activity.MODE_PRIVATE);
                HAS_REGISTER_LISENER = true;
            }
        }

    }
    private boolean HAS_REGISTER_LISENER = false;
}


