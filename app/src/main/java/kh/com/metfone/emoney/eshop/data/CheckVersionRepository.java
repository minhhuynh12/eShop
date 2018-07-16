package kh.com.metfone.emoney.eshop.data;

import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.injection.Local;
import kh.com.metfone.emoney.eshop.injection.Remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */
@Singleton
public class CheckVersionRepository implements CheckVersionDataSource {
    private final CheckVersionDataSource localCheckVersionDataSource;
    private final CheckVersionDataSource remoteCheckVersionDataSource;

    @Inject
    CheckVersionRepository(@Local CheckVersionDataSource localCheckVersionDataSource,
                           @Remote CheckVersionDataSource remoteCheckVersionDataSource) {
        this.localCheckVersionDataSource = localCheckVersionDataSource;
        this.remoteCheckVersionDataSource = remoteCheckVersionDataSource;
    }

    @Override
    public Flowable<VersionInformation> getVersionInformation(String version) {
        return remoteCheckVersionDataSource.getVersionInformation(version);
    }
}
