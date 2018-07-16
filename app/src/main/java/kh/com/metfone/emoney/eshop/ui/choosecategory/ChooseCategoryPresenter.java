package kh.com.metfone.emoney.eshop.ui.choosecategory;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetCommonConfigUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogPresenter;
import kh.com.metfone.emoney.eshop.ui.startapp.StartAppPresenter;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;

import java.net.SocketTimeoutException;
import java.util.Calendar;

import javax.inject.Inject;
import javax.net.ssl.SSLException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * Created by DCV on 3/2/2018.
 */

public class ChooseCategoryPresenter extends BaseSaveLogPresenter<ChooseCategoryMvpView> {
    private final GetCommonConfigUseCase getCommonConfigUseCase;

    @Inject
    ChooseCategoryPresenter(GetCommonConfigUseCase getCommonC, ClientLogUseCase clientLogUseCase) {
        super(clientLogUseCase);
        this.getCommonConfigUseCase = getCommonC;
    }

    public void getCommonConfigInfo() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                "",
                "",
                "common/app-config",
                StartAppPresenter.class.getName(),
                "getCommonConfigInfo");
        getCommonConfigUseCase.execute(new DisposableSubscriber<CommonConfigInfo>() {
            @Override
            public void onNext(CommonConfigInfo commonInfo) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                //Get CommonConfigInfo for using in other screen   beginTransaction realm
                getCommonConfigUseCase.saveCommonConfigInfo(commonInfo);
                DataUtils.setCommonConfigInfo(commonInfo);
                getMvpView().getCommonInfoSuccess(commonInfo.getShopeTypes());
                clientLog.setStatus(commonInfo.getStatus());
                String responseContent = new StringBuilder(String.valueOf(commonInfo.getStatus()))
                        .append("|")
                        .append(commonInfo.getCode())
                        .append("|")
                        .append(commonInfo.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(commonInfo.getStatus());
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
                        clientLogUseCase.clearClientLogLocal();
                        getMvpView().httpUnauthorizedError();
                    } else {
                        if (t instanceof SocketTimeoutException|| t instanceof SSLException) {
                            getMvpView().notifyError(R.string.connect_timeout);
                        } else {
                            getMvpView().getCommonInfoFailed(t.getMessage());
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
}
