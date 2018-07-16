package kh.com.metfone.emoney.eshop.ui.chooseshop;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetChainListUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
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
 * Created by DCV on 3/2/2018.
 */

public class ChooseShopPresenter extends BaseSaveLogCheckAuthenPresenter<ChooseShopMvpView> {
    private final GetChainListUseCase getChainListUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    ChooseShopPresenter(GetChainListUseCase getChainListUseCase,
                        ClientLogUseCase clientLogUseCase,
                        LogoutUseCase logoutUseCase,
                        SharePreferenceHelper sharePreferenceHelper) {
        super(clientLogUseCase, logoutUseCase);
        this.getChainListUseCase = getChainListUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    protected void getChainList(String staffCode) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                staffCode,
                "shop/chain",
                ChooseShopPresenter.class.getName(),
                "getChainList");
        getChainListUseCase.execute(new DisposableSubscriber<ChainList>() {
            @Override
            public void onNext(ChainList chainList) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    if (Const.VALUE_SUCCESS_CODE == chainList.getStatus()) {
                        getMvpView().getChainListSuccess(chainList);
                    } else {
                        getMvpView().getChainListFailed(chainList.getMessage());
                    }
                }
                clientLog.setStatus(chainList.getStatus());
                String responseContent = new StringBuilder(String.valueOf(chainList.getStatus()))
                        .append("|")
                        .append(chainList.getCode())
                        .append("|")
                        .append(chainList.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(chainList.getStatus());


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
                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException|| t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().getChainListFailed(t.getMessage());
                        }
                    }
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, null);
    }

    @Override
    public void detachView() {
        if (getChainListUseCase != null) {
            getChainListUseCase.dispose();
        }
        super.detachView();
    }
}
