package com.bri.ojt.Network;

import com.bri.ojt.Model.GetVAbyGroup;
import com.bri.ojt.Model.Request.FCMRequest;
import com.bri.ojt.Model.Request.GetVAbyVA;
import com.bri.ojt.Model.Request.ReqMutasi;
import com.bri.ojt.Model.Request.ReqOTP;
import com.bri.ojt.Model.Request.ReqTarikTunai;
import com.bri.ojt.Model.Request.ReqTransferExternal;
import com.bri.ojt.Model.Request.ReqTransferInternal;
import com.bri.ojt.Model.Response.AccInfoExternalResponse;
import com.bri.ojt.Model.Response.AccInfoInternalResponseCashcard;
import com.bri.ojt.Model.Response.BankListResponse;
import com.bri.ojt.Model.Response.FCMResponse;
import com.bri.ojt.Model.Response.GetTokenResponse;
import com.bri.ojt.Model.Response.LocationResponse;
import com.bri.ojt.Model.Response.MutasiVAResponse;
import com.bri.ojt.Model.Response.OTPResponse;
import com.bri.ojt.Model.Response.TarikTunaiResponse;
import com.bri.ojt.Model.Response.TransferExternalResponse;
import com.bri.ojt.Model.Response.TransferInternalResponse;
import com.bri.ojt.Model.Response.VAbyGroupResponseCashcard;
import com.bri.ojt.Util.Consts;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("oauth/client_credential/accesstoken?grant_type=client_credentials")
    Call<GetTokenResponse> getAccessToken(@Field("client_id") String clientId, @Field("client_secret") String clientSecret);

    @POST(Consts.PATH_VA_BY_GROUP)
    Call<VAbyGroupResponseCashcard> getVAbyGroup(@HeaderMap Map<String, String> headers, @Body GetVAbyGroup params);

    @POST(Consts.PATH_VA_BY_VA)
    Call<VAbyGroupResponseCashcard> getVAbyVA(@HeaderMap Map<String, String> headers, @Body GetVAbyVA params);

    @GET(Consts.PATH_ACC_INFO_INTERNAL)
    Call<AccInfoInternalResponseCashcard> getAccInfoInternal(@HeaderMap Map<String, String> headers, @Query("sourceAccount") String sourceAcc, @Query("beneficiaryAccount") String beneficiaryAcc);

    @POST(Consts.PATH_TRANSFER_INTERNAL)
    Call<TransferInternalResponse> reqTransferInternal(@HeaderMap Map<String, String> headers, @Body ReqTransferInternal params);

    @POST(Consts.PATH_MUTASI)
    Call<MutasiVAResponse> reqMutasi(@HeaderMap Map<String, String> headers, @Body ReqMutasi params);

    @POST(Consts.PATH_OTP_ECHANNEL)
    Call<TarikTunaiResponse> reqOTPEchannel(@HeaderMap Map<String, String> headers, @Body ReqTarikTunai params);

    @POST(Consts.PATH_OTP_TELLER)
    Call<TarikTunaiResponse> reqOTPTeller(@HeaderMap Map<String, String> headers, @Body ReqTarikTunai params);

    @GET(Consts.PATH_LIST_OTHER_BANK)
    Call<BankListResponse> getBankList(@HeaderMap Map<String, String> headers);

    @GET(Consts.PATH_ACC_INFO_EXTERNAL)
    Call<AccInfoExternalResponse> getAccInfoExternal(@HeaderMap Map<String, String> headers, @Query("bankCode") String bankCode, @Query("beneficiaryAccount") String beneficiaryAcc);

    @POST(Consts.PATH_TRANSFER_EXTERNAL)
    Call<TransferExternalResponse> reqTransferExternal(@HeaderMap Map<String, String> headers, @Body ReqTransferExternal params);

    @GET(Consts.PATH_LOCATION + "/{type}/{radius}/{latitude}/{longitude}")
    Call<LocationResponse> getLocation(@HeaderMap Map<String, String> headers, @Path("type") String type, @Path("radius") String radius, @Path("latitude") String latitude, @Path("longitude") String longitude);

    @POST("fcm/send")
    Call<FCMResponse> sendNotification(@HeaderMap Map<String, String> headers, @Body FCMRequest data);

    @Headers("Content-Type: application/json")
    @POST("api/cashcard/reqotpsms")
    Call<OTPResponse<String>> reqOtpSms(@Body ReqOTP data);

    @Headers("Content-Type: application/json")
    @POST("api/cashcard/reqotpemail")
    Call<OTPResponse<String>> reqOtpEmail(@Body ReqOTP data);

    @Headers("Content-Type: application/json")
    @POST("api/cashcard/verifyotp")
    Call<OTPResponse<String>> verifyOtp(@Body ReqOTP.VerifyOTP data);
}
