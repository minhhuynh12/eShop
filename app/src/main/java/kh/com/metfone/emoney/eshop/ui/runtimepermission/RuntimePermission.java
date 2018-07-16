package kh.com.metfone.emoney.eshop.ui.runtimepermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by minhhc on 6/21/2018.
 */

public class RuntimePermission {
    public static final int REQUEST_PHONE_STATE_PERMISSION_CODE = 7;

    public static boolean CheckingPermissionIsEnabledOrNot(Context context) {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED;
//        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
//                SecondPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime Phone state permission
     */
    public static void requestReadAndSendSmsPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, REQUEST_PHONE_STATE_PERMISSION_CODE);
    }

}
