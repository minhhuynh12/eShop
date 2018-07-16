package kh.com.metfone.emoney.eshop.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.login.LoginView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

/**
 *Created by DCV on 3/2/2018.
 */
public abstract class BaseDialogFragment extends BaseFragment implements BaseMvpView {
    public MaterialDialog progressDialog, alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createAlertDialog();
        createProgressDialog();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupDialogTitle();
    }

    @Override
    public void createProgressDialog() {
        progressDialog = AppUtils.createProgress(getActivity(), getString(R.string.app_name));
    }

    @Override
    public void createAlertDialog() {
        alertDialog = AppUtils.createAlertDialog(getActivity(), getString(R.string.app_name));
    }

    protected abstract void setupDialogTitle();

    @Override
    public void dismissDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void showProgressDialog(boolean value) {
        if (value) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        dismissDialog();
        super.onDestroyView();
    }


    @Override
    public void notifyError(String error) {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).notifyError(error);
        }
    }

    @Override
    public void notifyError(int errorResId) {
        notifyError(getString(errorResId));
    }

    @Override
    public void notifySuccess(String message) {
        if (activity instanceof HomeActivity) {
            ((HomeActivity) activity).notifySuccess(message);
        }
    }

    @Override
    public void setProgressDialogCancelable(boolean value) {
        progressDialog.setCancelable(value);
    }

    @Override
    public void httpUnauthorizedError() {
        Intent intent = new Intent(getContext(), LoginView.class);
        startActivity(intent);
        ActivityCompat.finishAffinity(getActivity());
    }
}
