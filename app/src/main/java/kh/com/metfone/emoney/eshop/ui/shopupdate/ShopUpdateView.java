package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.ChooseAreaEvent;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopUpdateView extends BaseDialogFragment implements ShopUpdateMvpView {
    @BindView(R.id.edt_shop_name)
    EditText edtShopName;
    @BindView(R.id.txt_owner_phone_number)
    TextView txtOwnerPhone;
    @BindView(R.id.edt_contact_phone)
    EditText edtContactPhone;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.txt_area)
    TextView txtArea;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    private ShopInfor shopInfor;
    private String areaCode;
    private int userType;
    private CompositeDisposable compositeDisposable;
    ChooseAreaDialog chooseAreaDialog;

    @Inject
    ShopUpdatePresenter shopUpdatePresenter;
    private String areaName;

    public static ShopUpdateView newInstance(int userType, ShopInfor shopInfor) {
        ShopUpdateView shopUpdateView = new ShopUpdateView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_SHOP_INFORMATION, shopInfor);
        bundle.putInt(Const.KEY_USER_TYPE, userType);
        shopUpdateView.setArguments(bundle);
        return shopUpdateView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            shopInfor = bundle.getParcelable(Const.KEY_SHOP_INFORMATION);
            areaCode = shopInfor.getAreaCode();
            userType = bundle.getInt(Const.KEY_USER_TYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shop_update, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        shopUpdatePresenter.attachView(this);
        initView();
        return view;
    }

    private void initView() {
        edtShopName.setText(shopInfor.getShopName());
        txtOwnerPhone.setText(shopInfor.getEmoneyAccountMsisdn());
        edtContactPhone.setText(shopInfor.getContactPhone());
        edtEmail.setText(shopInfor.getContactEmail());
        if(TextUtils.isEmpty(areaName)) {
            txtArea.setText(shopInfor.getAreaFullName());
        } else {
            txtArea.setText(areaName);
        }
        edtAddress.setText(shopInfor.getShopAddress());
    }

    @OnClick({R.id.txt_area})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_area:
                if (chooseAreaDialog == null) {
                    chooseAreaDialog = new ChooseAreaDialog(getContext(), getActivityComponent());
                }
                chooseAreaDialog.show();
                onChooseAreaListen();
                break;
        }
    }

    private void onChooseAreaListen() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(chooseAreaDialog.getChooseAreaPublish().subscribe(new Consumer<ChooseAreaEvent>() {
                @Override
                public void accept(ChooseAreaEvent chooseAreaEvent) throws Exception {
                    areaCode = chooseAreaEvent.getAreaCode();
                    areaName = chooseAreaEvent.getAreaName();
                    txtArea.setText(chooseAreaEvent.getAreaName());
                }
            }));
        }
    }

    @Override
    protected void setupDialogTitle() {

        if (Const.VALUE_SHOP_OWNER == userType) {
            ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, getString(R.string.save));
            ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkValidField();
                }
            });
        }
        ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((HomeActivity) activity).isHaveBackAction) {
                    activity.onBackPressed();
                }
            }
        });
    }

    private void checkValidField() {
        if (TextUtils.isEmpty(edtShopName.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_IV_201_SHOP_NAME));
            return;
        }
        if (TextUtils.isEmpty(edtContactPhone.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_IV_201_CONTACT_PHONE));
            return;
        }
        if (!DataUtils.isValidEmail(edtEmail.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_IV_201_CONTACT_EMAIL));
            return;
        }
        if (TextUtils.isEmpty(txtArea.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_IV_201_AREA_CODE));
            return;
        }
        if (TextUtils.isEmpty(edtAddress.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_IV_201_SHOP_ADDRESS));
            return;
        }
        if (AppUtils.isConnectivityAvailable(getContext())) {
            shopUpdatePresenter.updateShopInfo(edtShopName.getText().toString().trim(),
                    edtContactPhone.getText().toString().trim(),
                    edtEmail.getText().toString().trim(),
                    areaCode,
                    edtAddress.getText().toString().trim());

        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }


    }

    @OnTextChanged(R.id.edt_shop_name)
    public void onShopNameTextChange(CharSequence text) {
        if (TextUtils.isEmpty(edtShopName.getText().toString())) {
            edtShopName.setTypeface(null, Typeface.NORMAL);
        } else {
            edtShopName.setTypeface(null, Typeface.BOLD);
        }

    }

    @OnTextChanged(R.id.edt_contact_phone)
    public void onContactPhoneTextChange(CharSequence text) {
        if (TextUtils.isEmpty(edtContactPhone.getText().toString())) {
            edtContactPhone.setTypeface(null, Typeface.NORMAL);
        } else {
            edtContactPhone.setTypeface(null, Typeface.BOLD);
        }

    }

    @OnTextChanged(R.id.edt_email)
    public void onEmailTextChange(CharSequence text) {
        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
            edtEmail.setTypeface(null, Typeface.NORMAL);
        } else {
            edtEmail.setTypeface(null, Typeface.BOLD);
        }

    }

    @OnTextChanged(R.id.edt_address)
    public void onAddressTextChange(CharSequence text) {
        if (TextUtils.isEmpty(edtAddress.getText().toString())) {
            edtAddress.setTypeface(null, Typeface.NORMAL);
        } else {
            edtAddress.setTypeface(null, Typeface.BOLD);
        }

    }

    @OnTextChanged(R.id.txt_area)
    public void onAreaTextChange(CharSequence text) {
        if (TextUtils.isEmpty(txtArea.getText().toString())) {
            txtArea.setTypeface(null, Typeface.NORMAL);
        } else {
            txtArea.setTypeface(null, Typeface.BOLD);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void updateShopInfoSuccess(UpdateShopInfo updateShopInfo) {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).onBackPressed();
        }
    }

    @Override
    public void updateShopInfoFailed(String message) {
        notifyError(message);
    }
}
