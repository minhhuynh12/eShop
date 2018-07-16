package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.StaffsDataSource;
import kh.com.metfone.emoney.eshop.data.local.StaffsLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.StaffsRemoteDataSource;
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
public class StaffsModule {
    @Provides
    @Singleton
    @Local
    StaffsDataSource provideLocalStaffsDataSource() {
        return new StaffsLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    StaffsDataSource provideRemoteStaffsDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new StaffsRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
