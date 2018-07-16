package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class AreaGroupRepository implements AreaGroupDataSource {

    private final AreaGroupDataSource areaGroupLocalDataSource;
    private final AreaGroupDataSource areaGroupRemoteDataSource;

    @Inject
    AreaGroupRepository(@Local AreaGroupDataSource areaGroupLocalDataSource,
                        @Remote AreaGroupDataSource areaGroupRemoteDataSource) {
        this.areaGroupLocalDataSource = areaGroupLocalDataSource;
        this.areaGroupRemoteDataSource = areaGroupRemoteDataSource;
    }

    @Override
    public Flowable<AreaGroup> getAreaGroup() {
        return areaGroupRemoteDataSource.getAreaGroup();
    }

    @Override
    public Flowable<AreaGroup> getAreaGroupLocal() {
        return areaGroupLocalDataSource.getAreaGroupLocal();
    }

    @Override
    public void saveAreaGroup(AreaGroup areaGroup) {
        areaGroupLocalDataSource.saveAreaGroup(areaGroup);
    }
}
