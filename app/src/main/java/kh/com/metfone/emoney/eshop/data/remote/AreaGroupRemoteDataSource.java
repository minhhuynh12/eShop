package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.data.AreaGroupDataSource;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class AreaGroupRemoteDataSource implements AreaGroupDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public AreaGroupRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<AreaGroup> getAreaGroup() {
        return retrofitService.getAreaList(sharePreferenceHelper.getLanguage());
    }

    @Override
    public Flowable<AreaGroup> getAreaGroupLocal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveAreaGroup(AreaGroup areaGroup) {
        throw new UnsupportedOperationException();
    }
}
