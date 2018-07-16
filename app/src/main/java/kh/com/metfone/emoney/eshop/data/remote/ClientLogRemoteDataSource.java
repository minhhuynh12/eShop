package kh.com.metfone.emoney.eshop.data.remote;

import kh.com.metfone.emoney.eshop.consts.Const;
import kh.com.metfone.emoney.eshop.data.ClientLogDataSource;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ClientLog;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Created by DCV on 3/16/2018.
 */
@Singleton
public class ClientLogRemoteDataSource implements ClientLogDataSource {
    private final RetrofitService retrofitService;
    private final SharePreferenceHelper sharePreferenceHelper;

    @Inject()
    public ClientLogRemoteDataSource(RetrofitService retrofitService, SharePreferenceHelper sharePreferenceHelper) {
        this.retrofitService = retrofitService;
        this.sharePreferenceHelper = sharePreferenceHelper;
    }

    @Override
    public Flowable<BaseResult> saveClientLog(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            Map<String, String> map = new HashMap<String, String>();
            map.put(Const.KEY_LOGGED_TOKEN, sharePreferenceHelper.getAccessToken());

            Iterator<String> keysItr = jsonObject.keys();
            while (keysItr.hasNext()) {
                String key = keysItr.next();
                if (!key.equals("id") && !key.equals("isLogServerSuccess")) {
                    String value = jsonObject.getString(key);
                    map.put(key, value);
                }
            }
            return retrofitService.clientLog(sharePreferenceHelper.getLanguage(), map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void saveClientLogLocal(ClientLog clientLog) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clearClientLog() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Flowable<List<ClientLog>> getClientLogFailed() {
        throw new UnsupportedOperationException();
    }
}
