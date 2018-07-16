package kh.com.metfone.emoney.eshop.ui.receipts;

import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/2/2018.
 */

public interface ReceiptsMvpView extends BaseMvpView {
    void getReceiptsListSuccess(ReceiptsResponse receipt);

    void getReceiptsListFailed(String errorMessage);

    void getConfigSuccess(CommonConfigInfo commonConfigInfo);

    void getConfigFailed(String messageError);

    void getChainListSuccess(ChainList chainList);

    void getChainListFailed(String message);

    void getUserInfoSuccess(UserInformation userInformation);
}
