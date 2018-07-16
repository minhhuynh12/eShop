package kh.com.metfone.emoney.eshop.ui.staffdetail;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetStaffInfoUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.StaffResetPasswordUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;
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

public class StaffDetailPresenter extends BaseSaveLogCheckAuthenPresenter<StaffDetailMvpView> {
    private final GetStaffInfoUseCase getStaffInfoUseCase;
    StaffResetPasswordUseCase staffsResetUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    StaffDetailPresenter(StaffResetPasswordUseCase usecase,
                         ClientLogUseCase clientLogUseCase, LogoutUseCase logoutUseCase,
                         GetStaffInfoUseCase getStaffInfoUseCase,
                         SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.staffsResetUseCase = usecase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getStaffInfoUseCase = getStaffInfoUseCase;

    }

    protected void getStaffInfo(String staffId) {
        getStaffInfoUseCase.execute(new DisposableSubscriber<StaffInShop>() {
            @Override
            public void onNext(StaffInShop staffInShop) {
                if (getMvpView() != null) {
                    getMvpView().getStaffInfoSuccess(staffInShop);
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        }, GetStaffInfoUseCase.Params.forParam(staffId));
    }

    public void resetPasswordForStaff(int staffId) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                "",
                "/staff/reset-password",
                RegisterPresenter.class.getName(),
                "resetPasswordForStaff");
        staffsResetUseCase.execute(new DisposableSubscriber<StaffResetPassResponse>() {
            @Override
            public void onNext(StaffResetPassResponse baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);


                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (baseResult.getStatus() != Const.VALUE_SUCCESS_CODE) {
                        getMvpView().notifyError(baseResult.getMessage());
                    } else {
                        getMvpView().resetPasswordForStaffSuccess(baseResult.getPassword());
                    }
                }


                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_STAFF_ID)
                        .append(":")
                        .append(staffId)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(baseResult.getStatus()))
                        .append("|")
                        .append(baseResult.getCode())
                        .append("|")
                        .append(baseResult.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(baseResult.getStatus());
            }

            @Override
            public void onError(Throwable t) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                    clientLog.setErrorCode(((HttpException) t).code());
                    logoutUseCase.clearUserInfor();
                    clientLogUseCase.clearClientLogLocal();
                    getMvpView().httpUnauthorizedError();
                }
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                        getMvpView().notifyError(R.string.connect_timeout);
                    } else {
                        getMvpView().notifyError(t.getMessage());
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, StaffResetPasswordUseCase.Param.forParam(staffId));
    }
}
