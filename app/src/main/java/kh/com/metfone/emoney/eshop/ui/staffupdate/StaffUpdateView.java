package kh.com.metfone.emoney.eshop.ui.staffupdate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffUpdateView extends BaseDialogFragment implements StaffUpdateMvpView {
    public StaffInShop staffItem;
    @Inject
    StaffUpdatePresenter staffUpdatePresenter;
    @BindView(R.id.edt_shop_name)
    EditText edt_shop_name;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_phone_number)
    EditText edt_phone_number;
    @BindView(R.id.edt_status)
    EditText edt_status;
    @BindView(R.id.edt_create_date)
    EditText edt_create_date;


    public static StaffUpdateView newInstance(StaffInShop item) {
        StaffUpdateView staffListView = new StaffUpdateView();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Const.KEY_STAFF_ARG, item);
        staffListView.setArguments(bundle);
        return staffListView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            staffItem = bundle.getParcelable(Const.KEY_STAFF_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_staff_update, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        staffUpdatePresenter.attachView(this);
        edt_fullname.setText(staffItem.getStaffName());
        edt_username.setText(staffItem.getStaffCode());
        edt_shop_name.setText(staffItem.getShopName());
        edt_phone_number.setText(staffItem.getMsisdn());
        if ("1".equals(staffItem.getStatus())) {
            edt_status.setText(getString(R.string.active));
        } else if ("0".equals(staffItem.getStatus())) {
            edt_status.setText(getString(R.string.de_active));
        } else {
            edt_status.setText(getString(R.string.lock_status));
        }
        edt_create_date.setText(DateUtils.formateDateFromstring(DateUtils.PATTERN_YYYY_MM_dd, DateUtils.PATTERN_DD_MM_YYYY, staffItem.getCreateTime()));
        return view;
    }


    @Override
    protected void setupDialogTitle() {
        ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, getString(R.string.save));
        ((HomeActivity) activity).getToolbarTitle().setText(R.string.staffs_update);
        ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
        ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((HomeActivity) activity).isHaveBackAction) {
                    activity.onBackPressed();
                }
            }
        });

        ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });
    }


    public void checkValidate() {
        String username = edt_username.getText().toString();
        if (TextUtils.isEmpty(username)) {
            notifyError(getString(R.string.ERR_RQ_015_USERNAME));
            return;
        }
        if (username.matches(DataUtils.USERNAME_PATTERN) == false) {
            notifyError(getString(R.string.ERR_IV_115_USERNAME));
            return;
        }

        if (TextUtils.isEmpty(edt_fullname.getText().toString().trim())) {
            notifyError(getString(R.string.ERR_RQ_016_FULLNAME));
            return;
        }

        String phone = edt_phone_number.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            notifyError(getString(R.string.ERR_RQ_017_MSISDN));
            return;
        }
        if (AppUtils.isConnectivityAvailable(getContext())) {
            staffUpdatePresenter.updateStaff(staffItem.getStaffId(), edt_fullname.getText().toString().trim(), edt_phone_number.getText().toString());
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    public void onDestroy() {
        if (staffUpdatePresenter != null) {
            staffUpdatePresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void updateStaffSuccess(String fullname, String number_phone) {
        staffItem.setStaffName(fullname);
        staffItem.setMsisdn(number_phone);
        staffUpdatePresenter.saveStaff(staffItem);
        if (activity instanceof HomeActivity) {
            activity.onBackPressed();
        }
    }

    @Override
    public void updateStaffFailed(String errorMessage) {

    }
}
