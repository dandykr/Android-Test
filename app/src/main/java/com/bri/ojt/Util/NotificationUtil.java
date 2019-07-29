package com.bri.ojt.Util;

import com.bri.ojt.Model.Request.FCMRequest;
import com.bri.ojt.Model.Response.FCMResponse;
import com.bri.ojt.Network.API;
import com.bri.ojt.Network.ResponseInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotificationUtil implements ResponseInterface {

    private NotificationInterface actionCallback;

    public NotificationUtil(NotificationInterface actionCallback) {
        this.actionCallback = actionCallback;
    }

    public void sendNotification(String toDeviceId, String title, String body) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API api = retrofit.create(API.class);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "key=" + Consts.getInstance().getFCMKey());
        headers.put("Content-Type", "application/json");

        FCMRequest fcmRequest = new FCMRequest();
        fcmRequest.setToDeviceId(toDeviceId);
        fcmRequest.setDataPayload(new FCMRequest.DataPayload(title,body));
        fcmRequest.setPriority("HIGH");

        Call<FCMResponse> call = api.sendNotification(headers, fcmRequest);

        call.enqueue(new Callback<FCMResponse>() {
            @Override
            public void onResponse(Call<FCMResponse> call, Response<FCMResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getSuccess() == 1) {
                            actionCallback.onSuccess();
                        }
                    }
                } else {
                    actionCallback.onFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<FCMResponse> call, Throwable t) {
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

    public interface NotificationInterface {
        void onSuccess();
        void onFailed(String message);
    }
}
