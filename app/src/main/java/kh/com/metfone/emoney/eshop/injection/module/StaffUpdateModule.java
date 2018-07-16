package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.StaffUpdateDataSource;
import kh.com.metfone.emoney.eshop.data.local.StaffUpdateLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.data.remote.StaffUpdateRemoteDataSource;
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
public class StaffUpdateModule {
    @Provides
    @Singleton
    @Local
    StaffUpdateDataSource provideLocalStaffUpdateDataSource() {
        return new StaffUpdateLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    StaffUpdateDataSource provideRemoteStaffUpdateDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new StaffUpdateRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
