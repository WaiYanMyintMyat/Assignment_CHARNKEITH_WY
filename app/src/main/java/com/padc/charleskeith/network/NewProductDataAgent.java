package com.padc.charleskeith.network;

public interface NewProductDataAgent {
    void loadNewProductsList(int page,String accessToken,boolean isForceRefresh);
}
