package com.jack.gps.ui.activity;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jack.gps.BuildConfig;
import com.jack.gps.R;
import com.jack.gps.ui.base.BaseActivity;
import com.jack.gps.ui.db.Constant;
import com.jack.gps.ui.db.test.EptData;
import com.taobao.android.dexposed.utility.Logger;
import com.wuxiaosu.widget.SettingLabelView;
import com.wuxiaosu.widget.utils.PropertiesUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.et_latitude)
    EditText etLatitude;
    @BindView(R.id.et_longitude)
    EditText etLongitude;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.slv_hide_icon)
    SettingLabelView slvHideIcon;
    @BindView(R.id.rl_morra)
    RadioGroup rlMorra;
    @BindView(R.id.rl_dice)
    RadioGroup rlDice;
    @BindView(R.id.rl_step)
    RadioGroup rlStep;

    private final static int EXTERNAL_STORAGE_REQUEST_CODE = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getToolbar().setNavigationIcon(null);

        showModuleActiveInfo(false);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            showPermissionDialog();
        } else {
            initView();
        }

        LocationManager locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        List<String> list = locMgr.getAllProviders();
        for(String c : list) {
            Log.e("------------","LocationManager provider:" + c);
        }

        if (isSuEnable()){
            Log.e("axxxx", "123");
        } else {
            Log.e("axxxx", "321");
        }
    }



    private void initView() {

        bindProperties(etLatitude, Constant.PRO_FILE, R.string.pre_key_latitude, null);
        bindProperties(etLongitude, Constant.PRO_FILE, R.string.pre_key_longitude, null);
        bindProperties(etMoney, Constant.PRO_FILE, R.string.pre_key_money, "0.00");
        bindProperties(rlDice, Constant.PRO_FILE, R.string.pre_key_dice, "0");
        bindProperties(rlMorra, Constant.PRO_FILE, R.string.pre_key_morra, "0");
        bindProperties(rlStep, Constant.PRO_FILE, R.string.pre_key_step, "2");

        slvHideIcon.setCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hideLauncherIcon(isChecked);
            }
        });

        String ax = "24828447e8152d152643087a97790ab55b5cb4a00d9e52dce25ba65073e1fe9b7139d88a8fba2af6dab1d93978fb53e94156bab137110471d292f03c4b0818dffb8e4630e560f98785a0e27efcff01797e04a0b55ee9e72bbc5b64832dd2f321ff2bf9a8ca284356778b220fb960579c9a4ac7de071eb1f34ca179d90afd3b6772f8b2388c191106fae39850d9a2148207e5bae907933e5bd0ea49bb7e0bfd91c4ce381c705fcfeb4753d04de2b46ec750d7487c76cc291afcc4f6da6a765e43b835ccf182760ac99b7c51073df6d01378390671ec02283825da74b52e55ce13b2ea7431cb3adf890edf2726bcaccdb8f2506dfe3bf8eb5c76a875ffe51d5060b68b5a9b1a91925d1c298d58ca2239fc63693e88759ba417717f070249b4d1c16450f6fd738acb808bf1d03c2065dcb8443a74b518f2694aa7acf4ad0fea577e6cbf667f8b845439eac739c487f224cb8a7098f007ba83c3cd8fe9da59be02341f3ec35bcfa87ca68b6d4635b4b69b723bbb7a972c6629949f9a12dabc6c56c01b7a9932538746488f22dcd45a34c939fd2f89e15cc4b4c3ab689f2f2fa081c4";

        //base64解码
        //base64解码

        EptData eptData = new EptData();

        String str2 = eptData.decrypt3(ax);
        Log.e("------------", str2 + "");


    }

    private void showPermissionDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("需要存储卡读写权限来保存应用配置")
                .setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                }).setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                EXTERNAL_STORAGE_REQUEST_CODE);
                    }
                })
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    @OnClick({R.id.iv_location})
    public void onClickListener() {
        TencentMapLiteActivity.actionStart(this,
                etLatitude.getText().toString(), etLongitude.getText().toString());
    }

    public void hideLauncherIcon(boolean isHide) {
        PackageManager packageManager = this.getPackageManager();
        int hide = isHide ? PackageManager.COMPONENT_ENABLED_STATE_DISABLED : PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        packageManager.setComponentEnabledSetting(getAliasComponentName(), hide, PackageManager.DONT_KILL_APP);
    }

    private ComponentName getAliasComponentName() {
        return new ComponentName(MainActivity.this, "com.wuxiaosu.wechathelper.activity.MainActivity_Alias");
    }

    private void bindProperties(View view, final String properties, final int preStrResId, Object defaultValue) {

        if (view instanceof EditText) {
            String temp = PropertiesUtils.getValue(properties, getString(preStrResId), (String) defaultValue);
            ((EditText) view).setText(temp);
            ((EditText) view).addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    PropertiesUtils.putValue(properties, getString(preStrResId), s.toString());
                }
            });
            return;
        }
        if (view instanceof RadioGroup) {
            String temp = PropertiesUtils.getValue(properties, getString(preStrResId), (String) defaultValue);

            if (!TextUtils.isEmpty(temp)) {
                PropertiesUtils.putValue(properties, getString(preStrResId), temp);
                for (int i = 0; i < ((RadioGroup) view).getChildCount(); i++) {
                    if (((RadioGroup) view).getChildAt(i).getContentDescription().toString().equals(temp)) {
                        ((RadioButton) ((RadioGroup) view).getChildAt(i)).setChecked(true);
                        break;
                    }
                }
            }
            ((RadioGroup) view).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    PropertiesUtils.putValue(properties, getString(preStrResId), findViewById(checkedId).getContentDescription().toString());
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TencentMapLiteActivity.REQUEST_CODE && resultCode == RESULT_OK) {
            etLatitude.setText(data.getStringExtra(TencentMapLiteActivity.LAT_KEY));
            etLongitude.setText(data.getStringExtra(TencentMapLiteActivity.LON_KEY));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            showInfo();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    private void showInfo() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_about_content, null);
        TextView mTvVersionName = view.findViewById(R.id.tv_version_name);
        TextView mTvInfo = view.findViewById(R.id.tv_info);
        final TextView mTvUrl = view.findViewById(R.id.tv_url);
        mTvUrl.setText(Html.fromHtml("<a href=''>https://github.com/wuxiaosu/XposedWechatHelper</a>"));
        mTvUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendURLIntent(((TextView) v).getText().toString());
            }
        });
        mTvVersionName.setText(getString(R.string.app_name) + " v" + BuildConfig.VERSION_NAME);
        mTvInfo.setText("进群一起玩"
                + getContactInfo()
                + "\n更多详情：");
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("关于")
                .setView(view)
                .create();
        alertDialog.show();
    }

    private String getContactInfo() {
        return "\nQ群：[123320001]\n" +
                "或者请作者喝杯豆浆\n";
    }

    private void sendURLIntent(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri contentUrl = Uri.parse(url);
        intent.setData(contentUrl);
        startActivity(intent);
    }

    /**
     * 模块激活信息
     *
     * @param isModuleActive
     */
    private void showModuleActiveInfo(boolean isModuleActive) {
        if (!isModuleActive) {
            Toast.makeText(this, "模块未激活", Toast.LENGTH_SHORT).show();
        } else {
            showInfo();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initView();
        } else {
            this.finish();
        }
    }

    /**
     * 是否存在su命令，并且有执行权限
     *
     * @return 存在su命令，并且有执行权限返回true
     */
    public static boolean isSuEnable() {
        File file = null;
        String[] paths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/", "/su/bin/"};
        try {
            for (String path : paths) {
                file = new File(path + "su");
                if (file.exists() && file.canExecute()) {
                    Log.i("1111111", "find su in : " + path);
                    return true;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        return false;
    }
}
