package com.bri.ojt.Network;

import com.bri.ojt.Util.Consts;

import java.util.concurrent.TimeUnit;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient sInstance = null;
    private static RetrofitClient sNewInstance = null;
    private static RetrofitClient sHInstance = null;
    private API api;

    private RetrofitClient(boolean old, ResponseInterface responseInterface) {

        String baseUrl = old ? Consts.getInstance().getBaseUrl1() : Consts.getInstance().getBaseUrl2();
        String sslKey = old ? Consts.SSL_PUBLIC_KEY_1 : Consts.SSL_PUBLIC_KEY_2;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        RetrofitInterceptor interceptor = new RetrofitInterceptor(responseInterface);
        CertificatePinner certificatePinner = new CertificatePinner.Builder().add(baseUrl, sslKey).build();

        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        okhttpBuilder.addInterceptor(logging);
        okhttpBuilder.addInterceptor(interceptor);
        okhttpBuilder.connectTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(60, TimeUnit.SECONDS);
        okhttpBuilder.certificatePinner(certificatePinner);

        OkHttpClient client = okhttpBuilder.build();

        String httpsUrl = "https://" + baseUrl;

        Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl(httpsUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = sRetrofit.create(API.class);
    }

    public static RetrofitClient getInstance(ResponseInterface responseInterface) {
        if (sInstance == null) {
            sInstance = new RetrofitClient(true, responseInterface);
        }
        return sInstance;
    }

    public static RetrofitClient getNewInstance(ResponseInterface responseInterface) {
        if (sNewInstance == null) {
            sNewInstance = new RetrofitClient(false, responseInterface);
        }
        return sNewInstance;
    }

    public static RetrofitClient getHInstance(ResponseInterface responseInterface) {
        if (sHInstance == null) {
            sHInstance = new RetrofitClient(true, responseInterface);
        }
        return sHInstance;
    }

    public API getAPI() {
        return api;
    }
}
