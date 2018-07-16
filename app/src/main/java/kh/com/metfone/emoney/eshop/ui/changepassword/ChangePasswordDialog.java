package kh.com.metfone.emoney.eshop.ui.changepassword;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.customs.TSnackbar;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by DCV on 3/5/2018.
 */

public class ChangePasswordDialog extends BaseDialogActivity implements ChangePasswordMvpView {
    private final Context context;
    private final String staffCode;
    protected MaterialDialog dialog;
    @BindView(R.id.edt_old_password)
    EditText edtOldPassword;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_confirm_new_password)
    EditText edtConfirmNewPassword;
    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;
    public MaterialDialog progressDialog;

    PublishSubject<String> changePasswordPublisher;

    @Inject
    ChangePasswordPresenter changePasswordPresenter;

    public ChangePasswordDialog(Context context, ActivityComponent activityComponent, String staffCode) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_change_password, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.context = context;
        this.staffCode = staffCode;
        progressDialog = AppUtils.createProgress(context, context.getResources().getString(R.string.app_name));
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        activityComponent.inject(this);
        changePasswordPresenter.attachView(this);
        changePasswordPublisher = PublishSubject.create();
    }

    public PublishSubject<String> getChangePasswordPublisher() {
        return changePasswordPublisher;
    }

    @Override
    protected void setupDialogTitle() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.txt_close, R.id.txt_change_password})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_close:
                dialog.dismiss();
                break;
            case R.id.txt_change_password:
                checkEmptyFields();
                break;
        }
    }

    private void checkEmptyFields() {
        if (edtOldPassword.getText().toString().isEmpty()) {
            notifyError(context.getString(R.string.ERR_RQ_201_OLD_PASSWORD));
            return;
        }
        if (edtNewPassword.getText().toString().isEmpty()) {
            notifyError(context.getString(R.string.ERR_RQ_201_NEW_PASSWORD));
            return;
        }
        if (edtConfirmNewPassword.getText().toString().isEmpty()) {
            notifyError(context.getString(R.string.ERR_RQ_201_CONFIRM_NEW_PASSWORD));
            return;
        }

        if (!edtConfirmNewPassword.getText().toString().equals(edtNewPassword.getText().toString())) {
            notifyError(context.getString(R.string.ERR_IV_201_PASSWORD_CONFIRM));
            return;
        }
        if (AppUtils.isConnectivityAvailable(context)) {
            progressDialog.show();
            changePasswordPresenter.changePassword(staffCode, edtOldPassword.getText().toString(), edtConfirmNewPassword.getText().toString());
        } else {
            notifyError(context.getResources().getString(R.string.MSG_CM_NO_NETWORK_FOUND));
        }
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void changePasswordSuccess(String message) {
        changePasswordPublisher.onNext(message);
        progressDialog.dismiss();
        dialog.dismiss();
    }

    @Override
    public void changePasswordFailed(String messageError) {
        progressDialog.dismiss();
        notifyError(messageError);

    }

    @Override
    public void notifyError(int errorResId) {
        progressDialog.dismiss();
        notifyError(context.getString(errorResId));
    }

    @Override
    public void notifyError(String error) {
        showErrorMessage(error);
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

    @Override
    public void httpUnauthorizedError() {
        changePasswordPublisher.onNext(String.valueOf(Const.VALUE_UNAUTHORIZED_ERROR_CODE));
    }
}
