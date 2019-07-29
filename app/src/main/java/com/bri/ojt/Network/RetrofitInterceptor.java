package com.bri.ojt.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RetrofitInterceptor implements Interceptor {

    private ResponseInterface responseInterface;

    public RetrofitInterceptor(ResponseInterface responseInterface) {
        this.responseInterface = responseInterface;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();


        Response response = chain.proceed(originalRequest);


        if (response.body() != null) {
            String formatted = response.body().string().trim().replaceAll("^\"+|\"+$","");
            MediaType contentType = response.body().contentType();
            ResponseBody body = ResponseBody.create(contentType, formatted);

            if (response.code() == 400)
                responseInterface.OnBadRequest(body.string());
            if (response.code() == 401)
                responseInterface.OnUnauthorized(body.string());

            return response.newBuilder().body(body).build();
        }


        return response;

    }
}
