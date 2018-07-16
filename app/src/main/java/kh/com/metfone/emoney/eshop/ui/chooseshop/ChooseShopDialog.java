package kh.com.metfone.emoney.eshop.ui.chooseshop;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.UnauthorizedErrorEvent;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.customs.TSnackbar;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class ChooseShopDialog extends BaseDialogActivity implements ChooseShopMvpView {
    protected MaterialDialog dialog;
    @BindView(R.id.rl_category)
    RelativeLayout rlCategory;
    @BindView(R.id.txt_dialog_title)
    TextView txtTitle;
    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;

    private TreeNode root;
    private TreeView treeView;
    public MaterialDialog progressDialog;
    @Inject
    ChooseShopPresenter chooseShopPresenter;
    private Context context;
    private ChainList chainList;
    private String staffCode;
    public ChooseShopDialog(Context context, ActivityComponent activityComponent, String staffCode) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_choose_category, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.context = context;
        this.staffCode = staffCode;
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        activityComponent.inject(this);
        progressDialog = AppUtils.createProgress(context, context.getResources().getString(R.string.app_name));
        chooseShopPresenter.attachView(this);
        txtTitle.setText(R.string.choose_shop);
    }

    @Override
    protected void setupDialogTitle() {

    }

    @OnClick(R.id.txt_close)
    public void onClickEvent(View view) {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();

        if (chainList == null ||
                chainList.getAreaShopList() == null ||
                chainList.getAreaShopList().size() == 0) {
            getAreaList();
        } else {
            buildTree();
        }
    }

    private void getAreaList() {
        if (AppUtils.isConnectivityAvailable(context)) {
            progressDialog.show();
            chooseShopPresenter.getChainList(staffCode);

        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    private void buildTree() {
        root = TreeNode.root();
        if (chainList.getAreaShopList() != null && chainList.getAreaShopList().size() > 0) {
            for (int i = 0; i < chainList.getAreaShopList().size(); i++) {
                TreeNode treeNode = new TreeNode(chainList.getAreaShopList().get(i));
                treeNode.setLevel(0);
                if (chainList.getAreaShopList().get(i).getShopInforList() != null
                        && chainList.getAreaShopList().get(i).getShopInforList().size() > 0) {
                    for (int j = 0; j < chainList.getAreaShopList().get(i).getShopInforList().size(); j++) {
                        TreeNode treeNode1 = new TreeNode(chainList.getAreaShopList().get(i).getShopInforList().get(j));
                        treeNode1.setLevel(1);
                        treeNode.addChild(treeNode1);
                    }
                }
                root.addChild(treeNode);
            }
            treeView = new TreeView(root, context, new ShopViewFactory());
            View treView = treeView.getView();
            treView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rlCategory.removeAllViews();
            rlCategory.addView(treView);
        }
    }

    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void getChainListSuccess(ChainList chainList) {
        this.chainList = chainList;
        progressDialog.dismiss();
        buildTree();
    }

    @Override
    public void getChainListFailed(String message) {
        progressDialog.dismiss();
        notifyError(message);
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
        EventBus.getDefault().post(new UnauthorizedErrorEvent());
    }
}
