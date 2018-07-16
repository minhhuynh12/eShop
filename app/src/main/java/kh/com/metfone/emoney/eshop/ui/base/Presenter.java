package kh.com.metfone.emoney.eshop.ui.base;

/**
 * Created by DCV on 3/1/2018.
 */
public interface Presenter<V extends MvpView> {

    void attachView(V mvpView);

    void detachView();
}
