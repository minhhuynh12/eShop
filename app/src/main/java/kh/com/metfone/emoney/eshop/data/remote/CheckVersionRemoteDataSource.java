package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.CheckVersionDataSource;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */
@Singleton
public class CheckVersionRemoteDataSource implements CheckVersionDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public CheckVersionRemoteDataSource(RetrofitService retrofitService,
                                        SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<VersionInformation> getVersionInformation(String version) {
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_PLATFORM, Const.VALUE_ANDROID_PLATFORM);
        body.put(Const.KEY_VERSION, version);
        return retrofitService.checkVersionInformation(sharePreferenceHelper.getLanguage(), body);
    }
}
