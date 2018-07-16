package kh.com.metfone.emoney.eshop.ui.staffs;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.StaffsUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.ui.register.RegisterPresenter;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by Administrator on 3/7/2018.
 */

public class StaffsPresenter extends BaseSaveLogCheckAuthenPresenter<StaffsMvpView> {
    StaffsUseCase staffsUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    StaffsPresenter(ClientLogUseCase clientLogUseCase, StaffsUseCase useCase, LogoutUseCase logoutUseCase,
                    SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.staffsUseCase = useCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    protected void getStaffs() {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
                "staff/all",
                RegisterPresenter.class.getName(),
                "getStaffs");
        staffsUseCase.execute(new DisposableSubscriber<StaffsResponse>() {
            @Override
            public void onNext(StaffsResponse staffs) {
                if (getMvpView() != null && null != staffs) {
                    getMvpView().showProgressDialog(false);
                    long endTime = Calendar.getInstance().getTimeInMillis();
                    clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                    clientLog.setDuration(endTime - startTime);
                    if (getMvpView() != null && null != staffs) {
                        getMvpView().getStaffsListSuccess(staffs);
                    }
                    clientLog.setStatus(Integer.valueOf(staffs.getStatus()));
                    String responseContent = new StringBuilder(String.valueOf(staffs.getStatus()))
                            .append("|")
                            .append(staffs.getCode())
                            .append("|")
                            .append(staffs.getMessage())
                            .toString();
                    clientLog.setResponseContent(responseContent);
                    clientLog.setErrorCode(Integer.valueOf(staffs.getStatus()));
                }
            }


            @Override
            public void onError(Throwable t) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        clientLogUseCase.clearClientLogLocal();
                        logoutUseCase.clearUserInfor();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().getStaffsListFailed(t.getMessage());
                        }

                    }
                }
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, StaffsUseCase.Params.forParam());
    }
}
