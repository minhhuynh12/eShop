package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.AreaGroupDataSource;
import kh.com.metfone.emoney.eshop.data.local.AreaGroupLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.AreaGroupRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/9/2018.
 */
@Module
public class AreaGroupModule {
    @Provides
    @Singleton
    @Local
    AreaGroupDataSource provideLocaAreaGroupDataSource() {
        return new AreaGroupLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    AreaGroupDataSource provideRemoteAreaGroupDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new AreaGroupRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
