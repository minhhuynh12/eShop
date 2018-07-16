package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.StaffResetPasswordDataSource;
import kh.com.metfone.emoney.eshop.data.local.StaffResetPasswordLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.StaffResetPasswordRemoteDataSource;
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
public class StaffResetPasswordModule {
    @Provides
    @Singleton
    @Local
    StaffResetPasswordDataSource provideLocalResetPasswordDataSource() {
        return new StaffResetPasswordLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    StaffResetPasswordDataSource provideRemoteResetPasswordDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new StaffResetPasswordRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
