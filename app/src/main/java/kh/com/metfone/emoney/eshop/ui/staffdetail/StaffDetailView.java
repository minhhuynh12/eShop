package kh.com.metfone.emoney.eshop.ui.staffdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.staffupdate.StaffUpdateView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffDetailView extends BaseDialogFragment implements StaffDetailMvpView {
    @Inject
    StaffDetailPresenter staffDetailPresenter;
    @BindView(R.id.lin_reset_pass)
    LinearLayout lin_reset_pass;
    @BindView(R.id.lin_update_info)
    LinearLayout lin_update_info;
//    @BindView(R.id.lin_report)
//    LinearLayout lin_report;

    @BindView(R.id.tv_staff_name)
    TextView tv_staff_name;
    @BindView(R.id.tv_shop_name)
    TextView tv_shop_name;
    @BindView(R.id.tv_staff_code)
    TextView tv_staff_code;
    @BindView(R.id.tv_phone_number)
    TextView tv_phone_number;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_create_date)
    TextView tv_create_date;
    @BindView(R.id.lin_action_for_owner)
    LinearLayout lin_action_for_owner;
    private StaffInShop staffInShop;

    public static StaffDetailView newInstance(StaffInShop staffInShop) {
        StaffDetailView staffListView = new StaffDetailView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_STAFF_ARG, staffInShop);
        staffListView.setArguments(bundle);
        return staffListView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            staffInShop = bundle.getParcelable(Const.KEY_STAFF_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_staff_detail, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        staffDetailPresenter.attachView(this);
        staffDetailPresenter.getStaffInfo(staffInShop.getStaffId());
//        if (Const.VALUE_SHOP_OWNER == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
        setStaffInfo();
        return view;
    }

    private void setStaffInfo() {
        if (Const.VALUE_SHOP_OWNER == Integer.valueOf(staffInShop.getStaffType())) {
            lin_action_for_owner.setVisibility(View.GONE);
        } else {
            if (Const.VALUE_SHOP_OWNER == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
                lin_action_for_owner.setVisibility(View.VISIBLE);
            } else {
                lin_action_for_owner.setVisibility(View.GONE);
            }
        }

        tv_staff_name.setText(staffInShop.getStaffName());
        tv_staff_code.setText(staffInShop.getStaffCode());
        tv_shop_name.setText(staffInShop.getShopName());
        tv_phone_number.setText(staffInShop.getMsisdn());
        if ("1".equals(staffInShop.getStatus())) {
            tv_status.setText(getString(R.string.active));
        } else if ("0".equals(staffInShop.getStatus())) {
            tv_status.setText(getString(R.string.de_active));
        } else {
            tv_status.setText(getString(R.string.lock_status));
        }
        tv_create_date.setText(DateUtils.formateDateFromstring(DateUtils.PATTERN_YYYY_MM_dd, DateUtils.PATTERN_DD_MM_YYYY, staffInShop.getCreateTime()));
    }

    @OnClick({R.id.lin_reset_pass, R.id.lin_update_info})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.lin_reset_pass:
                if (AppUtils.isConnectivityAvailable(getContext())) {
                    staffDetailPresenter.resetPasswordForStaff(Integer.valueOf(staffInShop.getStaffId()));
                } else {
                    notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
                }
                break;
            case R.id.lin_update_info:
                if (activity instanceof HomeActivity) {
                    ((HomeActivity) activity).replaceFragment(StaffUpdateView.newInstance(staffInShop), R.id.container, true);
                    ((HomeActivity) activity).getToolbarTitle().setText(R.string.staffs_update);
                    ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
                }
                break;
//            case R.id.lin_report:
//                break;
        }
    }

    @Override
    protected void setupDialogTitle() {
        ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, null);
        ((HomeActivity) activity).getToolbarTitle().setText(R.string.staffs_detail);
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
    public void onDestroy() {
        if (staffDetailPresenter != null) {
            staffDetailPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void resetPasswordForStaffSuccess(String password) {
        DialogResetPassSuccess changePasswordDialog = new DialogResetPassSuccess(getContext(), getActivityComponent(), password);
        changePasswordDialog.show();
    }

    @Override
    public void resetPasswordForStaffFailed(String errorMessage) {

    }

    @Override
    public void getStaffInfoSuccess(StaffInShop staffInShop) {
        this.staffInShop = staffInShop;
        setStaffInfo();
    }

    @Override
    public void httpUnauthorizedError() {
        super.httpUnauthorizedError();
    }
}
