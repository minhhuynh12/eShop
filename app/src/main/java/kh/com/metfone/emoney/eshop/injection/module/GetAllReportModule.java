package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.GetAllReportDataSource;
import kh.com.metfone.emoney.eshop.data.local.GetAllReportLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.GetAllReportRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/20/2018.
 */
@Module
public class GetAllReportModule {
    @Provides
    @Singleton
    @Local
    GetAllReportDataSource provideLocalGetAllReportDataSource() {
        return new GetAllReportLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    GetAllReportDataSource provideRemoteAllReportDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new GetAllReportRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
