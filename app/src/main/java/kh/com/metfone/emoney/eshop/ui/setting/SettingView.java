package kh.com.metfone.emoney.eshop.ui.setting;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import kh.com.metfone.emoney.eshop.MyApplication;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.event.ChangeExchangeRateSuccess;
import kh.com.metfone.emoney.eshop.data.models.AppConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ShopConfig;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.printer.BluetoothService;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.changepassword.ChangePasswordDialog;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.login.ChangeLanguageDialog;
import kh.com.metfone.emoney.eshop.ui.login.LoginView;
import kh.com.metfone.emoney.eshop.ui.shopdetail.ShopDetailView;
import kh.com.metfone.emoney.eshop.ui.shoplist.ShopListView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by DCV on 3/5/2018.
 */

public class SettingView extends BaseDialogFragment implements SettingMvpView {
    @BindView(R.id.shop_row)
    RelativeLayout shop_row;
    @BindView(R.id.ll_change_exchange_rate)
    RelativeLayout llChangeExchangeRate;
    @BindView(R.id.ll_shop_management)
    LinearLayout llShopManagement;
    @BindView(R.id.img_user)
    ImageView imgUserAvatar;
    @BindView(R.id.txt_fullname)
    TextView txtFullName;
    @BindView(R.id.txt_id)
    TextView txtId;
    @BindView(R.id.txt_phone_number)
    TextView txtPhoneNumber;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_exchange_rate)
    TextView txtExchangeRate;
    @BindView(R.id.txt_current_language)
    TextView txtLanguage;
    @BindView(R.id.txt_printer_name)
    TextView txtPrinterName;
    @BindView(R.id.txt_connect_status)
    TextView txtConnectStatus;
    private UserInformation userInformation;
    private CompositeDisposable compositeDisposable;
    private ChangeLanguageDialog changeLanguageDialog;
    private ChangeExchangeRateDialog changeExchangeRateDialog;
    private String exchangeRateValue;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Inject
    SettingPresenter settingPresenter;
    private BluetoothAdapter mBluetoothAdapter = null;
    private ChoosePrinterDeviceDialog choosePrinterDeviceDialog;
    private BluetoothService mService = null;

    public static SettingView newInstance() {
        SettingView settingView = new SettingView();
        return settingView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mService = MyApplication.getmService();
        if (mService == null) {
            mService = new BluetoothService(getContext(), mHandler);
        }
        mService.setmHandler(mHandler);
        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        settingPresenter.attachView(this);
        return view;
    }

    private void initView() {
        txtFullName.setText(userInformation.getUserInfo().getStaffName());
        txtId.setText(userInformation.getUserInfo().getStaffCode());
        txtPhoneNumber.setText(userInformation.getUserInfo().getMsisdn());
        if (sharePreferenceHelper.getLanguage().equals(getResources().getString(R.string.en_language))) {
            txtLanguage.setText(getResources().getString(R.string.english));
        } else {
            txtLanguage.setText(getResources().getString(R.string.cambodia));
        }
        setShopManagement();
    }

    private void setShopManagement() {
        if (Const.VALUE_SHOP_OWNER == userInformation.getUserInfo().getStaffType() ||
                Const.VALUE_CHAIN_STORE_MANAGEMENT == userInformation.getUserInfo().getStaffType()) {
            txtShopName.setText(userInformation.getShopInfor().getShopName());
            llShopManagement.setVisibility(View.VISIBLE);
            if (Const.VALUE_SHOP_OWNER == userInformation.getUserInfo().getStaffType()) {
                llChangeExchangeRate.setClickable(true);
            } else {
                llChangeExchangeRate.setClickable(false);
            }
        } else {
            llShopManagement.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.ll_change_password, R.id.shop_row, R.id.ll_logout, R.id.ll_change_language, R.id.ll_change_exchange_rate, R.id.rel_choose_printer_device})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ll_change_password:
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(getContext(), getActivityComponent(), userInformation.getUserInfo().getStaffCode());
                changePasswordDialog.show();
                if (compositeDisposable == null) {
                    compositeDisposable = new CompositeDisposable();
                }
                compositeDisposable.add(changePasswordDialog.getChangePasswordPublisher().subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (activity instanceof HomeActivity) {
                            if (s.equals(String.valueOf(Const.VALUE_UNAUTHORIZED_ERROR_CODE))) {
                                settingPresenter.getClientLogUseCase().clearClientLogLocal();
                                settingPresenter.getLogoutUseCase().clearUserInfor();
                                httpUnauthorizedError();
                            } else {
                                ((HomeActivity) activity).notifySuccess(s);
                            }
                        }
                    }
                }));
                break;

            case R.id.shop_row:
                if (activity instanceof HomeActivity) {
                    if (Const.VALUE_CHAIN_STORE_MANAGEMENT == userInformation.getUserInfo().getStaffType()) {
                        ((HomeActivity) activity).replaceFragment(ShopListView.newInstance(userInformation.getUserInfo().getStaffType(), userInformation.getShopInfor().getShopName()), R.id.container, true);
                    } else {
                        ((HomeActivity) activity).replaceFragment(ShopDetailView.newInstance(userInformation.getShopInfor(), userInformation.getUserInfo().getStaffType(), SettingView.class.getName()), R.id.container, true);
                    }
                }
                break;
            case R.id.ll_logout:
                if (AppUtils.isConnectivityAvailable(getContext())) {
                    settingPresenter.logout(userInformation.getUserInfo().getStaffCode());
                } else {
                    notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
                }
                break;
            case R.id.ll_change_language:
                changeLanguageDialog = new ChangeLanguageDialog(getContext(), R.style.DialogSlideAnim, sharePreferenceHelper.getLanguage());
                changeLanguageDialog.show();
                changeLanguageListener();
                break;
            case R.id.ll_change_exchange_rate:
                changeExchangeRateDialog = new ChangeExchangeRateDialog(getContext(), getActivityComponent(), userInformation.getUserInfo().getStaffCode(), exchangeRateValue);
                changeExchangeRateDialog.show();
                changeExchangeListener();
                break;
            case R.id.rel_choose_printer_device:
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(
                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, Const.REQUEST_ENABLE_BT);
                    // Otherwise, setup the session
                } else {
                    if (choosePrinterDeviceDialog == null) {
                        choosePrinterDeviceDialog = new ChoosePrinterDeviceDialog(getContext(), getActivityComponent());
                    }
                    choosePrinterDeviceDialog.show();
                    choosePrinterListener();

                }

                break;

        }
    }


    @Override
    protected void setupDialogTitle() {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).setToolbar(0, null);
            ((HomeActivity) activity).getToolbarTitle().setText(R.string.setting);
            ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._300sdp)));

        }
    }

    @Override
    public void logoutSuccess() {
        Intent loginIntent = new Intent(getContext(), LoginView.class);
        startActivity(loginIntent);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                    FirebaseInstanceId.getInstance().getToken();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getActivity().finish();


    }

    @Override
    public void logoutFailed(String messageError) {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).notifyError(messageError);
        }

    }

    @Override
    public void getPrinterNameSuccess(String printerName) {
        txtPrinterName.setText(printerName);
        switch (mService.getState()) {
            case BluetoothService.STATE_CONNECTED:
                txtConnectStatus.setText(getString(R.string.connected));
                break;
            case BluetoothService.STATE_CONNECTING:
                txtConnectStatus.setText(R.string.title_connecting);
                break;
            case BluetoothService.STATE_LISTEN:
            case BluetoothService.STATE_NONE:
                txtConnectStatus.setText(R.string.title_not_connected);
                break;
        }
    }

    @Override
    public void getConfigSuccess(ShopConfig shopConfig) {
        exchangeRateValue = shopConfig.getConfigValue();
        setExchangeRate();
    }

    private void setExchangeRate() {
        String khrMoney = DataUtils.formatterLong.format(Double.parseDouble(exchangeRateValue));
        txtExchangeRate.setText(String.format(getString(R.string.exchange), khrMoney));
    }


    @Override
    public void getConfigFailed(String messageError) {

    }

    @Override
    public void getConfigSuccess(AppConfigInfo appConfigInfo) {
        exchangeRateValue = appConfigInfo.getConfigValue();
        setExchangeRate();
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        initView();
        setShopManagement();
    }

    @Override
    public void onDestroy() {
        if ((settingPresenter != null)) {
            settingPresenter.detachView();
        }

        if (mService != null) {
            MyApplication.setmService(mService);
        }
        getActivity().unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void changeLanguageListener() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(changeLanguageDialog.getChangeLanguagePublish().subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                setLocale(s);
            }
        }));
    }

    private void changeExchangeListener() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(changeExchangeRateDialog.getChangeExchangeRatePublisher().subscribe(new Consumer<ChangeExchangeRateSuccess>() {
            @Override
            public void accept(ChangeExchangeRateSuccess changeExchangeRateSuccess) throws Exception {
                if (activity instanceof HomeActivity) {
                    if (TextUtils.isEmpty(changeExchangeRateSuccess.getExchangeRate())) {
                        settingPresenter.getClientLogUseCase().clearClientLogLocal();
                        settingPresenter.getLogoutUseCase().clearUserInfor();
                        httpUnauthorizedError();
                    } else {
                        ((HomeActivity) activity).notifySuccess(changeExchangeRateSuccess.getMessage());
                        exchangeRateValue = changeExchangeRateSuccess.getExchangeRate();
                        setExchangeRate();
                    }
                }
            }
        }));
    }

    private void choosePrinterListener() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(choosePrinterDeviceDialog.getChoosePrinterPublish().subscribe(new Consumer<BluetoothDevice>() {
            @Override
            public void accept(BluetoothDevice bluetoothDevice) throws Exception {
                settingPresenter.savePrinterAddress(bluetoothDevice);
                txtPrinterName.setText(bluetoothDevice.getName());
                // Get the BLuetoothDevice object
                if (BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress())) {
                    BluetoothDevice device = mBluetoothAdapter
                            .getRemoteDevice(bluetoothDevice.getAddress());
                    // Attempt to connect to the device
                    mService.connect(device);
                }
            }
        }));
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= 17) {
            conf.setLocale(myLocale);
        } else {
            conf.locale = myLocale;
        }
        res.updateConfiguration(conf, dm);
        sharePreferenceHelper.putLanguage(lang);
        Intent refresh = new Intent(getContext(), HomeActivity.class);
        refresh.putExtra(Const.KEY_FRAGMENT, Const.KEY_SETTING_FRAGMENT);
        refresh.putExtra(Const.KEY_USER_INFORMATION, userInformation);
        startActivity(refresh);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Const.REQUEST_ENABLE_BT: {
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a session
                    if (choosePrinterDeviceDialog == null) {
                        choosePrinterDeviceDialog = new ChoosePrinterDeviceDialog(getContext(), getActivityComponent());
                    }
                    choosePrinterDeviceDialog.show();
                    choosePrinterListener();
                } else {
                }
                break;
            }
        }
    }

    /****************************************************************************************************/
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (getContext() != null) {
                switch (msg.what) {
                    case BluetoothService.MESSAGE_STATE_CHANGE:
                        switch (msg.arg1) {
                            case BluetoothService.STATE_CONNECTED:
                                txtConnectStatus.setText(getString(R.string.connected));
                                break;
                            case BluetoothService.STATE_CONNECTING:
                                txtConnectStatus.setText(R.string.title_connecting);
                                break;
                            case BluetoothService.STATE_LISTEN:
                            case BluetoothService.STATE_NONE:
                                txtConnectStatus.setText(R.string.title_not_connected);
                                break;
                        }
                        break;
                    case BluetoothService.MESSAGE_WRITE:

                        break;
                    case BluetoothService.MESSAGE_READ:

                        break;
                    case BluetoothService.MESSAGE_DEVICE_NAME:
                        // save the connected device's name
                        txtConnectStatus.setText(R.string.title_connecting);
                        break;
                    case BluetoothService.MESSAGE_TOAST:
                        txtConnectStatus.setText(msg.getData().getString(BluetoothService.TOAST));
                        break;
                    case BluetoothService.MESSAGE_CONNECTION_LOST:    //蓝牙已断开连接
                        txtConnectStatus.setText(getString(R.string.lost_connection));
                        break;
                    case BluetoothService.MESSAGE_UNABLE_CONNECT:     //无法连接设备
                        txtConnectStatus.setText(getString(R.string.unalbe_connect));
                        break;

                }
            }
        }
    };
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        mService.stop();
                        txtConnectStatus.setText(R.string.title_not_connected);
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        mService.stop();
                        txtConnectStatus.setText(R.string.title_not_connected);
                        break;
                    case BluetoothAdapter.STATE_ON:
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    };
}
