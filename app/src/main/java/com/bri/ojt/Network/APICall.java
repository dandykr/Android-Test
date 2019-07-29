package com.bri.ojt.Network;

import com.bri.ojt.Model.Request.GetVAbyVA;
import com.bri.ojt.Model.Request.ReqOTP;
import com.bri.ojt.Model.Response.OTPResponse;
import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Util.BRISignature;
import com.bri.ojt.Util.Consts;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICall implements ResponseInterface {

    private APICallback actionCallback;

    public APICall(APICallback actionCallback) {
        this.actionCallback = actionCallback;
    }

    public void getVAbyVA(String va) {
        API api = RetrofitClient.getInstance(this).getAPI();
        Consts consts = Consts.getInstance();

        final GetVAbyVA vAbyVA = new GetVAbyVA(va);
        Map<String, String> map = BRISignature.getHeader(Consts.PATH_VA_BY_VA, "POST", consts.getToken()
                , consts.getGson().toJson(vAbyVA));

        Call<VAbyGroupResponseCashcard> call = api.getVAbyVA(map, vAbyVA);

        call.enqueue(new Callback<VAbyGroupResponseCashcard>() {
            @Override
            public void onResponse(Call<VAbyGroupResponseCashcard> call, Response<VAbyGroupResponseCashcard> response) {
                VAbyGroupResponseCashcard vAbyGroupResponse = response.body();

                if (vAbyGroupResponse != null) {
                    if(response.code() == 200 && vAbyGroupResponse.getResponseCode().equalsIgnoreCase("00")) {
                        VAbyGroupResponseCashcard.DetailVA detailVA = vAbyGroupResponse.getDT().get(0);

                        actionCallback.onSuccess(detailVA);
                    } else {
                        actionCallback.onFailed(response.body() != null ? response.body().getResponseException() : null);
                    }
                } else {
                    actionCallback.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<VAbyGroupResponseCashcard> call, Throwable t) {
                actionCallback.onFailed(t.getMessage());
            }
        });

    }

    public void requestOTP(VAbyGroupResponseCashcard.DetailVA detailVA, OTPVIA otpvia) {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        okhttpBuilder.connectTimeout(160, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(160, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(160, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.133.215:7512")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpBuilder.build())
                .build();

        API apiOTP = retrofit.create(API.class);

        ReqOTP data = new ReqOTP(detailVA.getNomorVirtual(), detailVA.getCustomerName(), detailVA.getHandphone(), detailVA.getEmail());

        Call<OTPResponse<String>> call;

        switch (otpvia) {
            case SMS:
                call = apiOTP.reqOtpSms(data);
                break;
                default:
                    call = apiOTP.reqOtpEmail(data);
        }

        call.enqueue(new Callback<OTPResponse<String>>() {
            @Override
            public void onResponse(Call<OTPResponse<String>> call, Response<OTPResponse<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getSuccessCode() == 1) {
                            actionCallback.onSuccess(response.body());
                        }
                    }
                } else {
                    actionCallback.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<OTPResponse<String>> call, Throwable t) {
                actionCallback.onFailed(t.getMessage());
            }
        });
    }

    public void verifyOTP(String otp) {
        OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
        okhttpBuilder.connectTimeout(160, TimeUnit.SECONDS);
        okhttpBuilder.readTimeout(160, TimeUnit.SECONDS);
        okhttpBuilder.writeTimeout(160, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.18.133.215:7512")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpBuilder.build())
                .build();

        API api = retrofit.create(API.class);

        ReqOTP.VerifyOTP data = new ReqOTP.VerifyOTP(Consts.getInstance().getVAEncrypt(), otp);
        Call<OTPResponse<String>> call = api.verifyOtp(data);

        call.enqueue(new Callback<OTPResponse<String>>() {
            @Override
            public void onResponse(Call<OTPResponse<String>> call, Response<OTPResponse<String>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getSuccessCode() == 1) {
                            actionCallback.onSuccess(response.body());
                        } else {
                            actionCallback.onFailed(response.body().getMessage());
                        }
                    }
                } else {
                    actionCallback.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<OTPResponse<String>> call, Throwable t) {
                actionCallback.onFailed(t.getMessage());
            }
        });
    }

    @Override
    public void OnBadRequest(String message) {
        actionCallback.onFailed(message);
    }

    @Override
    public void OnUnauthorized(String message) {
        actionCallback.onFailed(message);
    }

    public interface APICallback {
        void onSuccess(Object data);
        void onFailed(String message);
    }

    public enum OTPVIA {
        EMAIL,
        SMS
    }
}
