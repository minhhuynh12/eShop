package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.StaffNewDataSource;
import kh.com.metfone.emoney.eshop.data.local.StaffNewLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.StaffNewRemoteDataSource;
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
public class StaffNewModule {
    @Provides
    @Singleton
    @Local
    StaffNewDataSource provideLocalStaffNewDataSource() {
        return new StaffNewLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    StaffNewDataSource provideRemoteStaffNewDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new StaffNewRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
