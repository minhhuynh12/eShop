package kh.com.metfone.emoney.eshop.ui.staffs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.event.UnauthorizedErrorEvent;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.staffcreate.StaffCreateNewView;
import kh.com.metfone.emoney.eshop.ui.staffdetail.StaffDetailView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffListView extends BaseDialogFragment implements StaffsMvpView {
    @Inject
    StaffsPresenter staffsPresenter;
    @BindView(R.id.list_app_preview)
    RecyclerView list_app_preview;
    @BindView(R.id.cv_cannot_use_this_feature)
    CardView cv_cannot_use_this_feature;

    private UserInformation userInformation;

    public static StaffListView newInstance() {
        StaffListView staffListView = new StaffListView();
        return staffListView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_staff_list, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        staffsPresenter.attachView(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inputData();
    }

    private void inputData() {
        if (Const.VALUE_SHOP_STAFF == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
            list_app_preview.setVisibility(View.GONE);
            cv_cannot_use_this_feature.setVisibility(View.VISIBLE);
        } else {
            if (AppUtils.isConnectivityAvailable(getContext())) {
                staffsPresenter.getStaffs();
            } else {
                notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
            }
            list_app_preview.setVisibility(View.VISIBLE);
            cv_cannot_use_this_feature.setVisibility(View.GONE);
        }
    }

    @Override
    protected void setupDialogTitle() {
        if (Const.VALUE_SHOP_OWNER == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
            ((HomeActivity) activity).setToolbar(0, getString(R.string.staff_toolbar_new));
        } else {
            ((HomeActivity) activity).setToolbar(0, null);
        }
        ((HomeActivity) activity).getToolbarTitle().setText(R.string.staffs);
        ((HomeActivity) activity).getToolbarAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) activity).getToolbarAction().setVisibility(View.GONE);
                ((HomeActivity) activity).replaceFragment(StaffCreateNewView.newInstance(), R.id.container, true);
                ((HomeActivity) activity).getToolbarTitle().setText(R.string.staff_new);
            }
        });
    }

    @Override
    public void getStaffsListSuccess(StaffsResponse receipt) {
        ShopAdapter adapter = new ShopAdapter(receipt.getListStaff());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        list_app_preview.setLayoutManager(layoutManager);
        list_app_preview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.getStaffInShopPublishSubject().subscribe(new Consumer<StaffInShop>() {
            @Override
            public void accept(StaffInShop staffInShop) throws Exception {
                ((HomeActivity) activity).replaceFragment(StaffDetailView.newInstance(staffInShop), R.id.container, true);
                ((HomeActivity) activity).getToolbarTitle().setText(R.string.staffs_detail);
                ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
            }
        });
    }

    @Override
    public void getStaffsListFailed(String errorMessage) {

    }

    @Override
    public void onDestroy() {
        if ((staffsPresenter != null)) {
            staffsPresenter.detachView();
        }
        super.onDestroy();
    }


    @Subscribe
    public void unauthorizedErrorEvent(UnauthorizedErrorEvent unauthorizedErrorEvent) {
        super.httpUnauthorizedError();
    }

}
