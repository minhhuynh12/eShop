package kh.com.metfone.emoney.eshop.ui.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by DCV on 3/1/2018.
 */
public class BasePresenter<V extends MvpView> implements Presenter<V> {
//    private final ClientLogUseCase clientLogUseCase;
    private V mMvpView;

//    @Inject
//    protected BasePresenter(ClientLogUseCase clientLogUseCase) {
//        this.clientLogUseCase = clientLogUseCase;
//    }

    @Override
    public void attachView(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    protected void dispose(CompositeDisposable compositeSubscription) {
        if (compositeSubscription != null) {
            compositeSubscription.dispose();
        }
    }

    public void onResume() {

    }

    public void onPause() {

    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before requesting data to presenter");
        }
    }

//    private void saveLog(String body) {
//        clientLogUseCase.execute(new DisposableSubscriber<BaseActivity>() {
//            @Override
//            public void onNext(BaseActivity baseActivity) {
//
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        }, ClientLogUseCase.Param.forParam(body));
//    }
}
