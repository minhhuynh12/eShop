package kh.com.metfone.emoney.eshop.ui.staffdetail;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by DCV on 3/5/2018.
 */

public class DialogResetPassSuccess extends BaseDialogActivity {
    protected MaterialDialog dialog;
    @BindView(R.id.lin_close_popup)
    LinearLayout lin_close_popup;
    @BindView(R.id.tv_password)
    TextView tv_password;

    PublishSubject<String> changePasswordPublisher;

    public DialogResetPassSuccess(Context context, ActivityComponent activityComponent, String msg) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.popup_staff_reset_pass_success, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        tv_password.setText(msg);
        changePasswordPublisher = PublishSubject.create();
    }

    @Override
    protected void setupDialogTitle() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.lin_close_popup})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.lin_close_popup:
                dialog.dismiss();
                break;
        }
    }

    public void show() {
        dialog.show();
    }
}
