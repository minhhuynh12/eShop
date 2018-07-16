package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.UserDataSource;
import kh.com.metfone.emoney.eshop.data.local.UserLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.data.remote.UserRemoteDataSource;
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
public class UserModule {
    @Provides
    @Singleton
    @Local
    UserDataSource provideLocalUserDataSource() {
        return new UserLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    UserDataSource provideRemoteUserDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new UserRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
