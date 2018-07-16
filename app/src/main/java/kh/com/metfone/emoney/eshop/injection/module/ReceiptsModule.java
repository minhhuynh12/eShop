package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ReceiptsDataSource;
import kh.com.metfone.emoney.eshop.data.local.ReceiptsLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ReceiptsRemoteDataSource;
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
public class ReceiptsModule {
    @Provides
    @Singleton
    @Local
    ReceiptsDataSource provideLocalReceiptsDataSource() {
        return new ReceiptsLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    ReceiptsDataSource provideRemoteReceiptsDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ReceiptsRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
