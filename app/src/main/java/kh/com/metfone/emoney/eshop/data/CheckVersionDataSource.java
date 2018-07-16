package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.VersionInformation;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public interface CheckVersionDataSource {
    Flowable<VersionInformation> getVersionInformation(String version);
}
