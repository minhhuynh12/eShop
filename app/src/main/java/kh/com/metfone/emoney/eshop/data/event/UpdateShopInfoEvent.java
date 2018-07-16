package kh.com.metfone.emoney.eshop.data.event;

import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;

/**
 * Created by DCV on 3/23/2018.
 */

public class UpdateShopInfoEvent {
    private UpdateShopInfo updateShopInfo;

    public UpdateShopInfoEvent(UpdateShopInfo updateShopInfo) {
        this.updateShopInfo = updateShopInfo;
    }

    public UpdateShopInfo getUpdateShopInfo() {
        return updateShopInfo;
    }
}
