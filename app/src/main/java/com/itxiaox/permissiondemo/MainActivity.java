package com.itxiaox.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.itxiaox.permissiondemo.original.PermissionHelper;

import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(menuItem -> {
            switchTab(menuItem);
            return true;
        });
    }


    public void switchTab(MenuItem menuItem){
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                requestPermission();
                break;
            case R.id.navigation_dashboard:
                requestPermission2();
                break;
            case R.id.navigation_notifications:
                break;
        }
    }

    //方式一，以原始的方式请求权限，这里采用了一个透明的Fragment代理Activity请求的回调处理结果，将结果通过一般的回调接口的方式返回
    public void requestPermission() {
        //方法一，通过一个透明的Activity代理，但需要注意的是，需要在Manifest.xml中备注
        PermissionHelper.requestPermission(this, (hasPermission, pemission) -> {
                    String text = hasPermission ? "获取到权限了" : "没有权限";
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                },
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        );
    }


    Disposable disposable;
    public void requestPermission2() {
        //在Activity中调用
//        RxPermissions rxPermissions = new RxPermissions(this);
//        disposable = rxPermissions
//                .request(Manifest.permission.CAMERA)
//                .subscribe(grandted -> {
//                    if (grandted) {
//                        Toast.makeText(MainActivity.this, "授权", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(MainActivity.this, "未授权", Toast.LENGTH_SHORT).show();
//
//                    }
//                });

        //在普通类中使用动态权限

        FragmentActivity activity = MainActivity.this;
        RxPermissionTest permissionTest = new RxPermissionTest(activity);

        permissionTest.requestPermission2();

    }



}
