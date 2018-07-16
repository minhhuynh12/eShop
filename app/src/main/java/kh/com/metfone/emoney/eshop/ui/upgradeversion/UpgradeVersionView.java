package kh.com.metfone.emoney.eshop.ui.upgradeversion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by DCV on 3/9/2018.
 */

public class UpgradeVersionView extends BaseDialogActivity {

    private final MaterialDialog dialog;
    private final Context context;
    @BindView(R.id.txt_content_upgrade)
    TextView txtContentUpgrade;
    private PublishSubject<VersionInformation> onQuitUpgrade;
    private VersionInformation versionInformation;

    public UpgradeVersionView(Context context, VersionInformation versionInformation, String language) {
        AppUtils.setLanguage(context, language);
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_upgrade_version, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.context = context;
        this.versionInformation = versionInformation;
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        txtContentUpgrade.setText(String.format(context.getResources().getString(R.string.content_upgrade), versionInformation.getVersionInforChild().getNewVersion()));
        onQuitUpgrade = PublishSubject.create();
    }

    @Override
    protected void setupDialogTitle() {

    }

    public void show() {
        dialog.show();
    }

    @OnClick({R.id.txt_close, R.id.txt_upgrade})
    public void onClickEvent(View view) {
        if (view.getId() == R.id.txt_close) {
            onQuitUpgrade.onNext(versionInformation);
            dialog.dismiss();
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.viettel.vtt.vn.shopemoney")));
        }
    }

    public PublishSubject<VersionInformation> getOnQuitUpgrade() {
        return onQuitUpgrade;
    }
}
