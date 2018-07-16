package kh.com.metfone.emoney.eshop.ui.receipts;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetChainListUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.LogoutUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ReceiptsUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogCheckAuthenPresenter;
import kh.com.metfone.emoney.eshop.ui.confirmphone.ConfirmByPhonePresenter;
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

public class ReceiptsPresenter extends BaseSaveLogCheckAuthenPresenter<ReceiptsMvpView> {
    private final ReceiptsUseCase receiptsUseCase;
    private final GetChainListUseCase getChainListUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    ReceiptsPresenter(ReceiptsUseCase useCase, GetChainListUseCase getChainListUseCase, ClientLogUseCase clientLogUseCase,
                      SharePreferenceHelper sharePreferenceHelper,
                      GetUserInforUseCase getUserInforUseCase,
                      LogoutUseCase logoutUseCase) {
        super(clientLogUseCase, logoutUseCase);
        this.receiptsUseCase = useCase;
        this.getChainListUseCase = getChainListUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
    }

    @Override
    public void attachView(ReceiptsMvpView mvpView) {
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


    protected void getReceipts(boolean isRefresh, String shop_id, String from_Date, String to_Date) {
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
                "payment/receipts",
                ConfirmByPhonePresenter.class.getName(),
                "getReceipts");
        receiptsUseCase.execute(new DisposableSubscriber<ReceiptsResponse>() {
            @Override
            public void onNext(ReceiptsResponse receipt) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    if (!isRefresh) {
                        getMvpView().showProgressDialog(false);
                    }
                    if (null != receipt) {
                        getMvpView().getReceiptsListSuccess(receipt);
                    }
                }
                clientLog.setStatus(Integer.valueOf(receipt.getStatus()));
                String requestContent = new StringBuilder(Const.KEY_SHOP_ID)
                        .append(":")
                        .append(shop_id)
                        .append("|")
                        .append(Const.KEY_FROM_DATE)
                        .append(":")
                        .append(from_Date)
                        .append("|")
                        .append(Const.KEY_TO_DATE)
                        .append(":")
                        .append(to_Date)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(receipt.getStatus()))
                        .append("|")
                        .append(receipt.getCode())
                        .append("|")
                        .append(receipt.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(Integer.valueOf(receipt.getStatus()));
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
                    if (t instanceof HttpException && Const.VALUE_UNAUTHORIZED_ERROR_CODE == ((HttpException) t).code()) {
                        logoutUseCase.clearUserInfor();
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().getReceiptsListFailed(t.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, ReceiptsUseCase.Params.forParam(shop_id, from_Date, to_Date));

    }
}
