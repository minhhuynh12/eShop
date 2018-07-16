package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ReceiptDataSource;
import kh.com.metfone.emoney.eshop.data.local.ReceiptLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ReceiptRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/13/2018.
 */
@Module
public class ReceiptModule {
    @Provides
    @Singleton
    @Local
    ReceiptDataSource provideReceiptLocalDataSource() {
        return new ReceiptLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    ReceiptDataSource provideReceiptRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ReceiptRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
