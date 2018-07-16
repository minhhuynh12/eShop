package kh.com.metfone.emoney.eshop.injection.module;

import kh.com.metfone.emoney.eshop.data.GetCodeDataSource;
import kh.com.metfone.emoney.eshop.data.local.GetCodeForgotPassLocalDataSource;
import kh.com.metfone.emoney.eshop.data.remote.GetCodeForgotPassRemoteDataSource;
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
public class GetCodeForgotPassModule {
    @Provides
    @Singleton
    @Local
    GetCodeDataSource provideLocalGetCodeDataSource() {
        return new GetCodeForgotPassLocalDataSource();
    }

    @Provides
    @Singleton
    @Remote
    GetCodeDataSource provideRemoteGetCodeDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        return new GetCodeForgotPassRemoteDataSource(retrofitService, sharePreferenceHelper);
    }
}
