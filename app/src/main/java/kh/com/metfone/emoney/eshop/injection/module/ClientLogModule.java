package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ClientLogDataSource;
import kh.com.metfone.emoney.eshop.data.local.ClientLogLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ClientLogRemoteDataSource;
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
public class ClientLogModule {
    @Provides
    @Singleton
    @Local
    ClientLogDataSource provideLocalClientLogDataSource() {
        return new ClientLogLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    ClientLogDataSource provideRemoteClientLogDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ClientLogRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
