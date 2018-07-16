package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ChangePasswordDataSource;
import kh.com.metfone.emoney.eshop.data.local.ChangePasswordLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ChangePasswordRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/15/2018.
 */
@Module
public class ChangePasswordModule {
    @Provides
    @Singleton
    @Local
    ChangePasswordDataSource provideLocalChangePasswordDataSource() {
        return new ChangePasswordLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    ChangePasswordDataSource provideRemoteChangePasswordDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ChangePasswordRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
