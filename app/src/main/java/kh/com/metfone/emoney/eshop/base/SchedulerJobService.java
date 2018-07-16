package kh.com.metfone.emoney.eshop.base;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.scheduling.FrameworkJobSchedulerService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by quanlt on 11/14/17.
 */

@Singleton
public class SchedulerJobService extends FrameworkJobSchedulerService {

    @Inject
    JobManager jobManager;

    @Inject
    public SchedulerJobService() {
    }

    public SchedulerJobService(JobManager jobManager) {
        this.jobManager = jobManager;
    }

    @Override
    protected JobManager getJobManager() {
        return jobManager;
    }
}
