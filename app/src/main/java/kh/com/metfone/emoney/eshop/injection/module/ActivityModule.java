package kh.com.metfone.emoney.eshop.injection.module;

import android.app.Activity;
import android.content.Context;

import kh.com.metfone.emoney.eshop.injection.ActivityContext;
import kh.com.metfone.emoney.eshop.injection.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import io.realm.Realm;

/**
 * Created by DCV on 3/1/2018.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideApplication() {
        return mActivity;
    }

    @Provides
    CompositeDisposable provideCompositeSubscription() {
        return new CompositeDisposable();
    }

    @Provides
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }
}
