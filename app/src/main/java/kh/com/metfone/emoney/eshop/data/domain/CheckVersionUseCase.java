package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.CheckVersionRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/9/2018.
 */

public class CheckVersionUseCase extends FlowableUseCase<VersionInformation, CheckVersionUseCase.Params> {
    private final CheckVersionRepository checkVersionRepository;

    @Inject
    CheckVersionUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, CheckVersionRepository checkVersionRepository) {
        super(threadExecutor, postExecutionThread);
        this.checkVersionRepository = checkVersionRepository;
    }

    @Override
    Flowable<VersionInformation> buildUseCaseObservable(Params params) {
        return checkVersionRepository.getVersionInformation(params.currentVersion);
    }

    public static final class Params {
        private String currentVersion;

        public Params(String currentVersion) {
            this.currentVersion = currentVersion;
        }

        public static Params forParams(String currentVersion) {
            return new Params(currentVersion);
        }
    }
}
