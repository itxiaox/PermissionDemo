package com.itxiaox.permissiondemo;

import android.Manifest;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

public class RxPermissionTest {

    public FragmentActivity activity;

    public RxPermissionTest(FragmentActivity activity){
        this.activity = activity;
    }

    Disposable disposable;

    public void requestPermission2() {
        RxPermissions rxPermissions = new RxPermissions(activity);
        disposable = rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(grandted -> {
                    if (grandted) {
                        Toast.makeText(activity, "授权", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity, "未授权", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
