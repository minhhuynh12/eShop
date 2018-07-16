package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.ShopRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */

public class UpdateShopInfoUseCase extends FlowableUseCase<UpdateShopInfo, UpdateShopInfoUseCase.Params> {
    private final ShopRepository shopRepository;

    @Inject
    UpdateShopInfoUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, ShopRepository shopRepository) {
        super(threadExecutor, postExecutionThread);
        this.shopRepository = shopRepository;
    }

    @Override
    Flowable<UpdateShopInfo> buildUseCaseObservable(Params params) {
        return shopRepository.updateShop(params.shopName, params.contactPhone, params.contactEmail, params.areaCode, params.shopAddress);
    }

    public static class Params {
        private String shopName;
        private String contactEmail;
        private String contactPhone;
        private String areaCode;
        private String shopAddress;

        public Params(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
            this.shopName = shopName;
            this.contactPhone = contactPhone;
            this.contactEmail = contactEmail;
            this.areaCode = areaCode;
            this.shopAddress = shopAddress;
        }

        public static Params forParams(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
            return new Params(shopName, contactPhone, contactEmail, areaCode, shopAddress);
        }
    }
}
