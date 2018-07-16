package kh.com.metfone.emoney.eshop.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.ReceiptInfo;
import kh.com.metfone.emoney.eshop.firebase.app.Config;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.confirmphone.CheckStatusUSSDView;
import kh.com.metfone.emoney.eshop.ui.customs.TSnackbar;
import kh.com.metfone.emoney.eshop.ui.generateqrcode.CheckStatusQRCodeView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.NewReceiptView;
import kh.com.metfone.emoney.eshop.ui.newreceipt.ViewReceiptView;
import kh.com.metfone.emoney.eshop.ui.receipts.ReceiptsView;
import kh.com.metfone.emoney.eshop.ui.reports.ReportsView;
import kh.com.metfone.emoney.eshop.ui.setting.SettingView;
import kh.com.metfone.emoney.eshop.ui.shopdetail.ShopDetailView;
import kh.com.metfone.emoney.eshop.ui.shoplist.ShopListView;
import kh.com.metfone.emoney.eshop.ui.shopupdate.ShopUpdateView;
import kh.com.metfone.emoney.eshop.ui.staffdetail.StaffDetailView;
import kh.com.metfone.emoney.eshop.ui.staffs.StaffListView;
import kh.com.metfone.emoney.eshop.ui.staffupdate.StaffUpdateView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DCV on 3/2/2018.
 */

public class HomeActivity extends BaseDialogActivity implements HomeMvpView {

    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;
    @BindView(R.id.bottom_bar)
    BottomNavigationView bottomBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_action)
    TextView toolbarAction;
    @BindView(R.id.txt_back)
    TextView txt_back;
    @BindView(R.id.img_logo_or_back)
    ImageView img_logo_or_back;
    @BindView(R.id.lin_back_action)
    LinearLayout lin_back_action;


    public boolean isHaveBackAction = false;
    @Inject
    HomePresenter homePresenter;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private ReceiptInfo mReceiptInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        AppUtils.setLanguage(this, sharePreferenceHelper.getLanguage());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        homePresenter.attachView(this);
        initView();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    ReceiptInfo receiptInfo = intent.getParcelableExtra(Const.KEY_RECEIPT_INFO);
                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                    if (fragments != null && fragments.size() > 0) {
                        if (fragments.get(0).getTag().equals(CheckStatusQRCodeView.class.getSimpleName()) ||
                                fragments.get(0).getTag().equals(ViewReceiptView.class.getSimpleName()) ||
                                fragments.get(0).getTag().equals(NewReceiptView.class.getSimpleName()) ||
                                fragments.get(0).getTag().equals(CheckStatusUSSDView.class.getSimpleName())) {
                            if (receiptInfo.getStatus() == Const.VALUE_WAIT_PAID
                                    && receiptInfo.getReceiptType() == Const.VALUE_RECEIPT_BY_QR
                                    && !fragments.get(0).getTag().equals(CheckStatusQRCodeView.class.getSimpleName())) {
                                replaceFragment(CheckStatusQRCodeView.newInstance(receiptInfo), R.id.container, false);
                            } else if (receiptInfo.getStatus() == Const.VALUE_ALREADY_PAID && !fragments.get(0).getTag().equals(ViewReceiptView.class.getSimpleName())) {
                                replaceFragment(ViewReceiptView.newInstance(receiptInfo), R.id.container, false);
                            }
                        } else {
                            mReceiptInfo = receiptInfo;
                        }
                    }
                }
            }
        };

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
        // clear the notification area when the app is opened
//        NotificationUtils.clearNotifications(this);
    }


    private void initView() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        BottomNavigationViewHelper.removeShiftMode(bottomBar);

        bottomBar.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setToolbar(0, null);
                        setTitleToolbarLine(true);
                        createBackStack();
//                        getToolbarAction().setVisibility(View.GONE); afafa
                        switch (item.getItemId()) {
                            case R.id.action_new_receipt:
                                if (mReceiptInfo != null) {
                                    replaceFragment(ViewReceiptView.newInstance(mReceiptInfo), R.id.container, false);
                                    mReceiptInfo = null;
                                    break;
                                }
                                if (newReceiptFragment != null) {
                                    List<Fragment> fragments = getSupportFragmentManager().getFragments();
                                    if (fragments != null && fragments.size() > 0) {
                                        if (fragments.get(0).getTag().equals(CheckStatusQRCodeView.class.getSimpleName()) ||
                                                fragments.get(0).getTag().equals(ViewReceiptView.class.getSimpleName()) ||
                                                fragments.get(0).getTag().equals(CheckStatusUSSDView.class.getSimpleName())) {
                                            replaceNewReceiptFragment();
                                            break;
                                        }
                                    }
                                    replaceFragment(newReceiptFragment, R.id.container, false);
                                } else {
                                    replaceNewReceiptFragment();
                                }
                                break;
                            case R.id.action_receipts:
                                replaceFragment(ReceiptsView.newInstance(), R.id.container, false);
                                toolbarTitle.setText(R.string.receipts);
                                break;
                            case R.id.action_reports:
                                replaceFragment(ReportsView.newInstance(), R.id.container, false);
                                toolbarTitle.setText(R.string.reports);
                                break;
                            case R.id.action_staffs:
                                List<Fragment> fragmentsInStaff = getSupportFragmentManager().getFragments();
                                if (fragmentsInStaff != null && fragmentsInStaff.size() > 0) {
                                    if (fragmentsInStaff.get(0).getTag().equals(StaffListView.class.getSimpleName()) ||
                                            fragmentsInStaff.get(0).getTag().equals(StaffDetailView.class.getSimpleName()) ||
                                            fragmentsInStaff.get(0).getTag().equals(StaffUpdateView.class.getSimpleName())) {
                                        replaceStaffFragment();
                                        staffListFragment.clear();
                                        staffFragmentManager = null;
                                        break;
                                    }
                                }
                                if (staffListFragment != null && staffListFragment.size() > 1) {
                                    replaceFragment(staffListFragment.get(0), R.id.container, false);
                                    for (int i = 1; i < staffListFragment.size(); i++) {
                                        replaceFragment(staffListFragment.get(i), R.id.container, true);
                                    }
                                    staffListFragment.clear();
                                } else {
                                    replaceStaffFragment();
                                }
                                toolbarTitle.setMaxWidth((int) AppUtils.convertDpToPixel(getBaseContext(), getResources().getDimension(R.dimen._300sdp)));
                                break;
                            case R.id.action_setting:
                                List<Fragment> fragmentsInSetting = getSupportFragmentManager().getFragments();
                                if (fragmentsInSetting != null && fragmentsInSetting.size() > 0) {
                                    if (fragmentsInSetting.get(0).getTag().equals(SettingView.class.getSimpleName()) ||
                                            fragmentsInSetting.get(0).getTag().equals(ShopListView.class.getSimpleName()) ||
                                            fragmentsInSetting.get(0).getTag().equals(ShopDetailView.class.getSimpleName()) ||
                                            fragmentsInSetting.get(0).getTag().equals(ShopUpdateView.class.getSimpleName())) {
                                        replaceSettingFragment();
                                        settingListFragment.clear();
                                        settingFragmentManager = null;
                                        break;
                                    }
                                }
                                if (settingListFragment != null && settingListFragment.size() > 1) {
                                    replaceFragment(settingListFragment.get(0), R.id.container, false);
                                    for (int i = 1; i < settingListFragment.size(); i++) {
                                        replaceFragment(settingListFragment.get(i), R.id.container, true);
                                    }
                                    settingListFragment.clear();
                                } else {
                                    replaceSettingFragment();
                                }
                                break;

                        }
                        return true;
                    }
                });
        Intent intent = getIntent();
        if (Const.KEY_NOTIFY_ACTION.equals(intent.getAction())) {
            bottomBar.setSelectedItemId(R.id.action_new_receipt);
            ReceiptInfo receiptInfo = intent.getParcelableExtra(Const.KEY_RECEIPT_INFO);
            if (receiptInfo.getStatus() == Const.VALUE_WAIT_PAID
                    && receiptInfo.getReceiptType() == Const.VALUE_RECEIPT_BY_QR) {
                replaceFragment(CheckStatusQRCodeView.newInstance(receiptInfo), R.id.container, false);
            } else if (receiptInfo.getStatus() == Const.VALUE_ALREADY_PAID) {
                replaceFragment(ViewReceiptView.newInstance(receiptInfo), R.id.container, false);
            }
            return;
        }
        if (intent.getExtras() != null && intent.getExtras().containsKey(Const.KEY_FRAGMENT)) {
            if (intent.getExtras().getString(Const.KEY_FRAGMENT).equals(Const.KEY_SETTING_FRAGMENT)) {
                bottomBar.setSelectedItemId(R.id.action_setting);
                return;
            }
        }
        bottomBar.setSelectedItemId(R.id.action_new_receipt);
    }

    private void createBackStack() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            if (fragments.get(0).getTag().equals(ShopListView.class.getSimpleName()) ||
                    fragments.get(0).getTag().equals(ShopDetailView.class.getSimpleName()) ||
                    fragments.get(0).getTag().equals(ShopUpdateView.class.getSimpleName())) {
                settingFragmentManager = getSupportFragmentManager();
                createShopBackStackList();
            }

            if (fragments.get(0).getTag().equals(StaffDetailView.class.getSimpleName()) ||
                    fragments.get(0).getTag().equals(StaffUpdateView.class.getSimpleName())) {
                staffFragmentManager = getSupportFragmentManager();
                createStaffBackStackList();
            }
        }
    }

    private void replaceNewReceiptFragment() {
        replaceFragment(NewReceiptView.newInstance(), R.id.container, false);
    }

    private void replaceSettingFragment() {
        replaceFragment(SettingView.newInstance(), R.id.container, false);
    }

    private void replaceStaffFragment() {
        replaceFragment(StaffListView.newInstance(), R.id.container, false);
        toolbarTitle.setText(R.string.staffs);
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragmentSave != null) {
            replaceFragment(fragmentSave, R.id.container, false);
        }
    }

    public void showErrorMessage(int messageResId) {
        showErrorMessage(getResources().getString(messageResId));
    }

    public void setTitleToolbarLine(boolean isSingleLine) {
        if (isSingleLine) {
            toolbarTitle.setSingleLine(true);
            toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            toolbarTitle.setSingleLine(false);
            toolbarTitle.setMaxLines(5);
        }
    }

    public void showErrorMessage(String message) {
        TSnackbar snackbar = TSnackbar
                .make(layoutParent, message, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundResource(R.color.colorRed);
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public TextView getToolbarTitle() {
        return toolbarTitle;
    }

    public TextView getToolbarAction() {
        return toolbarAction;
    }

    public ImageView getLogoImage() {
        return img_logo_or_back;
    }

    public LinearLayout getBackAction() {
        return lin_back_action;
    }

    public void setToolbar(int back_img_id, String rightCommand) {
        if (back_img_id > 0) {
            isHaveBackAction = true;
            img_logo_or_back.setImageResource(back_img_id);
            txt_back.setVisibility(View.VISIBLE);
            txt_back.setText(R.string.back);
        } else if (back_img_id == 0) {
            isHaveBackAction = false;
            img_logo_or_back.setImageResource(R.drawable.ic_small_logo);
            txt_back.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(rightCommand)) {
            toolbarAction.setVisibility(View.GONE);
        } else {
            toolbarAction.setVisibility(View.VISIBLE);
            toolbarAction.setText(rightCommand);
        }
    }


    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            new MaterialDialog.Builder(this)
                    .icon(getResources().getDrawable(R.mipmap.ic_launcher))
                    .content(getString(R.string.exit_confirm))
                    .title(getString(R.string.app_name))
                    .positiveText(R.string.ok)
                    .negativeText(R.string.cancel)
                    .negativeColor(getResources().getColor(R.color.colorGray2))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            HomeActivity.super.onBackPressed();
                        }
                    })
                    .build()
                    .show();
        } else {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null && fragments.size() > 0) {
                if (fragments.get(0).getTag().equals(SettingView.class.getSimpleName()) ||
                        fragments.get(0).getTag().equals(ShopListView.class.getSimpleName()) ||
                        fragments.get(0).getTag().equals(ShopDetailView.class.getSimpleName()) ||
                        fragments.get(0).getTag().equals(ShopUpdateView.class.getSimpleName())) {
                    settingFragmentManager = getSupportFragmentManager();
                }

                if (fragments.get(0).getTag().equals(StaffListView.class.getSimpleName()) ||
                        fragments.get(0).getTag().equals(StaffDetailView.class.getSimpleName()) ||
                        fragments.get(0).getTag().equals(StaffUpdateView.class.getSimpleName())) {
                    staffFragmentManager = getSupportFragmentManager();
                }
            }
            super.onBackPressed();
        }
    }


    @Override
    protected void onDestroy() {
        if (homePresenter != null) {
            homePresenter.detachView();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onDestroy();
    }

}
