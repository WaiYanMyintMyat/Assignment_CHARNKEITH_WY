package com.padc.charleskeith.network.response;

import com.google.gson.annotations.SerializedName;
import com.padc.charleskeith.data.vos.NewProductVO;

import java.util.List;

public class GetNewProductResponse {
    //Api Specific
    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("apiVersion")
    private String apiVersion;

    @SerializedName("page")
    private String page;

    @SerializedName("newProducts")
    private List<NewProductVO> newProductList;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getPage() {
        return page;
    }

    public List<NewProductVO> getNewProductList() {
        return newProductList;
    }


    public boolean isRsponseOK() {
        return (code==200 && newProductList != null);//short term Take Note...if else ma use buu!!!
    }
}
