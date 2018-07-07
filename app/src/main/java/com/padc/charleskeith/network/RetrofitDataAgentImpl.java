package com.padc.charleskeith.network;

import com.padc.charleskeith.events.ApiErrorEvent;
import com.padc.charleskeith.events.SuccessForceRefreshGetNewProductEvent;
import com.padc.charleskeith.events.SuccessGetNewProductEvent;
import com.padc.charleskeith.network.response.GetNewProductResponse;
import com.padc.charleskeith.utils.NewProductConstants;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitDataAgentImpl implements NewProductDataAgent {
    private static RetrofitDataAgentImpl objInstance;
    private NewProductApi mTheApi;

    private RetrofitDataAgentImpl() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder() //1.
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewProductConstants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mTheApi=retrofit.create(NewProductApi.class);
    }

    public static RetrofitDataAgentImpl getObjInstance() {
        if(objInstance==null){
            objInstance=new RetrofitDataAgentImpl();
        }
        return objInstance;
    }

    @Override
    public void loadNewProductsList(int page, String accessToken,boolean isForceRefresh) {
        Call<GetNewProductResponse> call=mTheApi.loadNewProductList(accessToken,page);
        call.enqueue(new Callback<GetNewProductResponse>() {
            @Override
            public void onResponse(Call<GetNewProductResponse> call, Response<GetNewProductResponse> response) {
                GetNewProductResponse newProductResponse=response.body();
                if (newProductResponse != null && newProductResponse.isRsponseOK()) {
                    if(isForceRefresh){
                        SuccessForceRefreshGetNewProductEvent event=new SuccessForceRefreshGetNewProductEvent(newProductResponse.getNewProductList());
                        EventBus.getDefault().post(event);
                    }else{
                        SuccessGetNewProductEvent event = new SuccessGetNewProductEvent(newProductResponse.getNewProductList());
                        EventBus.getDefault().post(event);
                    }
                } else {
                    if (newProductResponse == null) {
                        ApiErrorEvent errorEvent = new ApiErrorEvent("Empty Response in network call.");
                        EventBus.getDefault().post(errorEvent);
                    } else {
                        ApiErrorEvent errorEvent = new ApiErrorEvent(newProductResponse.getMessage());//eg.Access forbidden
                        EventBus.getDefault().post(errorEvent);
                    }
                }
            }

            @Override
            public void onFailure(Call<GetNewProductResponse> call, Throwable t) {
                //Server Fail,Api Crash,etc...
                ApiErrorEvent errorEvent = new ApiErrorEvent(t.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}
