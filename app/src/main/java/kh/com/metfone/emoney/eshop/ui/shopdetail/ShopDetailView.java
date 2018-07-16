package kh.com.metfone.emoney.eshop.ui.shopdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.shoplist.ShopListView;
import kh.com.metfone.emoney.eshop.ui.shopupdate.ShopUpdateView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopDetailView extends BaseDialogFragment implements ShopDetailMvpView {
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.txt_owner_phone_number)
    TextView txtOwnerPhone;
    @BindView(R.id.txt_contact_phone)
    TextView txtContactPhone;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_area)
    TextView txtArea;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    private ShopInfor shopInfor;
    private int userType;
    private String fromScreen;
    @Inject
    ShopDetailPresenter shopDetailPresenter;

    public static ShopDetailView newInstance(ShopInfor shopInfor, int userType, String fromScreen) {
        ShopDetailView shopDetailView = new ShopDetailView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_SHOP_INFORMATION, shopInfor);
        bundle.putInt(Const.KEY_USER_TYPE, userType);
        bundle.putString(Const.KEY_VIEW_FROM_SCREEN, fromScreen);
        shopDetailView.setArguments(bundle);
        return shopDetailView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            shopInfor = bundle.getParcelable(Const.KEY_SHOP_INFORMATION);
            userType = bundle.getInt(Const.KEY_USER_TYPE);
            fromScreen = bundle.getString(Const.KEY_VIEW_FROM_SCREEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shop_detail, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        shopDetailPresenter.attachView(this);

        if (fromScreen.equals(ShopListView.class.getName())) {
            setData();
        } else {
            shopDetailPresenter.getUserInfo();
        }
        return view;
    }

    private void setData() {
        txtShopName.setText(shopInfor.getShopName());
        txtOwnerPhone.setText(TextUtils.isEmpty(shopInfor.getEmoneyAccountMsisdn()) ? "--" : shopInfor.getEmoneyAccountMsisdn());
        txtContactPhone.setText(TextUtils.isEmpty(shopInfor.getContactPhone()) ? "--" : shopInfor.getContactPhone());
        txtEmail.setText(TextUtils.isEmpty(shopInfor.getContactEmail()) ? "--" : shopInfor.getContactEmail());
        txtArea.setText(shopInfor.getAreaFullName());
        txtAddress.setText(shopInfor.getShopAddress());
    }

    @Override
    protected void setupDialogTitle() {
        if (userType == Const.VALUE_SHOP_OWNER) {
            ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, getString(R.string.shop_edit_btn));
            ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) activity).getToolbarAction().setVisibility(View.GONE);
                    ((HomeActivity) activity).replaceFragment(ShopUpdateView.newInstance(userType, shopInfor), R.id.container, true);
                    ((HomeActivity) activity).getToolbarTitle().setText(R.string.shop_update);
                    ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
                }
            });
        } else {
            ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, "");
        }
        ((HomeActivity) activity).getToolbarTitle().setText(R.string.shop_detail);
        ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
        ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((HomeActivity) activity).isHaveBackAction) {
                    activity.onBackPressed();
                }
            }
        });
    }


    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        shopInfor = userInformation.getShopInfor();
        setData();
    }

    @Override
    public void getUserInfoFailed(String message) {
        notifyError(message);
    }
}
