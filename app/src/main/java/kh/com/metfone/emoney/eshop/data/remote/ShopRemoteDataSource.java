package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ShopDataSource;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/15/2018.
 */
@Singleton
public class ShopRemoteDataSource implements ShopDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject
    public ShopRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<ChainList> getChainList() {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        return retrofitService.getChainList(sharePreferenceHelper.getUserName(), accessToken, sharePreferenceHelper.getLanguage());
    }

    @Override
    public Flowable<UpdateShopInfo> updateShop(String shopName, String contactPhone, String contactEmail, String areaCode, String shopAddress) {
        String accessToken = "Basic " + sharePreferenceHelper.getAccessToken();
        Map<String, String> body = new HashMap<>();
        body.put(Const.KEY_SHOP_NAME, shopName);
        body.put(Const.KEY_CONTACT_PHONE, contactPhone);
        body.put(Const.KEY_CONTACT_EMAIL, contactEmail);
        body.put(Const.KEY_AREA_CODE, areaCode);
        body.put(Const.KEY_SHOP_ADDRESS, shopAddress);
        return retrofitService.updateShopInfo(sharePreferenceHelper.getUserName(), accessToken, sharePreferenceHelper.getLanguage(), body);
    }
}
