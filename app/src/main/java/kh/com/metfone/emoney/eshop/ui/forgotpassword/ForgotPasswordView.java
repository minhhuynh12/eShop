package kh.com.metfone.emoney.eshop.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by DCV on 3/5/2018.
 */

public class ForgotPasswordView extends BaseDialogActivity implements ForgotPasswordMvpView {

    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.edt_password)
    EditText edtPassword;

    @Inject
    ForgotPasswordPresenter forgotPasswordPresenter;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivityComponent().inject(this);
        AppUtils.setLanguage(this, sharePreferenceHelper.getLanguage());
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        forgotPasswordPresenter.attachView(this);
    }

    @OnClick({R.id.txt_close, R.id.btn_change_password, R.id.txt_get_code})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_close:
                finish();
                break;
            case R.id.btn_change_password:
                checkEmptyField();
                break;
            case R.id.txt_get_code:
                validatePhoneField();
                break;
        }
    }

    private void validatePhoneField() {
        if (edtAccount.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_001_EMONEY_ACCOUNT);
            return;
        } else {
            if (AppUtils.isConnectivityAvailable(ForgotPasswordView.this)) {
                String phoneNumber = DataUtils.getPhoneNumber(edtAccount.getText().toString().trim());
                forgotPasswordPresenter.getCodeForForgotPassword(phoneNumber);
            } else {
                notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
            }
        }

    }

    private void checkEmptyField() {
        if (edtAccount.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_001_EMONEY_ACCOUNT);
            return;
        }
        if (edtCode.getText().toString().trim().isEmpty()) {
            notifyError(R.string.ERR_RQ_002_PRIVATE_CODE);
            return;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            notifyError(R.string.ERR_RQ_008_NEW_PASSWORD);
            return;
        }
        if (AppUtils.isConnectivityAvailable(ForgotPasswordView.this)) {
            forgotPasswordPresenter.changePassword(edtAccount.getText().toString().trim(),
                    edtCode.getText().toString().trim(),
                    edtPassword.getText().toString());
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void changePasswordSuccess(String message) {
        Intent intent = getIntent();
        intent.putExtra(Const.KEY_MESSAGE, message);
        setResult(RESULT_OK, intent);
        finish();
    }
}
