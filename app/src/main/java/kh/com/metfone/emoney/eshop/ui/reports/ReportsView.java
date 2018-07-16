package kh.com.metfone.emoney.eshop.ui.reports;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.event.ChooseShopEvent;
import kh.com.metfone.emoney.eshop.data.event.UnauthorizedErrorEvent;
import kh.com.metfone.emoney.eshop.data.models.ReceiptByDate;
import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseDialogFragment;
import kh.com.metfone.emoney.eshop.ui.chooseshop.ChooseShopDialog;
import kh.com.metfone.emoney.eshop.ui.customs.EndlessScrollListener;
import kh.com.metfone.emoney.eshop.ui.customs.XYMarkerView;
import kh.com.metfone.emoney.eshop.ui.receipts.ChooseDateDialog;
import kh.com.metfone.emoney.eshop.utils.AppUtils;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ReportsView extends BaseDialogFragment implements ReportsMvpView, OnChartValueSelectedListener {
    @Inject
    ReportsPresenter reportsPresenter;
    Date fromDate;
    Date toDate;
    Calendar calendar;
    @BindView(R.id.rec_reports)
    RecyclerView recReport;
    @BindView(R.id.txt_date_area)
    TextView txtDateAre;

    @BindView(R.id.chart1)
    LinearLayout chartLayout;
    @BindView(R.id.flbt_today)
    FloatingActionButton flbtToday;
    @BindView(R.id.flbt_this_week)
    FloatingActionButton flbtThisWeek;
    @BindView(R.id.flbt_last_30_days)
    FloatingActionButton flbtLast30Day;
    @BindView(R.id.flbt_date_option)
    FloatingActionButton flbtDateOption;
    @BindView(R.id.material_design_android_floating_action_menu)
    FloatingActionMenu floatingActionMenu;
    @BindView(R.id.txt_usd_total)
    TextView txtUSDTotal;
    @BindView(R.id.txt_khr_total)
    TextView txtKHRTotal;
    @BindView(R.id.txt_shop_name)
    TextView txtShopName;
    @BindView(R.id.img_choose_shop)
    ImageView imgChooseShop;
    @BindView(R.id.srl_reload)
    SwipeRefreshLayout swipeRefreshLayout;
    private CompositeDisposable compositeDisposable;
    private ChooseDateDialog chooseDateDialog;
    private ChooseShopDialog chooseShopDialog;
    private ReportsAdapter reportsAdapter;
    private int shopId;
    private UserInformation userInformation;
    private List<ReceiptByDate> receiptByDateList;
    @BindView(R.id.layout_parent)
    CoordinatorLayout layoutParent;
    @BindView(R.id.txt_no_chart_data)
    TextView txtNoChartData;

    public static ReportsView newInstance() {
        ReportsView reportsView = new ReportsView();
        return reportsView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        ButterKnife.bind(this, view);
        getActivityComponent().inject(this);
        reportsPresenter.attachView(this);
        EventBus.getDefault().register(this);
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        fromDate = calendar.getTime();
        toDate = calendar.getTime();
        return view;
    }

    private void initView() {
        reportsAdapter = new ReportsAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recReport.setLayoutManager(linearLayoutManager);
        recReport.setAdapter(reportsAdapter);
        recReport.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onShowOrHideFloatMenu(boolean isShow) {
                if (reportsAdapter.getItemCount() > 0) {
                    if (isShow) {
                        floatingActionMenu.showMenuButton(true);
                    } else {
                        floatingActionMenu.hideMenuButton(true);
                    }
                }
            }
        });
        if (Const.VALUE_CHAIN_STORE_MANAGEMENT == userInformation.getUserInfo().getStaffType()) {
            imgChooseShop.setVisibility(View.VISIBLE);
            txtShopName.setActivated(true);
            txtShopName.setClickable(true);
            shopId = userInformation.getChain().get(0).getShopId();
            txtShopName.setText(userInformation.getChain().get(0).getShopName());

        } else {
            imgChooseShop.setVisibility(View.GONE);
            txtShopName.setActivated(false);
            txtShopName.setClickable(false);
            txtShopName.setText(userInformation.getShopInfor().getShopName());
            shopId = userInformation.getShopInfor().getShopId();
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllReport(true);
            }
        });
        getAllReport(false);
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
    }


    @OnClick({R.id.txt_date_area, R.id.txt_shop_name, R.id.img_choose_shop, R.id.flbt_today, R.id.flbt_this_week, R.id.flbt_last_30_days, R.id.flbt_date_option})
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.txt_shop_name:
                if (!txtShopName.isActivated()) {
                    break;
                }
            case R.id.img_choose_shop:
                if (chooseShopDialog == null) {
                    chooseShopDialog = new ChooseShopDialog(getContext(), getActivityComponent(), userInformation.getUserInfo().getStaffCode());
                }
                chooseShopDialog.show();
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
                getAllReport(false);
                break;
            case R.id.flbt_this_week:
                floatingActionMenu.close(true);
                Calendar calendar11 = Calendar.getInstance();
                calendar11.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                fromDate = calendar11.getTime();
                toDate = calendar.getTime();
                getAllReport(false);
                break;
            case R.id.flbt_last_30_days:
                floatingActionMenu.close(true);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.add(Calendar.DATE, -29);
                fromDate = calendar2.getTime();
                toDate = calendar.getTime();
                getAllReport(false);
                break;
        }
    }

    private void getAllReport(boolean isRefresh) {
        if (AppUtils.isConnectivityAvailable(getContext())) {
            reportsPresenter.getAllReport(isRefresh, String.valueOf(shopId),
                    DateUtils.formatDateTime(fromDate, DateUtils.PATTERN_DD_MM_YYYY),
                    DateUtils.formatDateTime(toDate, DateUtils.PATTERN_DD_MM_YYYY));
        } else {
            notifyError(R.string.MSG_CM_NO_NETWORK_FOUND);
        }
    }

    @Override
    protected void setupDialogTitle() {

    }

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
                getAllReport(false);
            }
        }));
    }

    @Override
    public void getReportsSuccess(Report report) {
        String dateArea = fromDate.getDate() + "/" + (fromDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900)
                + " - " + toDate.getDate() + "/" + (toDate.getMonth() + 1) + "/" + (toDate.getYear() + 1900);
        txtDateAre.setText(dateArea);
        txtUSDTotal.setText(DataUtils.getDoubleString(Double.parseDouble(report.getTotalAmountUsd())));
        txtKHRTotal.setText(DataUtils.formatterLong.format(Double.parseDouble(report.getTotalAmountKhr())));

        if (report.getReceiptsByDate() != null && report.getReceiptsByDate().size() > 0) {
            txtNoChartData.setVisibility(View.GONE);
            receiptByDateList = report.getReceiptsByDate();
            BarChart barChart = new BarChart(getContext());
            barChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            barChart.getAxisRight().setEnabled(false);
            barChart.getAxisLeft().setEnabled(false);
            barChart.setScaleEnabled(false);
            barChart.setPinchZoom(false);
            barChart.setDoubleTapToZoomEnabled(false);
            barChart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
            barChart.setDrawGridBackground(false);
            BarData data = new BarData(getXAxisValues(), getDataSet());
            barChart.setData(data);
            barChart.getData().setGroupSpace(100f);
            barChart.animateXY(2000, 2000);
            barChart.invalidate();
            barChart.setDescription("");
            barChart.setVisibleXRange(12);
            chartLayout.removeAllViews();
            XYMarkerView mv = new XYMarkerView(getContext());
            barChart.setMarkerView(mv);
            chartLayout.addView(barChart);
        } else {
            if (receiptByDateList == null) {
                receiptByDateList = new ArrayList<>();
            } else {
                receiptByDateList.clear();
            }
            chartLayout.removeAllViews();
            txtNoChartData.setVisibility(View.VISIBLE);
        }

        if (report.getReceiptsByStaff() != null && report.getReceiptsByStaff().size() > 0) {
            reportsAdapter.setList(report.getReceiptsByStaff());
        } else {
            reportsAdapter.clearList();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getReportsFailed(String errorMessage) {
        swipeRefreshLayout.setRefreshing(false);
        notifyError(errorMessage);
    }

    @Override
    public void getUserInfoSuccess(UserInformation userInformation) {
        this.userInformation = userInformation;
        if (shopId == userInformation.getShopInfor().getShopId()) {
            txtShopName.setText(userInformation.getShopInfor().getShopName());
        }
        initView();
    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    }

    @Override
    public void onNothingSelected() {

    }

    @Subscribe
    public void chooseShopEvent(ChooseShopEvent chooseShopEvent) {
        chooseShopDialog.dismiss();
        txtShopName.setText(chooseShopEvent.getShopInfor().getShopName());
        shopId = chooseShopEvent.getShopInfor().getShopId();
        getAllReport(false);
    }

    @Subscribe
    public void unauthorizedErrorEvent(UnauthorizedErrorEvent unauthorizedErrorEvent) {
        reportsPresenter.getClientLogUseCase().clearClientLogLocal();
        reportsPresenter.getLogoutUseCase().clearUserInfor();
        super.httpUnauthorizedError();
    }

    @Override
    public void onDestroy() {
        if ((reportsPresenter != null)) {
            reportsPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        for (int i = 0; i < receiptByDateList.size(); i++) {
            BarEntry v1e1 = new BarEntry(Float.parseFloat(receiptByDateList.get(i).getTotalAmountKhr()), i);
            valueSet1.add(v1e1);
            BarEntry v2e1 = new BarEntry(Float.parseFloat(receiptByDateList.get(i).getTotalAmountUsd()), i); // Jan
            valueSet2.add(v2e1);
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "KHR");
        barDataSet1.setColor(Color.GREEN);
        barDataSet1.setDrawValues(false);

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "USD");
        barDataSet2.setColor(Color.BLUE);
        barDataSet2.setDrawValues(false);
        if (valueSet1.size() <= 8) { // barEntries is my Entry Array
            int factor = 10; // increase this to decrease the bar width. Decrease to increase he bar width
            int percent = (factor - valueSet1.size()) * 10;
            barDataSet1.setBarSpacePercent(percent);
        }

        if (valueSet2.size() <= 8) { // barEntries is my Entry Array
            int factor = 10; // increase this to decrease the bar width. Decrease to increase he bar width
            int percent = (factor - valueSet2.size()) * 10;
            barDataSet2.setBarSpacePercent(percent);
        }
        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < receiptByDateList.size(); i++) {
            xAxis.add(receiptByDateList.get(i).getCollectDate());
        }
        return xAxis;
    }
}
