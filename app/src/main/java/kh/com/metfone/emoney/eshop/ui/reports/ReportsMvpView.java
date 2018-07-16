package kh.com.metfone.emoney.eshop.ui.reports;

import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.ui.base.BaseMvpView;

/**
 * Created by DCV on 3/9/2018.
 */

public interface ReportsMvpView extends BaseMvpView {
    void getReportsSuccess(Report report);

    void getReportsFailed(String errorMessage);

    void getUserInfoSuccess(UserInformation userInformation);
}
