package kh.com.metfone.emoney.eshop.base;

import android.support.annotation.IntDef;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.component.ApplicationComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

/**
 * Created by quanlt on 11/14/17.
 */

public abstract class BaseJob extends Job {

    public static final int UI_HIGH = 10;
    public static final int BACKGROUND = 1;
    @Inject
    protected transient RetrofitService apiService;

    public BaseJob(Params params) {
        super(params);
    }

    public void inject(ApplicationComponent appComponent) {

    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({UI_HIGH, BACKGROUND})
    public @interface Priority {

    }
}