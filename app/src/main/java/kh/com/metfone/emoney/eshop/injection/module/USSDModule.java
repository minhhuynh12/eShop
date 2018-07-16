package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.USSDDataSource;
import kh.com.metfone.emoney.eshop.data.local.USSDLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.data.remote.UssdRemoteDataSource;
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
public class USSDModule {
    @Provides
    @Singleton
    @Local
    USSDDataSource provideLocalUssdDataSource() {
        return new USSDLocalDataSource();
    }
    @Provides
    @Singleton
    @Remote
    USSDDataSource provideRemoteUssdDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new UssdRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
