package com.bri.ojt.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTokenResponse {
    @SerializedName("refresh_token_expires_in")
    private String refreshTokenExpiresIn;
    @SerializedName("api_product_list")
    private String apiProductList;
    @SerializedName("api_product_list_json")
    private List<String> apiProductListJson;
    @SerializedName("organization_name")
    private String organizationName;
    @SerializedName("developer.email")
    private String developerEmail;
    @SerializedName("issued_at")
    private String issuedAt;
    @SerializedName("access_token")
    private String accessToken;

    public GetTokenResponse() {
    }

    public String getRefreshTokenExpiresIn() {
        return refreshTokenExpiresIn;
    }

    public String getApiProductList() {
        return apiProductList;
    }

    public List<String> getApiProductListJson() {
        return apiProductListJson;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getDeveloperEmail() {
        return developerEmail;
    }

    public String getIssuedAt() {
        return issuedAt;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
