package com.itxiaox.permissiondemo.original;

import android.app.Activity;
import android.app.FragmentManager;

public class PermissionHelper {

    private static final String TAG = "PermissionHelper";

    private static PermissionListener mListener;
    public interface PermissionListener{
        void onResult(boolean hasPermission, String[] pemission);

    }

    public static void requestPermission(Activity activity,
                                         PermissionListener listener, String... permissions){

        if(permissions==null)return;
        mListener = listener;
        String[] mPermissions = new String[permissions.length];
        for(int i=0;i<permissions.length;i++){
            mPermissions[i] = permissions[i];
        }
        createFramgent(activity,mPermissions);
    }

    private static PermissionFragment createFramgent(Activity activity,String[] mPermissions) {
        PermissionFragment permissionFragment = new PermissionFragment();
            permissionFragment.setPermission(mPermissions);
            permissionFragment.setCallback(mListener);
            FragmentManager fragmentManager = activity.getFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .add(permissionFragment, TAG)
                    .commitAllowingStateLoss();
            fragmentManager.executePendingTransactions();
        return permissionFragment;
    }

    private static PermissionFragment findFragment(Activity activity) {
        return (PermissionFragment) activity.getFragmentManager().findFragmentByTag(TAG);
    }
}
