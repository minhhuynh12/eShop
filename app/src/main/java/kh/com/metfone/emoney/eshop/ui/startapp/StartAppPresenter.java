
package kh.com.metfone.emoney.eshop.ui.startapp;

import android.text.TextUtils;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.CheckVersionUseCase;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetClientLogFailedUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetCommonConfigUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.domain.GetUserStatusUseCase;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseSaveLogPresenter;
import kh.com.metfone.emoney.eshop.utils.DataUtils;
import kh.com.metfone.emoney.eshop.utils.DateUtils;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;
import retrofit2.Retrofit;


/**
 * Created by DCV on 3/9/2018.
 */


public class StartAppPresenter extends BaseSaveLogPresenter<StartAppMvpView> {
    private final CheckVersionUseCase checkVersionUseCase;
    private final GetUserStatusUseCase getUserStatusUseCase;
    private final GetCommonConfigUseCase getCommonConfigUseCase;
    private final Retrofit retrofit;
    private final SharePreferenceHelper sharePreferenceHelper;
    private final GetUserInforUseCase getUserInforUseCase;
    private final GetClientLogFailedUseCase getClientLogFailedUseCase;

    @Inject
    StartAppPresenter(CheckVersionUseCase checkVersionUseCase,
                      GetUserStatusUseCase getUserStatusUseCase,
                      GetCommonConfigUseCase getCommonC,
                      ClientLogUseCase clientLogUseCase,
                      Retrofit retrofit,
                      SharePreferenceHelper sharePreferenceHelper,
                      GetUserInforUseCase getUserInforUseCase,
                      GetClientLogFailedUseCase getClientLogFailedUseCase) {
        super(clientLogUseCase);
        this.checkVersionUseCase = checkVersionUseCase;
        this.getUserStatusUseCase = getUserStatusUseCase;
        this.getCommonConfigUseCase = getCommonC;
        this.retrofit = retrofit;
        this.sharePreferenceHelper = sharePreferenceHelper;
        this.getUserInforUseCase = getUserInforUseCase;
        this.getClientLogFailedUseCase = getClientLogFailedUseCase;
    }

    @Override
    public void attachView(StartAppMvpView mvpView) {
        super.attachView(mvpView);
        if (TextUtils.isEmpty(sharePreferenceHelper.getLanguage())) {
            sharePreferenceHelper.putLanguage("km");
        }
        if (getMvpView() != null) {
            getMvpView().setLocale(sharePreferenceHelper.getLanguage());
        }
    }

    public void saveClientLogFailed(String version) {
        List<ClientLog> clientLogList = new ArrayList<>();
        getClientLogFailedUseCase.execute(new DisposableSubscriber<List<ClientLog>>() {
            @Override
            public void onNext(List<ClientLog> clientLogs) {
                clientLogList.addAll(clientLogs);
            }

            @Override
            public void onError(Throwable t) {
                onComplete();
            }

            @Override
            public void onComplete() {
                checkVersion(version);
                if (clientLogList != null && clientLogList.size() > 0) {
                    for (int i = 0; i < clientLogList.size(); i++) {
                        saveLog(clientLogList.get(i));
                    }
                }
            }
        }, null);
    }

    public void checkVersion(String version) {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
                "common/version",
                StartAppPresenter.class.getName(),
                "checkVersion");
        checkVersionUseCase.execute(new DisposableSubscriber<VersionInformation>() {
            @Override
            public void onNext(VersionInformation versionInformation) {
                long endTime = Calendar.getInstance().getTimeInMillis();
                clientLog.setEndTime(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS));
                clientLog.setDuration(endTime - startTime);
                if (getMvpView() != null) {
                    getMvpView().checkVersionAppSuccess(versionInformation);
//                    getCommonConfigInfo();
                }


                clientLog.setStatus(versionInformation.getStatus());
                String requestContent = new StringBuilder("platform:android|")
                        .append(Const.KEY_VERSION)
                        .append(":")
                        .append(version)
                        .toString();
                clientLog.setRequestContent(requestContent);
                String responseContent = new StringBuilder(String.valueOf(versionInformation.getStatus()))
                        .append("|")
                        .append(versionInformation.getCode())
                        .append("|")
                        .append(versionInformation.getMessage())
                        .toString();
                clientLog.setResponseContent(responseContent);
                clientLog.setErrorCode(versionInformation.getStatus());
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
                    getMvpView().checkVersionAppFailed("failed");
                }
                onComplete();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, CheckVersionUseCase.Params.forParams(version));

    }


    public void getCommonConfigInfo() {
        long startTime = Calendar.getInstance().getTimeInMillis();
        ClientLog clientLog = new ClientLog(DateUtils.formatDateTime(Calendar.getInstance().getTime(), DateUtils.PATTERN_DD_MM_YYYY_HH_MM_SS),
                Calendar.getInstance().getTimeInMillis(),
                sharePreferenceHelper.getAccessToken(),
                sharePreferenceHelper.getUserName(),
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
                onComplete();
//                Flowable<CommonConfigInfo> commonInfo = getCommonConfigUseCase.getCommonConfigInfo();
            }

            @Override
            public void onComplete() {
                saveLog(clientLog);
            }
        }, null);

    }


    public void checkLoginStatus() {
        getUserStatusUseCase.execute(new DisposableSubscriber<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                if (getMvpView() != null) {
                    getMvpView().goNextScreen(aBoolean);
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

    public void getUserInfor() {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if (getMvpView() != null) {
                    getMvpView().getUserInforSuccess(userInformation);
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

    @Override
    public void detachView() {
        checkVersionUseCase.dispose();
        getUserStatusUseCase.dispose();
        getCommonConfigUseCase.dispose();
        getUserInforUseCase.dispose();
        super.detachView();
    }
}

