package kh.com.metfone.emoney.eshop.ui.base;

import com.google.gson.Gson;
import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.domain.ClientLogUseCase;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by DCV on 3/1/2018.
 */
public class BaseSaveLogPresenter<V extends MvpView> implements Presenter<V> {
    protected final ClientLogUseCase clientLogUseCase;
    private V mMvpView;

    @Inject
    protected BaseSaveLogPresenter(ClientLogUseCase clientLogUseCase) {
        this.clientLogUseCase = clientLogUseCase;
    }

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

    protected void saveLog(ClientLog clientLog) {
        Gson gson = new Gson();
        String logJson = gson.toJson(clientLog);
        clientLogUseCase.execute(new DisposableSubscriber<BaseResult>() {
            @Override
            public void onNext(BaseResult baseResult) {
                if (Const.VALUE_SUCCESS_CODE == baseResult.getStatus()) {
                    clientLog.setLogServerSuccess(true);
                } else {
                    clientLog.setLogServerSuccess(false);
                }
            }

            @Override
            public void onError(Throwable t) {
                clientLog.setLogServerSuccess(false);
            }

            @Override
            public void onComplete() {
                clientLogUseCase.saveClientLogLocal(clientLog);
            }
        }, ClientLogUseCase.Param.forParam(logJson));
    }
}
