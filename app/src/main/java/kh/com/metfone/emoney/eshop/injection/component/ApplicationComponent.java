package kh.com.metfone.emoney.eshop.injection.component;

import android.content.Context;

import kh.com.metfone.emoney.eshop.data.AreaGroupRepository;
import kh.com.metfone.emoney.eshop.data.ChangePasswordRepository;
import kh.com.metfone.emoney.eshop.data.CheckStatusPaymentRepository;
import kh.com.metfone.emoney.eshop.data.CheckVersionRepository;
import kh.com.metfone.emoney.eshop.data.ClientLogRepository;
import kh.com.metfone.emoney.eshop.data.ForgotPassChangeRepository;
import kh.com.metfone.emoney.eshop.data.GetAllReportRepository;
import kh.com.metfone.emoney.eshop.data.ShopRepository;
import kh.com.metfone.emoney.eshop.data.GetCodeRegisterRepository;
import kh.com.metfone.emoney.eshop.data.GetCodeRepository;
import kh.com.metfone.emoney.eshop.data.GetCommonConfigInfoRepository;
import kh.com.metfone.emoney.eshop.data.ReceiptRepository;
import kh.com.metfone.emoney.eshop.data.ReceiptsRepository;
import kh.com.metfone.emoney.eshop.data.RegisterRepository;
import kh.com.metfone.emoney.eshop.data.StaffNewRepository;
import kh.com.metfone.emoney.eshop.data.StaffResetPasswordRepository;
import kh.com.metfone.emoney.eshop.data.StaffUpdateRepository;
import kh.com.metfone.emoney.eshop.data.StaffsRepository;
import kh.com.metfone.emoney.eshop.data.USSDRepository;
import kh.com.metfone.emoney.eshop.data.UserRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.remote.RetrofitService;
import kh.com.metfone.emoney.eshop.injection.ApplicationContext;
import kh.com.metfone.emoney.eshop.injection.module.ApplicationModule;
import kh.com.metfone.emoney.eshop.injection.module.AreaGroupModule;
import kh.com.metfone.emoney.eshop.injection.module.ChangePasswordModule;
import kh.com.metfone.emoney.eshop.injection.module.CheckStatusPaymentModule;
import kh.com.metfone.emoney.eshop.injection.module.CheckVersionModule;
import kh.com.metfone.emoney.eshop.injection.module.ClientLogModule;
import kh.com.metfone.emoney.eshop.injection.module.ForgotPassChangeModule;
import kh.com.metfone.emoney.eshop.injection.module.GetAllReportModule;
import kh.com.metfone.emoney.eshop.injection.module.GetChainListModule;
import kh.com.metfone.emoney.eshop.injection.module.GetCodeForgotPassModule;
import kh.com.metfone.emoney.eshop.injection.module.GetCodeRegisterModule;
import kh.com.metfone.emoney.eshop.injection.module.GetCommonConfigModule;
import kh.com.metfone.emoney.eshop.injection.module.ReceiptModule;
import kh.com.metfone.emoney.eshop.injection.module.ReceiptsModule;
import kh.com.metfone.emoney.eshop.injection.module.RegisterModule;
import kh.com.metfone.emoney.eshop.injection.module.StaffNewModule;
import kh.com.metfone.emoney.eshop.injection.module.StaffResetPasswordModule;
import kh.com.metfone.emoney.eshop.injection.module.StaffUpdateModule;
import kh.com.metfone.emoney.eshop.injection.module.StaffsModule;
import kh.com.metfone.emoney.eshop.injection.module.USSDModule;
import kh.com.metfone.emoney.eshop.injection.module.UserModule;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by DCV on 3/1/2018.
 */
@Singleton
@Component(modules = {
        ApplicationModule.class,
        GetCommonConfigModule.class,
        CheckVersionModule.class,
        UserModule.class,
        GetCodeForgotPassModule.class,
        ForgotPassChangeModule.class,
        ReceiptModule.class,
        CheckStatusPaymentModule.class,
        USSDModule.class,
        GetCodeRegisterModule.class,
        RegisterModule.class,
        ReceiptModule.class,
        ReceiptsModule.class,
        StaffsModule.class,
        ChangePasswordModule.class,
        ClientLogModule.class,
        GetAllReportModule.class,
        StaffNewModule.class,
        StaffResetPasswordModule.class,
        StaffUpdateModule.class,
        GetChainListModule.class,
        AreaGroupModule.class})
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    CheckVersionRepository checkVersionRepository();

    GetCommonConfigInfoRepository getConfigCommonInfoRepository();

    UserRepository userRepository();

    GetCodeRepository getCodeRepository();

    ForgotPassChangeRepository forgotPassChangeRepository();

    ReceiptRepository receiptRepository();

    ReceiptsRepository receiptsRepository();

    ChangePasswordRepository changePasswordRepository();

    ClientLogRepository clientLogRepository();

    StaffsRepository staffsRepository();

    USSDRepository confirmByPhone();

    PostExecutionThread postExecutionThread();

    CheckStatusPaymentRepository checkStatusPaymentRepository();

    GetCodeRegisterRepository getRegisterCodeRepository();

    RegisterRepository registerRepository();

    GetAllReportRepository getAllReportRepository();

    ShopRepository getChainListRepository();

    StaffNewRepository staffNewRepository();

    StaffResetPasswordRepository resetPassForStaffRepository();

    StaffUpdateRepository updateStaffRepository();

    AreaGroupRepository areaGroupRepository();

    ThreadExecutor threadExecutor();

    Retrofit retrofit();

    RetrofitService retrofitService();

    SharePreferenceHelper sharePreferenceHelper();

}
