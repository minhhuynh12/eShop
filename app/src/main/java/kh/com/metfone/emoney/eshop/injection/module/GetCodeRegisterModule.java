package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.GetCodeRegisterDataSource;
import kh.com.metfone.emoney.eshop.data.local.GetCodeRegisterLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.GetCodeRegisterRemoteDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by DCV on 3/12/2018.
 */
@Module
public class GetCodeRegisterModule {
    @Provides
    @Singleton
    @Local
    GetCodeRegisterDataSource provideLocalGetCodeDataSource() {
        return new GetCodeRegisterLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    GetCodeRegisterDataSource provideRemoteGetCodeDataSource(RetrofitService retrofitService) {
        return new GetCodeRegisterRemoteDataSource(retrofitService);
    }
}
