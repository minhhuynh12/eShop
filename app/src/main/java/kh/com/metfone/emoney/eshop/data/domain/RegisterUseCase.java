package kh.com.metfone.emoney.eshop.data.domain;

import kh.com.metfone.emoney.eshop.data.RegisterRepository;
import kh.com.metfone.emoney.eshop.data.executor.PostExecutionThread;
import kh.com.metfone.emoney.eshop.data.executor.ThreadExecutor;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/12/2018.
 */

public class RegisterUseCase extends FlowableUseCase<BaseResult, RegisterUseCase.Param> {

    private final RegisterRepository repository;

    @Inject
    RegisterUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, RegisterRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }
    @Override
    Flowable<BaseResult> buildUseCaseObservable(Param param) {
        return repository.register(param.username, param.private_Code, param.password, param.category_id, param.shop_name, param.address );
    }

    public static class Param {
        private String username;
        private String private_Code;
        private String password;
        private String category_id;
        private String shop_name;
        private String address;

        public Param(String user_name, String private_code, String pass, String cate_id, String shopname, String adr) {
            this.username = user_name;
            this.private_Code = private_code;
            this.password = pass;
            this.category_id = cate_id;
            this.shop_name = shopname;
            this.address = adr;
        }

        public static Param forParam(String username, String private_Code, String password, String category_id, String shop_name, String address) {
            return new Param(username, private_Code, password, category_id, shop_name, address);
        }

    }
}
