package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by LoanBT on 3/9/2018.
 */
@Singleton
public class GetCommonConfigInfoRepository implements GetCommonConfigInfoDataSource {
    private final GetCommonConfigInfoDataSource localGetConfigInfoDataSource;
    private final GetCommonConfigInfoDataSource remotegetConfigDataSource;

    @Inject
    GetCommonConfigInfoRepository(@Local GetCommonConfigInfoDataSource localCheckVersionDataSource,
                                  @Remote GetCommonConfigInfoDataSource remoteCheckVersionDataSource) {
        this.localGetConfigInfoDataSource = localCheckVersionDataSource;
        this.remotegetConfigDataSource = remoteCheckVersionDataSource;
    }

    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInformation() {
        return remotegetConfigDataSource.getCommonConfigInformation();
    }

    @Override
    public Flowable<CommonConfigInfo> getCommonConfigInfo() {
        return localGetConfigInfoDataSource.getCommonConfigInfo();
    }

    @Override
    public Flowable<BaseResult> changeExchangeRate(String khrMoney) {
        return remotegetConfigDataSource.changeExchangeRate(khrMoney);
    }

    @Override
    public void saveCommonConfigInfo(CommonConfigInfo commonConfigInfo) {
        localGetConfigInfoDataSource.saveCommonConfigInfo(commonConfigInfo);
    }

    @Override
    public void clearCommonConfigInfo() {
        localGetConfigInfoDataSource.clearCommonConfigInfo();

    }
}
