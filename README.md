# Android 权限管理学习研究


[官方文档](https://developer.android.google.cn/guide/topics/permissions/overview)

# 方式一 
 original 包名下面的是，使用Android原始的动态权限申请

  思路，采用一个透明的Activity或者Fragment来实现权限代码统一集中处理操作
  
  调用样例：
  
  ```
  
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
       
   ```
   
#   方式二 [PermissionDispatcher库](https://github.com/permissions-dispatcher/PermissionsDispatcher)
   
   采用开源框架 PermissionDispatcher库, 
   * 最新的版本： 4.x only supports Jetpack. If you still use appcompat 3.x is the way to go.
   
 
# 方式三 [RxPermission库](https://github.com/tbruyelle/RxPermissions)
Rx 和RxJava 搭配起来使用
```
    implementation 'com.github.tbruyelle:rxpermissions:0.10.2'
    implementation "io.reactivex.rxjava2:rxjava:2.2.8"
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
	
````


# 方式四 [AndPermission](https://github.com/yanzhenjie/AndPermission)