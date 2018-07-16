package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.CheckStatusPaymentDataSource;
import kh.com.metfone.emoney.eshop.data.local.CheckStatusPaymentLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.CheckStatusPaymentRemoteDataSource;
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
public class CheckStatusPaymentModule {
    @Provides
    @Singleton
    @Local
    CheckStatusPaymentDataSource provideLocalCheckStatusDataSource() {
        return new CheckStatusPaymentLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    CheckStatusPaymentDataSource provideRemoteCheckStatusDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new CheckStatusPaymentRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
