package kh.com.metfone.emoney.eshop.ui.reports;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetAllReportUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by DCV on 3/9/2018.
 */

public class ReportsPresenter extends BaseSaveLogCheckAuthenPresenter<ReportsMvpView> {

    private final GetAllReportUseCase getAllReportUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    public ReportsPresenter(GetAllReportUseCase getAllReportUseCase,
                            LogoutUseCase logoutUseCase,
                            ClientLogUseCase clientLogUseCase,
                            SharePreferenceHelper sharePreferenceHelper,
                            GetUserInforUseCase getUserInforUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.getAllReportUseCase = getAllReportUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(ReportsMvpView mvpView) {
        super.attachView(mvpView);
        getUserInfo();
    }

    private void getUserInfo() {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if (getMvpView() != null) {
                    getMvpView().getUserInfoSuccess(userInformation);
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }, null);
    }


    public void getAllReport(boolean isRefresh, String shopId, String fromDate, String toDate) {
        if (getMvpView() != null) {
            if (!isRefresh) {
                getMvpView().showProgressDialog(true);
            }
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
                "report/all",
                ReportsView.class.getName(),
                "all");
        getAllReportUseCase.execute(new DisposableSubscriber<Report>() {
            @Override
            public void onNext(Report report) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    if (!isRefresh) {
                        getMvpView().showProgressDialog(false);
                    }
                    if (Const.VALUE_SUCCESS_CODE == report.getStatus()) {
                        getMvpView().getReportsSuccess(report);
                    } else {
                        getMvpView().getReportsFailed(report.getMessage());
                    }
                }
                clientLog.setStatus(report.getStatus());
                String requestContent = new StringBuilder(Const.KEY_SHOP_ID)
                        .append(":")
                        .append(shopId)
                        .append("|")
                        .append(Const.KEY_FROM_DATE)
                        .append(":")
                        .append(fromDate)
                        .append("|")
                        .append(Const.KEY_TO_DATE)
                        .append(":")
                        .append(toDate)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(report.getStatus()))
                        .append("|")
                        .append(report.getCode())
                        .append("|")
                        .append(report.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(report.getStatus());

            }

            @Override
            public void onError(Throwable t) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (t instanceof HttpException) {
                    clientLog.setErrorCode(((HttpException) t).code());
                }
                if (getMvpView() != null) {
                    if (!isRefresh) {
                        getMvpView().showProgressDialog(false);
                    }
                    if (t instanceof HttpException && ((HttpException) t).code() == 401) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();

                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().getReportsFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, GetAllReportUseCase.Params.forParams(shopId, fromDate, toDate));
    }
}
