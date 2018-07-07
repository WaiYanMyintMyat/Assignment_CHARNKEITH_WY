package com.padc.charleskeith.network;

import com.padc.charleskeith.network.response.GetNewProductResponse;
import com.padc.charleskeith.utils.NewProductConstants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface NewProductApi {
    @FormUrlEncoded
    @POST(NewProductConstants.GET_NEWPRODUCTS)
    Call<GetNewProductResponse> loadNewProductList(
            @Field(NewProductConstants.PARAM_ACCESS_TOKEN) String accessToken,
            @Field(NewProductConstants.PARAM_PAGE) int page);
}
