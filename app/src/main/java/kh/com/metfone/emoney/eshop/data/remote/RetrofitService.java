package kh.com.metfone.emoney.eshop.data.remote;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kh.com.metfone.emoney.eshop.Manifest;
import kh.com.metfone.emoney.eshop.data.models.AreaGroup;
import kh.com.metfone.emoney.eshop.data.models.BaseResult;
import kh.com.metfone.emoney.eshop.data.models.ChainList;
import kh.com.metfone.emoney.eshop.data.models.CommonConfigInfo;
import kh.com.metfone.emoney.eshop.data.models.Receipt;
import kh.com.metfone.emoney.eshop.data.models.Report;
import kh.com.metfone.emoney.eshop.data.models.ReceiptsResponse;
import kh.com.metfone.emoney.eshop.data.models.StaffResetPassResponse;
import kh.com.metfone.emoney.eshop.data.models.StaffsResponse;
import kh.com.metfone.emoney.eshop.data.models.UpdateShopInfo;
import kh.com.metfone.emoney.eshop.data.models.UserInformation;
import kh.com.metfone.emoney.eshop.data.models.UssdResponse;
import kh.com.metfone.emoney.eshop.data.models.VersionInformation;
import kh.com.metfone.emoney.eshop.utils.SharePreferenceHelper;

import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Flowable;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

//TODO change api
public interface RetrofitService {
    String END_POINT = "https://36.37.242.80:8668/";//real link
//    String END_POINT = "https://36.37.242.80:8778/";//test link


    @POST("common/version")
    Flowable<VersionInformation> checkVersionInformation(@Header("x-language") String language,
                                                         @Body Map<String, String> body);

    @GET("common/app-config")
    Flowable<CommonConfigInfo> getCommonInformation();


    @POST("auth/login")
    Flowable<UserInformation> login(@Header("x-language") String language,
                                    @Header("x-f-token") String tokenFirebase,
                                    @Body Map<String, String> body);

    @GET("auth/forgotpass/getcode/{msisdn}")
    Flowable<BaseResult> getCodeForForgotPass(@Header("x-language") String language,
                                              @Path("msisdn") String msisdn);

    @GET("/auth/register/getcode/{msisdn}")
    Flowable<BaseResult> getRegisterCode(@Path("msisdn") String msisdn);

    @POST("/auth/register")
    Flowable<BaseResult> register(@Body Map<String, String> body);

    @GET("payment/ussd/{receiptId}")
    Flowable<UssdResponse> confirmByPhone(@Header("x-username") String username,
                                          @Header("Authorization") String accessToken,
                                          @Path("receiptId") int receiptId);

    @POST("payment/receipts")
    Flowable<ReceiptsResponse> getReceipts(@Header("x-username") String username,
                                           @Header("Authorization") String accessToken,
                                           @Header("x-language") String language,
                                           @Body Map<String, String> body);

    @POST("auth/forgot-password")
    Flowable<BaseResult> forgotPassword(@Header("x-language") String language,
                                        @Body Map<String, String> body);

    @POST("payment/receipt")
    Flowable<Receipt> newReceipt(@Header("x-username") String username,
                                 @Header("Authorization") String accessToken,
                                 @Header("x-language") String language,
                                 @Body Map<String, String> body);

    @GET("payment/receipt/{receiptId}")
    Flowable<Receipt> getReceiptInfor(@Header("x-username") String username,
                                      @Header("Authorization") String accessToken,
                                      @Header("x-language") String language,
                                      @Path("receiptId") int receiptId);

    @POST("setting/change-password")
    Flowable<BaseResult> changePassword(@Header("x-username") String username,
                                        @Header("Authorization") String accessToken,
                                        @Header("x-language") String language,
                                        @Body Map<String, String> body);

    @GET("auth/logout")
    Flowable<BaseResult> logout(@Header("x-username") String username,
                                @Header("Authorization") String accessToken,
                                @Header("x-language") String language);

    @POST("setting")
    Flowable<BaseResult> changeExchangeRate(@Header("x-username") String username,
                                            @Header("Authorization") String accessToken,
                                            @Header("x-language") String language,
                                            @Body Map<String, String> body);

    @POST("common/client-log")
    Flowable<BaseResult> clientLog(@Header("x-language") String language,
                                   @Body Map<String, String> body);

    @POST("report/all")
    Flowable<Report> getAllReport(@Header("x-username") String username,
                                  @Header("Authorization") String accessToken,
                                  @Header("x-language") String language,
                                  @Body Map<String, String> body);

    @GET("shop/chain")
    Flowable<ChainList> getChainList(@Header("x-username") String username,
                                     @Header("Authorization") String accessToken,
                                     @Header("x-language") String language);

    @GET("staff/all")
    Flowable<StaffsResponse> getAllStaffs(@Header("x-username") String username,
                                          @Header("Authorization") String accessToken,
                                          @Header("x-language") String language);


    @POST("staff/new")
    Flowable<BaseResult> newStaffs(@Header("x-username") String username,
                                   @Header("Authorization") String accessToken,
                                   @Header("x-language") String language,
                                   @Body Map<String, String> body);

    @GET("staff/reset-password/{staffId}")
    Flowable<StaffResetPassResponse> resetPasswordStaff(@Header("x-username") String username,
                                                        @Header("Authorization") String accessToken,
                                                        @Header("x-language") String language,
                                                        @Path("staffId") int staffId);

    @POST("staff/update")
    Flowable<BaseResult> updateStaffs(@Header("x-username") String username,
                                      @Header("Authorization") String accessToken,
                                      @Header("x-language") String language,
                                      @Body Map<String, String> body);

    @GET("common/areas")
    Flowable<AreaGroup> getAreaList(@Header("x-language") String language);

    @POST("shop/update")
    Flowable<UpdateShopInfo> updateShopInfo(@Header("x-username") String username,
                                            @Header("Authorization") String accessToken,
                                            @Header("x-language") String language,
                                            @Body Map<String, String> body);

    class Creator {

        private static Interceptor cacheInterceptor;
        public static Retrofit newRetrofitInstance(final Context context,
                                                   final boolean isNetworkAvailable,
                                                   SharePreferenceHelper sharePreferenceHelper) {

            cacheInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (!isNetworkAvailable) {
                        int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                        request.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                                .build();
                    }
                   /* Response originalResponse = chain;

//                            .withReadTimeout(30, TimeUnit.SECONDS)
                            .proceed(request);
                    return originalResponse;*/
                   if (request.tag().toString().contains("payment/ussd")) {
                       return chain
                               .withReadTimeout(180, TimeUnit.SECONDS)
                               .withConnectTimeout(180,TimeUnit.SECONDS)
                               .proceed(request);
                   } else {
                       return chain
                               .withReadTimeout(60, TimeUnit.SECONDS)
                               .withConnectTimeout(60,TimeUnit.SECONDS)
                               .proceed(request);
                   }
                }
            };
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //Setup cache
            File httpCacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "OKHttpCache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);

            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(60, TimeUnit.SECONDS);
                builder.readTimeout(60, TimeUnit.SECONDS);
                builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });

                TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String uuid = tManager.getDeviceId();
                builder.addInterceptor(cacheInterceptor)
                        .cache(cache)
                        .addInterceptor(interceptor)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();
                                Request.Builder builder = original.newBuilder()
                                        .header("x-d-token", uuid)
                                        .header("x-language", sharePreferenceHelper.getLanguage());
                                Request request = builder
                                        .method(original.method(), original.body())
                                        .build();
                                return chain.proceed(request);
                            }
                        });
                OkHttpClient okHttpClient = builder.build();
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:SSS'Z'")
                        .create();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(END_POINT)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(okHttpClient)
                        .build();

                return retrofit;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
}
