package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoDataSource;
import kh.com.metfone.emoney.eshop.data.local.GetCommonConfigInfoLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.GetCommonConfigInfoRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/9/2018.  app-config
 */
@Module
public class GetCommonConfigModule {
    @Provides
    @Singleton
    @Local
    GetCommonConfigInfoDataSource provideLocalGetCommonConfigDataSource() {
        return new GetCommonConfigInfoLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    GetCommonConfigInfoDataSource provideRemoteGetCommonConfigDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new GetCommonConfigInfoRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
