package kh.com.metfone.emoney.eshop.ui.shopupdate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import kh.com.metfone.emoney.eshop.ChooseAreaEvent;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.injection.component.ActivityComponent;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogActivity;
import kh.com.metfone.emoney.eshop.ui.customs.TSnackbar;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.subjects.PublishSubject;
import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;

public class ChooseAreaDialog extends BaseDialogActivity implements ChooseAreaMvpView {
    protected MaterialDialog dialog;
    @BindView(R.id.rl_category)
    RelativeLayout rlArea;

    private TreeNode root;
    private TreeView treeView;
    @Inject
    ChooseAreaPresenter chooseAreaPresenter;
    private Context context;
    @BindView(R.id.layout_parent)
    RelativeLayout layoutParent;
    private PublishSubject<ChooseAreaEvent> chooseAreaPublish;
    public MaterialDialog progressDialog;
    private AreaGroup areaGroup;

    public ChooseAreaDialog(Context context, ActivityComponent activityComponent) {
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.dialog_choose_area, false)
                .autoDismiss(false)
                .canceledOnTouchOutside(false)
                .show();
        this.context = context;
        View view = dialog.getCustomView();
        assert view != null;
        ButterKnife.bind(this, view);
        activityComponent.inject(this);
        chooseAreaPresenter.attachView(this);
        chooseAreaPublish = PublishSubject.create();
        EventBus.getDefault().register(this);
        progressDialog = AppUtils.createProgress(context, context.getResources().getString(R.string.app_name));
    }

    private void getAreaList() {
        if (AppUtils.isConnectivityAvailable(context)) {
            progressDialog.show();
            chooseAreaPresenter.getAreaGroupRemote();
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    public PublishSubject<ChooseAreaEvent> getChooseAreaPublish() {
        return chooseAreaPublish;
    }

    private void buildTree(AreaGroup areaGroup) {
        root = TreeNode.root();
        if (areaGroup.getAreaLevel1List() != null && areaGroup.getAreaLevel1List().size() > 0) {
            for (int i = 0; i < areaGroup.getAreaLevel1List().size(); i++) {
                TreeNode treeNode = new TreeNode(areaGroup.getAreaLevel1List().get(i));
                treeNode.setLevel(0);
                if (areaGroup.getAreaLevel1List().get(i).getAreaLevel2List() != null
                        && areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().size() > 0) {
                    for (int j = 0; j < areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().size(); j++) {
                        TreeNode treeNode1 = new TreeNode(areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().get(j));
                        treeNode1.setLevel(1);
                        treeNode.addChild(treeNode1);
                        if (areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().get(j).getAreaLevel3List() != null
                                && areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().get(j).getAreaLevel3List().size() > 0) {
                            for (int z = 0; z < areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().get(j).getAreaLevel3List().size(); z++) {
                                TreeNode treeNode2 = new TreeNode(areaGroup.getAreaLevel1List().get(i).getAreaLevel2List().get(j).getAreaLevel3List().get(z));
                                treeNode2.setLevel(2);
                                treeNode1.addChild(treeNode2);
                            }
                        }
                    }
                }
                root.addChild(treeNode);
            }
            treeView = new TreeView(root, context, new AreaViewFactory());
            View treView = treeView.getView();
            treView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rlArea.removeAllViews();
            rlArea.addView(treView);

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

    public void show() {
        dialog.show();

        if (areaGroup == null ||
                areaGroup.getAreaLevel1List() == null ||
                areaGroup.getAreaLevel1List().size() == 0) {
            getAreaList();
        } else {
            buildTree(areaGroup);
        }
    }

    @Override
    public void getAreaGroupSuccess(AreaGroup areaGroup) {
        this.areaGroup = areaGroup;
        buildTree(areaGroup);
        progressDialog.dismiss();
    }

    @Override
    public void getAreaGroupFailed(String message) {
        notifyError(message);
        progressDialog.dismiss();
    }

    @Override
    public void notifyError(int errorResId) {
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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void chooseAreaEvent(ChooseAreaEvent chooseAreaEvent) {
        chooseAreaPublish.onNext(chooseAreaEvent);
        dialog.dismiss();
    }

}
