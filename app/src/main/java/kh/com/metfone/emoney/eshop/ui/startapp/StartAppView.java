package kh.com.metfone.emoney.eshop.ui.startapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.ReceiptInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.firebase.app.Config;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.dialog.Popup;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.login.LoginView;
import kh.com.metfone.emoney.eshop.ui.runtimepermission.RuntimePermission;
import kh.com.metfone.emoney.eshop.ui.upgradeversion.UpgradeVersionView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by DCV on 3/9/2018.
 */

public class StartAppView extends BaseDialogActivity implements StartAppMvpView {
    @Inject
    StartAppPresenter startAppPresenter;
    private CompositeDisposable compositeDisposable;
    private UpgradeVersionView upgradeVersionView;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;
    private boolean flaw ;
    private Dialog dialog;


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RuntimePermission.REQUEST_PHONE_STATE_PERMISSION_CODE:
                flaw = RuntimePermission.CheckingPermissionIsEnabledOrNot(StartAppView.this);
                if (flaw){
                    getActivityComponent().inject(this);
                    startAppPresenter.attachView(this);
                    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        String version = pInfo.versionName;
                        startAppPresenter.saveClientLogFailed(version);
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }else {
                    Button btnOK = (Button) dialog.findViewById(R.id.btnOK);
                    btnOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    dialog.show();
                }
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!isTaskRoot()) {
             super.onCreate(savedInstanceState);
            // app is in foreground, broadcast the push message
            ReceiptInfo receiptInfo = getReceiptInfoFromNotify();
            if (receiptInfo != null) {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra(Const.KEY_RECEIPT_INFO, receiptInfo);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            }
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start_app);

        initNotiDialog();

//        getActivityComponent().inject(this);
//        startAppPresenter.attachView(this);
//        try {
//            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
//            String version = pInfo.versionName;
//            startAppPresenter.saveClientLogFailed(version);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }

        flaw = RuntimePermission.CheckingPermissionIsEnabledOrNot(this);
        if (!flaw){
            RuntimePermission.requestReadAndSendSmsPermission(this);
        }else {
            getActivityComponent().inject(this);
            startAppPresenter.attachView(this);
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String version = pInfo.versionName;
                startAppPresenter.saveClientLogFailed(version);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void checkVersionAppSuccess(VersionInformation information) {
        if (information.getVersionInforChild() != null) {
            upgradeVersionView = new UpgradeVersionView(this, information, sharePreferenceHelper.getLanguage());
            upgradeVersionView.show();
            onListenQuitUpgrade();
        } else {
            startAppPresenter.checkLoginStatus();
        }
        startAppPresenter.getCommonConfigInfo();
    }

    private void onListenQuitUpgrade() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(upgradeVersionView.getOnQuitUpgrade().subscribe(new Consumer<VersionInformation>() {
                @Override
                public void accept(VersionInformation information) throws Exception {
                    if (information.getVersionInforChild().isForceUpgrade()) {
                        finish();
                    } else {
                        startAppPresenter.checkLoginStatus();
                    }
                }
            }));
        }
    }

    @Override
    public void checkVersionAppFailed(String errorMessage) {
        startAppPresenter.checkLoginStatus();
    }


    @Override
    public void goNextScreen(boolean isLogin) {
        goToNextScreen(isLogin);
    }

    @Override
    public void getUserInforSuccess(UserInformation userInformation) {
        Intent notificationIntent = getIntent();
        Intent intent = new Intent(this, HomeActivity.class);
        DataUtils.setUserInformation(userInformation);
        intent.putExtra(Const.KEY_USER_INFORMATION, userInformation);
        if (notificationIntent != null && notificationIntent.getExtras() != null && notificationIntent.getExtras().containsKey(Const.KEY_RECEIPT_ID)) {
            ReceiptInfo receiptInfo = new ReceiptInfo();
            receiptInfo.setTransReceiptId(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_RECEIPT_ID)));
            if (notificationIntent.getExtras().containsKey(Const.KEY_AMOUNT)) {
                receiptInfo.setAmount(notificationIntent.getExtras().getString(Const.KEY_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_TOTAL_AMOUNT)) {
                receiptInfo.setTotalAmount(notificationIntent.getExtras().getString(Const.KEY_TOTAL_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_INVOICE_TITLE)) {
                receiptInfo.setInvoiceTitle(notificationIntent.getExtras().getString(Const.KEY_INVOICE_TITLE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_STATUS)) {
                receiptInfo.setStatus(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_STATUS)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_ACTION_TYPE)) {
                receiptInfo.setReceiptType(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_ACTION_TYPE)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT)) {
                receiptInfo.setDiscount(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT_AMOUNT)) {
                receiptInfo.setDiscountAmount(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT_TYPE)) {
                receiptInfo.setDiscountType(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT_TYPE)));
            }

            if (notificationIntent.getExtras().containsKey(Const.KEY_PAID_TIME)) {
                receiptInfo.setPaidTime(Long.parseLong(notificationIntent.getExtras().getString(Const.KEY_PAID_TIME)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_CURRENCY_CODE)) {
                receiptInfo.setCurrencyCode(notificationIntent.getExtras().getString(Const.KEY_CURRENCY_CODE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_RECEIPT_CODE)) {
                receiptInfo.setReceiptCode(notificationIntent.getExtras().getString(Const.KEY_RECEIPT_CODE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_PAID_TID)) {
                receiptInfo.setPaidTid(notificationIntent.getExtras().getString(Const.KEY_PAID_TID));
            }
            intent.putExtra(Const.KEY_RECEIPT_INFO, receiptInfo);
            intent.setAction(Const.KEY_NOTIFY_ACTION);
            startActivity(intent);
            ActivityCompat.finishAffinity(this);
        } else {
            startActivity(intent);
            ActivityCompat.finishAffinity(this);
        }
    }

    private ReceiptInfo getReceiptInfoFromNotify() {
        Intent notificationIntent = getIntent();
        if (notificationIntent != null && notificationIntent.getExtras() != null && notificationIntent.getExtras().containsKey(Const.KEY_RECEIPT_ID)) {
            ReceiptInfo receiptInfo = new ReceiptInfo();
            receiptInfo.setTransReceiptId(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_RECEIPT_ID)));
            if (notificationIntent.getExtras().containsKey(Const.KEY_AMOUNT)) {
                receiptInfo.setAmount(notificationIntent.getExtras().getString(Const.KEY_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_TOTAL_AMOUNT)) {
                receiptInfo.setTotalAmount(notificationIntent.getExtras().getString(Const.KEY_TOTAL_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_INVOICE_TITLE)) {
                receiptInfo.setInvoiceTitle(notificationIntent.getExtras().getString(Const.KEY_INVOICE_TITLE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_STATUS)) {
                receiptInfo.setStatus(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_STATUS)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_ACTION_TYPE)) {
                receiptInfo.setReceiptType(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_ACTION_TYPE)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT)) {
                receiptInfo.setDiscount(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT_AMOUNT)) {
                receiptInfo.setDiscountAmount(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT_AMOUNT));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_DISCOUNT_TYPE)) {
                receiptInfo.setDiscountType(Integer.parseInt(notificationIntent.getExtras().getString(Const.KEY_DISCOUNT_TYPE)));
            }

            if (notificationIntent.getExtras().containsKey(Const.KEY_PAID_TIME)) {
                receiptInfo.setPaidTime(Long.parseLong(notificationIntent.getExtras().getString(Const.KEY_PAID_TIME)));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_CURRENCY_CODE)) {
                receiptInfo.setCurrencyCode(notificationIntent.getExtras().getString(Const.KEY_CURRENCY_CODE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_RECEIPT_CODE)) {
                receiptInfo.setReceiptCode(notificationIntent.getExtras().getString(Const.KEY_RECEIPT_CODE));
            }
            if (notificationIntent.getExtras().containsKey(Const.KEY_PAID_TID)) {
                receiptInfo.setPaidTid(notificationIntent.getExtras().getString(Const.KEY_PAID_TID));
            }
            return receiptInfo;
        } else {
            return null;
        }
    }

    @Override
    public void setLocale(String language) {
        AppUtils.setLanguage(this, language);
    }

    private void goToNextScreen(boolean isLogin) {
        if (isLogin) {
            startAppPresenter.getUserInfor();
        } else {
            Intent intent = new Intent(this, LoginView.class);
            startActivity(intent);
            finish();
        }

    }

    private void initNotiDialog() {
//        dialog = new Popup(R.layout.dialog_checked_permission);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_checked_permission);
        dialog.setCanceledOnTouchOutside(false);
    }


}
