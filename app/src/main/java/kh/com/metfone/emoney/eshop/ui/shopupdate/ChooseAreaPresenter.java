package kh.com.metfone.emoney.eshop.ui.shopupdate;

import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetAreaGroupLocalUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetAreaGroupRemoteUseCase;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogPresenter;
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

public class ChooseAreaPresenter extends BaseSaveLogPresenter<ChooseAreaMvpView> {
    private final GetAreaGroupRemoteUseCase getAreaGroupRemoteUseCase;
    private final GetAreaGroupLocalUseCase getAreaGroupLocalUseCase;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    ChooseAreaPresenter(GetAreaGroupRemoteUseCase getAreaGroupRemoteUseCase,
                        GetAreaGroupLocalUseCase getAreaGroupLocalUseCase,
                        SharePreferenceHelper sharePreferenceHelper,
                        ClientLogUseCase clientLogUseCase) {
        super(clientLogUseCase);
        this.getAreaGroupRemoteUseCase = getAreaGroupRemoteUseCase;
        this.getAreaGroupLocalUseCase = getAreaGroupLocalUseCase;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public void attachView(ChooseAreaMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getAreaGroup() {
        getAreaGroupLocalUseCase.execute(new DisposableSubscriber<AreaGroup>() {
            @Override
            public void onNext(AreaGroup areaGroup) {
                if (getMvpView() != null) {
                    getMvpView().getAreaGroupSuccess(areaGroup);
                }
            }

            @Override
            public void onError(Throwable t) {
                getAreaGroupRemote();
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }

    public void getAreaGroupRemote() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
                "common/areas",
                ChooseAreaPresenter.class.getName(),
                "getAreaGroupRemote");
        getAreaGroupRemoteUseCase.execute(new DisposableSubscriber<AreaGroup>() {
            @Override
            public void onNext(AreaGroup areaGroup) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);

                if (getMvpView() != null) {
                    if (Const.VALUE_SUCCESS_CODE == areaGroup.getStatus()) {
                        getAreaGroupRemoteUseCase.saveAreaGroup(areaGroup);
                        getMvpView().getAreaGroupSuccess(areaGroup);
                    } else {
                        getMvpView().getAreaGroupFailed(areaGroup.getMessage());
                    }
                }
                String responseContent = new StringBuilder(String.valueOf(areaGroup.getStatus()))
                        .append("|")
                        .append(areaGroup.getCode())
                        .append("|")
                        .append(areaGroup.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(areaGroup.getStatus());
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
                    if (t instanceof SocketTimeoutException || t instanceof SSLException) {
                        getMvpView().notifyError(R.string.connect_timeout);
                    } else {
                        getMvpView().getAreaGroupFailed(t.getMessage());
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
