package kh.com.metfone.emoney.eshop.ui.shopdetail;

import kh.com.metfone.emoney.eshop.data.domain.GetUserInforUseCase;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by Administrator on 3/7/2018.
 */

public class ShopDetailPresenter extends BasePresenter<ShopDetailMvpView> {
    private final GetUserInforUseCase getUserInforUseCase;

    @Inject
    ShopDetailPresenter(GetUserInforUseCase getUserInforUseCase) {
        this.getUserInforUseCase = getUserInforUseCase;
    }

    public void getUserInfo() {
        getUserInforUseCase.execute(new DisposableSubscriber<UserInformation>() {
            @Override
            public void onNext(UserInformation userInformation) {
                if(getMvpView() != null) {
                    getMvpView().getUserInfoSuccess(userInformation);
                }
            }

            @Override
            public void onError(Throwable t) {
                if(getMvpView() != null) {
                    getMvpView().getUserInfoFailed(t.getMessage());
                }
            }

            @Override
            public void onComplete() {

            }
        }, null);
    }
}
