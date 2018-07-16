package kh.com.metfone.emoney.eshop.data.local;

import kh.com.metfone.emoney.eshop.data.CheckVersionDataSource;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public class CheckVersionLocalDataSource implements CheckVersionDataSource {
    @Override
    public Flowable<VersionInformation> getVersionInformation(String version) {
        return null;
    }
}
