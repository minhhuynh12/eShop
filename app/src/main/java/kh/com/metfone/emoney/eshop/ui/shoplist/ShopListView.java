package kh.com.metfone.emoney.eshop.ui.shoplist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.models.AreaShop;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.ShopInfor;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.home.HomeActivity;
import kh.com.metfone.emoney.eshop.ui.shopdetail.ShopDetailView;
import kh.com.metfone.emoney.eshop.utils.AppUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopListView extends BaseDialogFragment implements ShopListMvpView {

    @BindView(R.id.rc_shop_list)
    RecyclerView rcShopList;
    @Inject
    ShopListPresenter shopListPresenter;
    private AreaShopAdapter areaShopAdapter;
    private int userType;
    private String chainName;
    private CompositeDisposable compositeDisposable;
    private List<AreaShop> areaShopList;

    public static ShopListView newInstance(int userType, String chainName) {
        ShopListView shopListView = new ShopListView();
        Bundle bundle = new Bundle();
        bundle.putInt(Const.KEY_USER_TYPE, userType);
        bundle.putString(Const.KEY_CHAIN_NAME, chainName);
        shopListView.setArguments(bundle);
        return shopListView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            userType = bundle.getInt(Const.KEY_USER_TYPE);
            chainName = bundle.getString(Const.KEY_CHAIN_NAME);
        }
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shop_list, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        shopListPresenter.attachView(this);
        initView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (areaShopList != null && areaShopList.size() > 0) {
            areaShopAdapter.setList(areaShopList);
        } else {
            getShopList();
        }

    }

    private void initView() {
        areaShopAdapter = new AreaShopAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcShopList.setLayoutManager(layoutManager);
        rcShopList.setNestedScrollingEnabled(false);
        rcShopList.setAdapter(areaShopAdapter);
        compositeDisposable.add(areaShopAdapter.getShopInforPublishSubject().subscribe(new Consumer<ShopInfor>() {
            @Override
            public void accept(ShopInfor shopInfor) throws Exception {
                if (activity instanceof HomeActivity) {
                    ((HomeActivity) activity).replaceFragment(ShopDetailView.newInstance(shopInfor, userType, ShopListView.class.getName()), R.id.container, true);
                    ((HomeActivity) activity).getToolbarTitle().setText(R.string.shop_detail);
                    ((HomeActivity) activity).getToolbarTitle().setMaxWidth((int) AppUtils.convertDpToPixel(getContext(), getResources().getDimension(R.dimen._100sdp)));
                }
            }
        }));
    }

    public void getShopList() {
        if (AppUtils.isConnectivityAvailable(getContext())) {
            shopListPresenter.getChainList();
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {
        ((HomeActivity) activity).setToolbar(R.drawable.ic_action_back, "");
        ((HomeActivity) activity).getToolbarTitle().setText(chainName);
        ((HomeActivity) activity).getBackAction().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((HomeActivity) activity).isHaveBackAction) {

                    ((HomeActivity) activity).onBackPressed();
                }
            }
        });
    }


    @Override
    public void getChainListSuccess(ChainList chainList) {
        if (chainList.getAreaShopList() != null && chainList.getAreaShopList().size() > 0) {
            areaShopList = chainList.getAreaShopList();
            areaShopAdapter.setList(areaShopList);
        }
    }

    @Override
    public void getChainListFailed(String message) {
        notifyError(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
