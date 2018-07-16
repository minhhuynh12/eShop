package kh.com.metfone.emoney.eshop.ui.staffcreate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffCreateNewView extends BaseDialogFragment implements StaffCreateNewMvpView {
    @Inject
    StaffCreateNewPresenter staffNewPresenter;

    @BindView(R.id.edt_shop_name)
    TextView edt_shop_name;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.edt_fullname)
    EditText edt_fullname;
    @BindView(R.id.edt_phone_number)
    EditText edt_phone_number;

    public static StaffCreateNewView newInstance() {
        StaffCreateNewView staffListView = new StaffCreateNewView();
        return staffListView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_staff_newstaffl, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        staffNewPresenter.attachView(this);
        edt_shop_name.setText(DataUtils.getUserInformation().getShopInfor().getShopName());
        return view;
    }

    @Override
    public void onDestroy() {
        if (staffNewPresenter != null) {
            staffNewPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    protected void setupDialogTitle() {
        ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, getString(R.string.save));
        ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidate();
            }
        });
        ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((HomeActivity) activity).isHaveBackAction) {
                    activity.onBackPressed();
                }
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
            staffNewPresenter.newStaff(edt_username.getText().toString(), edt_fullname.getText().toString().trim(), edt_phone_number.getText().toString());
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    public void createStaffSuccess() {
        ((HomeActivity) activity).onBackPressed();
    }
}
