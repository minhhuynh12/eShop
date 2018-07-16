package kh.com.metfone.emoney.eshop.ui.choosecategory;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.event.UnauthorizedErrorEvent;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ShopTypeInfo;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.customs.TSnackbar;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class ChooseCategoryDialog extends BaseDialogActivity implements ChooseCategoryMvpView {
    @Inject
    ChooseCategoryPresenter chooseCatePresenter;
    protected MaterialDialog dialog;
    public MaterialDialog progressDialog;
    @BindView(R.id.rl_category)
    RelativeLayout rlCategory;
    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;

    private TreeNode root;
    private TreeView treeView;
    CommonConfigInfo commonInfo;
    Context context;

    public ChooseCategoryDialog(Context context, ActivityComponent activityComponent) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_choose_category, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        View view = dialog.getCustomView();
        assert view != null;
        this.context = context;
        activityComponent.inject(this);
        chooseCatePresenter.attachView(this);
        progressDialog = AppUtils.createProgress(context, context.getResources().getString(R.string.app_name));
        ButterKnife.bind(this, view);
        root = TreeNode.root();
        commonInfo = DataUtils.getCommonConfigInfo();
        if (commonInfo != null) {
            buildTree(commonInfo.getShopeTypes());
        } else {
            if (AppUtils.isConnectivityAvailable(context)) {
                progressDialog.show();
                chooseCatePresenter.getCommonConfigInfo();
            } else {
                notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
            }
        }
    }

    private void buildTree(List<ShopTypeInfo> listShopType) {
        if (null != listShopType) {
            for (int i = 0; i < listShopType.size(); i++) {
                TreeNode treeNode = new TreeNode(listShopType.get(i));
                treeNode.setLevel(0);
                for (int j = 0; j < listShopType.get(i).getSubShopTypes().size(); j++) {
                    TreeNode treeNode1 = new TreeNode(listShopType.get(i).getSubShopTypes().get(j));
                    treeNode1.setLevel(1);
                    treeNode.addChild(treeNode1);
                }
                root.addChild(treeNode);
            }
            treeView = new TreeView(root, context, new CategoryViewFactory());
            View treView = treeView.getView();
            treView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rlCategory.removeAllViews();
            rlCategory.addView(treView);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupDialogTitle() {

    }

    @OnClick(R.id.txt_close)
    public void onClickEvent(View view) {
        dialog.dismiss();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
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
    public void getCommonInfoSuccess(List<ShopTypeInfo> listShopType) {
        progressDialog.dismiss();
        buildTree(listShopType);
    }

    @Override
    public void getCommonInfoFailed(String message) {
        progressDialog.dismiss();
    }
    @Override
    public void httpUnauthorizedError() {
        EventBus.getDefault().post(new UnauthorizedErrorEvent());
    }
}
