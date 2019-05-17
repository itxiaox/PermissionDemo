package com.itxiaox.permissiondemo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.itxiaox.permissiondemo.original.PermissionActivity;
import com.itxiaox.permissiondemo.original.PermissionFragment;
import com.itxiaox.permissiondemo.original.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void requestPermission(View view) {
        //方法一，通过一个透明的Activity代理，但需要注意的是，需要在Manifest.xml中备注
//       PermissionActivity.requestPermission(this, new PermissionActivity.PermissionListener() {
//           @Override
//           public void onResult(boolean hasPermission, String[] pemission) {
//               String text = hasPermission?"获取到权限了":"没有权限";
//               Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
//           }
//       },
//       Manifest.permission.CAMERA,
//       Manifest.permission.WRITE_EXTERNAL_STORAGE,
//       Manifest.permission.READ_EXTERNAL_STORAGE
//       );
        PermissionHelper.requestPermission(this, new PermissionHelper.PermissionListener() {
           @Override
           public void onResult(boolean hasPermission, String[] pemission) {
               String text = hasPermission?"获取到权限了":"没有权限";
               Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
           }
       },
       Manifest.permission.CAMERA,
       Manifest.permission.WRITE_EXTERNAL_STORAGE,
       Manifest.permission.READ_EXTERNAL_STORAGE
       );
    }
}
