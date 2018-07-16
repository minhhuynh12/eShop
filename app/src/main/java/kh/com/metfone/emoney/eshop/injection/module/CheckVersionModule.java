package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.CheckVersionDataSource;
import kh.com.metfone.emoney.eshop.data.local.CheckVersionLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.CheckVersionRemoteDataSource;
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
public class CheckVersionModule {
    @Provides
    @Singleton
    @Local
    CheckVersionDataSource provideLocalCheckVersionDataSource() {
        return new CheckVersionLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    CheckVersionDataSource provideRemoteCheckVersionDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new CheckVersionRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
