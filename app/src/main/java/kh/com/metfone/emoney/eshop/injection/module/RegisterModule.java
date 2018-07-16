package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.RegisterDataSource;
import kh.com.metfone.emoney.eshop.data.local.RegisterLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.RegisterRemoteDataSource;
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
public class RegisterModule {
    @Provides
    @Singleton
    @Local
    RegisterDataSource provideLocalRegisterDataSource() {
        return new RegisterLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    RegisterDataSource provideRemoteRegisterDataSource(RetrofitService retrofitService) {
        return new RegisterRemoteDataSource(retrofitService);
    }
}
