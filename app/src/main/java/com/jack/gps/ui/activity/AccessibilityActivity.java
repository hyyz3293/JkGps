package com.jack.gps.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jack.gps.R;
import com.jack.gps.auxiliary.AuxiliaryClickService;
import com.jack.gps.ui.base.BaseActivity;
import com.jack.gps.utils.AppUtil;
import com.jack.gps.xposed.ins.S;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jack.gps.auxiliary.AuxiliaryClickService.BROARD_ACIOTN_CLICK;

public class AccessibilityActivity extends BaseActivity implements AccessibilityManager.AccessibilityStateChangeListener {

    private AccessibilityManager accessibilityManager;

    @BindView(R.id.layout_control_accessibility_icon)
    ImageView pluginStatusIcon;
    @BindView(R.id.layout_control_accessibility_text)
    TextView pluginStatusText;
    @BindView(R.id.open_accessibility)
    LinearLayout openAccessibility;


    @BindView(R.id.start_taaobao)
    TextView mStartTvs;
    @BindView(R.id.start_tamll)
    TextView mStartTamll;
    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.aaa)
    RadioGroup radioGroup;
    @BindView(R.id.radio_tb)
    RadioButton tb;
    @BindView(R.id.radio_tm)
    RadioButton tm;


    private boolean istbtm = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitity_accessibility);


        //监听AccessibilityService 变化
        accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        updateServiceStatus();

        //new TimeThread().start(); //启动新的线程

        initview();

        tb.setChecked(true);
        tm.setEnabled(false);
    }

    private void initview() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio_tb:
                        istbtm = true;
                        break;
                    case R.id.radio_tm:
                        istbtm = false;
                        break;
                }
            }
        });


    }



    @OnClick({R.id.open_accessibility, R.id.start_taaobao, R.id.start_tamll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_accessibility:
                openAccessibilityServiceSettings();
                break;
            case R.id.start_taaobao: {
                Intent intent = new Intent(BROARD_ACIOTN_CLICK);
                intent.putExtra("flag", 1);
                sendBroadcast(intent);
            }break;
            case R.id.start_tamll: {
                Intent intent = new Intent(BROARD_ACIOTN_CLICK);
                intent.putExtra("flag", 2);
                sendBroadcast(intent);
            }break;
        }
    }


    /**
     * 打开辅助服务的设置
     */
    private void openAccessibilityServiceSettings() {
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "找[AAAAGPS],然后开启/关闭服务", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新当前 HongbaoService 显示状态
     */
    private void updateServiceStatus() {
        if (AuxiliaryClickService.isRunning()) {
            pluginStatusText.setText("关闭插件");
            pluginStatusIcon.setBackgroundResource(R.mipmap.icon_plug_stop);
        } else {
            pluginStatusText.setText("开启插件");
            pluginStatusIcon.setBackgroundResource(R.mipmap.icon_plug_start);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateServiceStatus();
    }

    @Override
    public void onAccessibilityStateChanged(boolean enabled) {
        updateServiceStatus();
    }

    @Override
    protected void onDestroy() {
        //移除监听服务
        accessibilityManager.removeAccessibilityStateChangeListener(this);
        super.onDestroy();
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(50);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

   private boolean aaa = true;

    //在主线程里面处理消息并更新UI界面
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();//获取系统时间
                    String s = AppUtil.getNewTime();
                    time.setText(s); //更新时间
                                   //1578571199500
                                   //1578571199000
                                   //1578657599000
                    if (sysTime >= 1578657599950l && aaa) {
                        aaa = false;
                        //sss();
                        Intent intent = new Intent(BROARD_ACIOTN_CLICK);

                        if (istbtm) {
                            intent.putExtra("flag", 1);
                        } else {
                            intent.putExtra("flag", 2);
                        }

                        sendBroadcast(intent);
                    }
                    break;
                default:
                    break;

            }
        }
    };


}
