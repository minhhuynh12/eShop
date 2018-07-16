package kh.com.metfone.emoney.eshop.ui.base;


/**
 * Created by DCV on 3/1/2018.
 */
public interface BaseMvpView extends MvpView{

    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog(boolean value);

    void showAlertDialog(String errorMessage);

    void dismissDialog();

    void notifyError(String error);

    void notifyError(int errorResId);

    void notifySuccess(String message);

    void setProgressDialogCancelable(boolean value);

    void httpUnauthorizedError();
}
