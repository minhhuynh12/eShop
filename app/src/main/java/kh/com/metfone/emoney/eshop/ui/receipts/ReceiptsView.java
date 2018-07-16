package kh.com.metfone.emoney.eshop.ui.receipts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.event.ChooseShopEvent;
import kh.com.metfone.emoney.eshop.data.event.UnauthorizedErrorEvent;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.chooseshop.ChooseShopDialog;
import kh.com.metfone.emoney.eshop.ui.customs.EndlessScrollListener;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by DCV on 3/2/2018.
 */

public class ReceiptsView extends BaseDialogFragment implements ReceiptsMvpView {
    @Inject
    ReceiptsPresenter receiptsPresenter;
    @BindView(R.id.rec_receipt)
    RecyclerView recReceipts;
    @BindView(R.id.txt_date_area)
    TextView txtDateAre;
    @BindView(R.id.txt_usd_total)
    TextView txt_usd_total;
    @BindView(R.id.txt_khr_total)
    TextView txt_khr_total;
    @BindView(R.id.txt_shopname)
    TextView txt_shopname;
    @BindView(R.id.ll_shop_infor)
    LinearLayout llShopInfo;
    @BindView(R.id.material_design_android_floating_action_menu)
    FloatingActionMenu floatingActionMenu;
    @BindView(R.id.img_icon_select)
    ImageView img_icon_select;
    Date fromDate;
    Date toDate;
    Calendar calendar;
    private CompositeDisposable compositeDisposable;
    private ChooseShopDialog chooseShopDialog;
    private ChooseDateDialog chooseDateDialog;
    private String shopId;
    @BindView(R.id.srl_reload)
    SwipeRefreshLayout swipeRefreshLayout;
    private UserInformation userInformation;
    private ReceiptsAdapter receiptsAdapter;
    @BindView(R.id.layout_parent)
    CoordinatorLayout layoutParent;


    public static ReceiptsView newInstance() {
        ReceiptsView receiptsView = new ReceiptsView();
        return receiptsView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopId = String.valueOf(DataUtils.getUserInformation().getUserInfo().getShopId());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_receipts, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        receiptsPresenter.attachView(this);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initView() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        fromDate = calendar.getTime();
        toDate = calendar.getTime();
        String dateArea = fromDate.getDate() + "/" + (fromDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900)
                + " - " + toDate.getDate() + "/" + (toDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900);
        txtDateAre.setText(dateArea);
        if (Const.VALUE_CHAIN_STORE_MANAGEMENT != DataUtils.getUserInformation().getUserInfo().getStaffType()) {
            img_icon_select.setVisibility(View.GONE);
        }
        if (Const.VALUE_CHAIN_STORE_MANAGEMENT == userInformation.getUserInfo().getStaffType()) {
            img_icon_select.setVisibility(View.VISIBLE);
            llShopInfo.setActivated(true);
            shopId = String.valueOf(userInformation.getChain().get(0).getShopId());
            txt_shopname.setText(userInformation.getChain().get(0).getShopName());
        } else {
            img_icon_select.setVisibility(View.GONE);
            llShopInfo.setActivated(false);
            txt_shopname.setText(userInformation.getShopInfor().getShopName());
            shopId = String.valueOf(userInformation.getShopInfor().getShopId());
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getReceipt(true);
            }
        });
        receiptsAdapter = new ReceiptsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recReceipts.setLayoutManager(layoutManager);
        recReceipts.setAdapter(receiptsAdapter);
        getReceipt(false);
        layoutParent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (floatingActionMenu.isOpened()) {
                    floatingActionMenu.close(true);
                    return true;
                }
                return false;
            }
        });

        recReceipts.addOnScrollListener(new EndlessScrollListener(layoutManager) {
            @Override
            public void onShowOrHideFloatMenu(boolean isShow) {
                if (receiptsAdapter.getItemCount() > 0) {
                    if (isShow) {
                        floatingActionMenu.showMenuButton(true);
                    } else {
                        floatingActionMenu.hideMenuButton(true);
                    }
                }
            }
        });
    }
   /* public void initData() {
        txt_shopname.setText(DataUtils.getUserInformation().getShopInfor().getShopName());
        shopId = String.valueOf(DataUtils.getUserInformation().getUserInfo().getShopId());
        if (Const.VALUE_CHAIN_STORE_MANAGEMENT == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
            llShopInfo.setClickable(true);
        } else {
            getReceipt();
            llShopInfo.setClickable(false);
        }
    }*/

    private void listenChangeDateAction() {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(chooseDateDialog.getOnChangeFromDate().subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Exception {
                fromDate = date;
            }
        }));
        compositeDisposable.add(chooseDateDialog.getOnChangeToDate().subscribe(new Consumer<Date>() {
            @Override
            public void accept(Date date) throws Exception {
                toDate = date;
                checkValidateDateField();
            }
        }));
    }

    public void setDateForFilterText() {
        String dateArea = fromDate.getDate() + "/" + (fromDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900)
                + " - " + toDate.getDate() + "/" + (toDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900);
        txtDateAre.setText(dateArea);
    }

    public void checkValidateDateField() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (fromDate == null) {
            notifyError(R.string.ERR_IV_012_FROM_DATE);
            return;
        }
        if (toDate == null) {
            notifyError(R.string.ERR_IV_112_FROM_DATE);
            return;
        }
        if (fromDate != null || fromDate != null) {
            Date today = calendar.getTime();
            int compare1 = sdf.format(fromDate).compareTo(sdf.format(today));
            int compare2 = sdf.format(toDate).compareTo(sdf.format(today));
            int compare3 = sdf.format(fromDate).compareTo(sdf.format(toDate));
            if (compare1 > 0) { //from > current
                notifyError(R.string.ERR_IV_112_FROM_DATE);
                return;
            }
            if (compare2 > 0) { //to > current
                notifyError(R.string.ERR_IV_113_TO_DATE);
                return;
            }
            if (compare3 > 0) {
                notifyError(R.string.ERR_IV_114_FROM_TO_DATE);
                return;
            }
            long diff = toDate.getTime() - fromDate.getTime();
            float dayCount = (float) diff / (24 * 60 * 60 * 1000);
            int number_days = (int) dayCount + 1;
            if (number_days > 60) {
                notifyError(R.string.ERR_IV_114_MAXIMUM_DATE);
                return;
            }
        }
        getReceipt(false);
    }

    @Subscribe
    public void unauthorizedErrorEvent(UnauthorizedErrorEvent unauthorizedErrorEvent) {
        receiptsPresenter.getClientLogUseCase().clearClientLogLocal();
        receiptsPresenter.getLogoutUseCase().clearUserInfor();
        super.httpUnauthorizedError();
    }

    @Subscribe
    public void chooseShopEvent(ChooseShopEvent chooseShopEvent) {
        chooseShopDialog.dismiss();
        txt_shopname.setText(chooseShopEvent.getShopInfor().getShopName());
        shopId = String.valueOf(chooseShopEvent.getShopInfor().getShopId());
        getReceipt(false);
    }

    @OnClick({R.id.ll_shop_infor, R.id.txt_date_area, R.id.flbt_date_option, R.id.flbt_last_30_days, R.id.flbt_this_week, R.id.flbt_today})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.ll_shop_infor:
                if (!floatingActionMenu.isOpened()) {
                    if (Const.VALUE_CHAIN_STORE_MANAGEMENT == DataUtils.getUserInformation().getUserInfo().getStaffType()) {
                        if (chooseShopDialog == null) {
                            chooseShopDialog = new ChooseShopDialog(getContext(), getActivityComponent(), DataUtils.getUserInformation().getUserInfo().getStaffCode());
                        }
                        chooseShopDialog.show();
                    }
                }
                break;
            case R.id.txt_date_area:
                if (!floatingActionMenu.isOpened()) {
                    chooseDateDialog = new ChooseDateDialog(getContext(), fromDate, toDate);
                    chooseDateDialog.show();
                    listenChangeDateAction();
                }
                break;

            case R.id.flbt_date_option:
                floatingActionMenu.close(true);
                chooseDateDialog = new ChooseDateDialog(getContext(), fromDate, toDate);
                chooseDateDialog.show();
                listenChangeDateAction();
                break;
            case R.id.flbt_today:
                floatingActionMenu.close(true);
                fromDate = calendar.getTime();
                toDate = calendar.getTime();
                getReceipt(false);
                break;
            case R.id.flbt_this_week:
                floatingActionMenu.close(true);
                Calendar calendar11 = Calendar.getInstance();
                calendar11.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                fromDate = calendar11.getTime();
                toDate = calendar.getTime();
                getReceipt(false);
                break;
            case R.id.flbt_last_30_days:
                floatingActionMenu.close(true);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.DATE, -29);
                fromDate = calendar2.getTime();
                toDate = calendar.getTime();
                getReceipt(false);
                break;
        }
    }


    @Override
    protected void setupDialogTitle() {
    }

    public void getReceipt(boolean isRefresh) {
        if (AppUtils.isConnectivityAvailable(getContext())) {
            receiptsPresenter.getReceipts(isRefresh, shopId, DateUtils.formatDateTime(fromDate, DateUtils.PATTERN_DD_MM_YYYY),
                    DateUtils.formatDateTime(toDate, DateUtils.PATTERN_DD_MM_YYYY));
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }

    }

    @Override
    public void getReceiptsListSuccess(ReceiptsResponse receipt) {
        if (null != receipt) {
            if (Const.VALUE_SUCCESS_CODE == receipt.getStatus()) {
                setDateForFilterText();
                String usdMoney = receipt.getTotalAmountUsd();
                if (TextUtils.isEmpty(usdMoney)) {
                    usdMoney = "0";
                }
//            usdMoney = "234345453.23";
                txt_usd_total.setText(DataUtils.getDoubleString(Double.parseDouble(usdMoney)));
                String khormeMoney = receipt.getTotalAmountKhr();
                if (TextUtils.isEmpty(khormeMoney)) {
                    khormeMoney = "0";
                }
                txt_khr_total.setText(DataUtils.formatterLong.format(Double.parseDouble(khormeMoney)));

                if (null != receipt.getReceipts()) {
                    receiptsAdapter.setList(receipt.getReceipts());
                } else {
                    receiptsAdapter.clearList();
                }
            } else {
                notifyError(receipt.getMessage());
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getReceiptsListFailed(String s) {
        notifyError(s);
    }

    @Override
    public void getConfigSuccess(CommonConfigInfo commonConfigInfo) {

    }

    @Override
    public void getConfigFailed(String messageError) {

    }

    @Override
    public void getChainListSuccess(ChainList chainList) {
        String shopname_0 = chainList.getAreaShopList().get(0).getShopInforList().get(0).getShopName();
        txt_shopname.setText(shopname_0);
        shopId = String.valueOf(chainList.getAreaShopList().get(0).getShopInforList().get(0).getShopId());
        getReceipt(false); // Basic
    }


    @Override
    public void getChainListFailed(String message) {
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        if (shopId.equals(String.valueOf(DataUtils.getUserInformation().getShopInfor().getShopId()))) {
            txt_shopname.setText(userInformation.getShopInfor().getShopName());
        }
        initView();
    }

    @Override
    public void onDestroy() {
        if ((receiptsPresenter != null)) {
            receiptsPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
