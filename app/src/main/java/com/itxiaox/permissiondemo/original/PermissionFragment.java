package com.itxiaox.permissiondemo.original;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


/**
 * 权限管理封装思路一：
 * @Title: 一个透明的权限管理Activity
 * @Description: 当app中需要权限的时候，跳转到一个透明的Activity中进行处理，
 * 处理完成后，关闭透明的权限PermissionActivity，进行后续逻辑操作。
 *
 * @author: xiao
 * @date:  2019/5/17 10:05
 * @version v1.0
 */
public class PermissionFragment extends Fragment {

    private static final String TAG = "PermissionFragment";
    private static final int REQUEST_PERMISSIONS = 991;
    private  String[] mPermissions ;

    private static Activity mActivity;
    private boolean hasPermisson = true;
    PermissionHelper.PermissionListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity =  getActivity();
        initPermission();
    }

    public void setCallback(PermissionHelper.PermissionListener listener){
        this.mListener = listener;
    }

    public void setPermission(String[] permissions){
        this.mPermissions = permissions;
    }

    /**
     * 初始化相机相关权限
     * 适配6.0+手机的运行时权限
     */
    private void initPermission() {

            //检查权限
            if (ContextCompat.checkSelfPermission(mActivity, mPermissions[0])
                    != PackageManager.PERMISSION_GRANTED) {
                hasPermisson = false;
                // 之前拒绝了权限，但没有点击 不再询问 这个时候让它继续请求权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity,
                        mPermissions[0])) {
                    Toast.makeText(mActivity, "用户曾拒绝"+mPermissions[0], Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(mActivity, mPermissions, REQUEST_PERMISSIONS);
                } else {
                    //注册相机权限
                    ActivityCompat.requestPermissions(mActivity, mPermissions, REQUEST_PERMISSIONS);
                }
            }else {
                back(true);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (REQUEST_PERMISSIONS == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功
                back(true);
            } else {
                // 勾选了不再询问
                Toast.makeText(mActivity, "用户拒绝相机权限", Toast.LENGTH_SHORT).show();
                /**
                 * 跳转到 APP 详情的权限设置页
                 *
                 * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                 */
                Intent intent = goSettingIntent(mActivity);
                startActivity(intent);
                back(false);
            }
        }

    }

    public void back(boolean hasPermission) {
        if(mListener!=null)mListener.onResult(hasPermission,mPermissions);
    }

    /**
     * 获取 APP 详情页面intent
     *
     * @return
     */
    public static Intent goSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }
}
