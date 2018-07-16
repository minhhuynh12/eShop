package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.AreaGroup;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public interface AreaGroupDataSource {
    Flowable<AreaGroup> getAreaGroup();

    Flowable<AreaGroup> getAreaGroupLocal();

    void saveAreaGroup(AreaGroup areaGroup);
}
