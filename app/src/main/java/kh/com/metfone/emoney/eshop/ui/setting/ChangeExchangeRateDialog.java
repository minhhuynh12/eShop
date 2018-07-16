package kh.com.metfone.emoney.eshop.ui.setting;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.ChangeExchangeRateSuccess;
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
 * Created by DCV on 3/16/2018.
 */

public class ChangeExchangeRateDialog extends BaseDialogActivity implements ChangeExchangeRateMvpView {
    private final Context context;
    private MaterialDialog dialog;
    @BindView(R.id.edt_khr_money)
    EditText edtKhrMoney;

    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;
    private String staffCode;
    public MaterialDialog progressDialog;
    @Inject
    ChangeExchangeRatePresenter changeExchangeRatePresenter;

    PublishSubject<ChangeExchangeRateSuccess> changeExchangeRatePublisher;

    public ChangeExchangeRateDialog(Context context, ActivityComponent activityComponent, String staffCode, String exchangeRateValue) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_change_exchange_rate, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.staffCode = staffCode;
        this.context = context;
        progressDialog = AppUtils.createProgress(context, context.getResources().getString(R.string.app_name));
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        activityComponent.inject(this);
        changeExchangeRatePresenter.attachView(this);
        changeExchangeRatePublisher = PublishSubject.create();
        edtKhrMoney.setText(exchangeRateValue);
    }

    public PublishSubject<ChangeExchangeRateSuccess> getChangeExchangeRatePublisher() {
        return changeExchangeRatePublisher;
    }

    @OnClick({R.id.txt_close, R.id.txt_update})
    public void onClickEvent(View view) {
        if (view.getId() == R.id.txt_close) {
            dialog.dismiss();
        } else {
            validateKhrMoney();
        }
    }

    private void validateKhrMoney() {
        if (TextUtils.isEmpty(edtKhrMoney.getText().toString().trim())) {
            notifyError(context.getString(R.string.ERR_RQ_018_EXCH_RATE));
            return;
        }
        if (AppUtils.isConnectivityAvailable(context)) {
            progressDialog.show();
            changeExchangeRatePresenter.changeExchangeRate(staffCode, edtKhrMoney.getText().toString().trim());
        } else {
            notifyError(context.getResources().getString(R.string.MSG_CM_NO_NETWORK_FOUND));
        }
    }

    @Override
    protected void setupDialogTitle() {

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
    public void changeExchangeRateSuccess(ChangeExchangeRateSuccess changeExchangeRateSuccess) {
        changeExchangeRatePublisher.onNext(changeExchangeRateSuccess);
        progressDialog.dismiss();
        dialog.dismiss();
    }

    @Override
    public void changeExchangeRateFailed(String messageError) {
        progressDialog.dismiss();
        notifyError(messageError);
    }

    public void show() {
        dialog.show();
    }

    @Override
    public void httpUnauthorizedError() {
        changeExchangeRatePublisher.onNext(new ChangeExchangeRateSuccess("", ""));
    }
}
