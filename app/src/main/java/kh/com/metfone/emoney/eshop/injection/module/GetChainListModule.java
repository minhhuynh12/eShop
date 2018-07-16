package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.ShopDataSource;
import kh.com.metfone.emoney.eshop.data.local.ShopLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.ShopRemoteDataSource;
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
public class GetChainListModule {
    @Provides
    @Singleton
    @Local
    ShopDataSource provideLocaGetChainListDataSource() {
        return new ShopLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    ShopDataSource provideRemoteGetChainListDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new ShopRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
