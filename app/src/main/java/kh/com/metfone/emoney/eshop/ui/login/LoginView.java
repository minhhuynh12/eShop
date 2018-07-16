package kh.com.metfone.emoney.eshop.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.firebase.app.Config;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.forgotpassword.ForgotPasswordView;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.register.RegisterView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by DCV on 3/1/2018.
 */

public class LoginView extends BaseDialogActivity implements LoginMvpView {

    @BindView(R.id.edt_account)
    EditText edtAccount;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @Inject
    LoginPresenter loginPresenter;
    private CompositeDisposable compositeDisposable;
    private ChangeLanguageDialog changeLanguageDialog;
    @Inject
    SharePreferenceHelper sharePreferenceHelper;

    @BindView(R.id.txt_language)
    TextView txtLanguage;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_sign_up)
    TextView txtSignUp;
    @BindView(R.id.txt_forgot_password)
    TextView txtForgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivityComponent().inject(this);
        AppUtils.setLanguage(this, sharePreferenceHelper.getLanguage());
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.attachView(this);
        initView();
    }

    private void initView() {
        if (sharePreferenceHelper.getLanguage().equals(getResources().getString(R.string.en_language))) {
            txtLanguage.setText(getResources().getString(R.string.english));
        } else {
            txtLanguage.setText(getResources().getString(R.string.cambodia));
        }
        edtAccount.setHint(getString(R.string.enter_your_username));
        edtPassword.setHint(getString(R.string.enter_your_password));
        btnLogin.setText(getString(R.string.login));
        txtSignUp.setText(getString(R.string.sign_up));
        txtForgotPassword.setText(getString(R.string.forgot_password));
        createProgressDialog();
    }

    @OnClick({R.id.txt_sign_up, R.id.btn_login, R.id.txt_forgot_password, R.id.rel_choose_language})
    public void onClickEvent(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txt_sign_up:
                intent = new Intent(this, RegisterView.class);
                startActivityForResult(intent, Const.KEY_SIGN_UP_REQUEST_CODE);
                break;
            case R.id.btn_login:
                checkEmptyFields();
                break;
            case R.id.txt_forgot_password:
                intent = new Intent(this, ForgotPasswordView.class);
                startActivityForResult(intent, Const.KEY_FORGOT_PASSWORD_REQUEST_CODE);
                break;
            case R.id.rel_choose_language:
                changeLanguageDialog = new ChangeLanguageDialog(this, R.style.DialogSlideAnim, sharePreferenceHelper.getLanguage());
                changeLanguageDialog.show();
                changeLanguageListener();
                break;
        }
    }

    private void checkEmptyFields() {
        if (edtAccount.getText().toString().trim().isEmpty() ||
                edtPassword.getText().toString().isEmpty()) {
            notifyError(R.string.ERR_RQ_007_LOGIN_INFORMATION);
            return;
        }
        if (AppUtils.isConnectivityAvailable(LoginView.this)) {
            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
            String fireBaseToken = pref.getString(Config.TOKEN_FIREBASE, "");
            loginPresenter.login(edtAccount.getText().toString().trim(), edtPassword.getText().toString(), fireBaseToken);
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {

    }

    @Override
    public void loginSuccess(UserInformation userInformation) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Const.KEY_USER_INFORMATION, userInformation);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailed(String errorMessage) {
        notifyError(errorMessage);
    }

    public void setLocale(String lang) {
        AppUtils.setLanguage(this, lang);
        sharePreferenceHelper.putLanguage(lang);
        initView();
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

    @Override
    protected void onDestroy() {
        if (loginPresenter != null) {
            loginPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Const.KEY_FORGOT_PASSWORD_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            String message = data.getExtras().getString(Const.KEY_MESSAGE);
            notifySuccess(message);
        }

        if (Const.KEY_SIGN_UP_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            String message = data.getExtras().getString(Const.KEY_MESSAGE);
            notifySuccess(message);
        }
    }
}
