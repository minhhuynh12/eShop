package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ForgotPassChangeDataSource;
import kh.com.metfone.emoney.eshop.data.local.ForgotPassChangeLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ForgotPassChangeRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/12/2018.
 */
@Module
public class ForgotPassChangeModule {
    @Provides
    @Singleton
    @Local
    ForgotPassChangeDataSource provideLocalForgotPassChangeDataSource() {
        return new ForgotPassChangeLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    ForgotPassChangeDataSource provideRemoteForgotPassChangeDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ForgotPassChangeRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
