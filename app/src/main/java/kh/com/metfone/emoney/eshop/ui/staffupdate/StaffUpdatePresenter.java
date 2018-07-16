package kh.com.metfone.emoney.eshop.ui.staffupdate;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.StaffUpdateUseCase;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.StaffInShop;
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

public class StaffUpdatePresenter extends BaseSaveLogCheckAuthenPresenter<StaffUpdateMvpView> {
    private final StaffUpdateUseCase staffUpdateUseCase;

    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    StaffUpdatePresenter(ClientLogUseCase clientLogUseCase, StaffUpdateUseCase usecase, LogoutUseCase logoutUseCase,
                         GetUserInforUseCase getUserInforUseCase,
                         SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.staffUpdateUseCase = usecase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    public void updateStaff(String staffId, String staffName, String msisdn) {
        if (getMvpView() != null) {
            getMvpView().showProgressDialog(true);
        }
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                "",
                "/staff/update",
                RegisterPresenter.class.getName(),
                "updateStaff");
        staffUpdateUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (baseResult.getStatus() != Const.VALUE_SUCCESS_CODE) {
                        getMvpView().notifyError(baseResult.getMessage());
                    } else {
                        getMvpView().notifySuccess(baseResult.getMessage());
                        getMvpView().updateStaffSuccess(staffName, msisdn);
                    }
                }
                clientLog.setStatus(baseResult.getStatus());
                String requestContent = new StringBuilder(Const.KEY_MSISDN)
                        .append(":")
                        .append(staffId)
                        .append(staffName)
                        .append(msisdn)
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
                if (getMvpView() != null) {
                    getMvpView().showProgressDialog(false);
                    if (t instanceof HttpException && ((HttpException) t).code() == 401) {
                        clientLogUseCase.clearClientLogLocal();
                        logoutUseCase.clearUserInfor();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().notifyError(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, StaffUpdateUseCase.Param.forParam(staffId, staffName, msisdn));
    }

    protected void saveStaff(StaffInShop staffInShop) {
        getUserInforUseCase.saveStaff(staffInShop);
    }
}
