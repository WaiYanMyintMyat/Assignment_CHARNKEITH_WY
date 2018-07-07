package com.padc.charleskeith.events;

import com.padc.charleskeith.data.vos.NewProductVO;

import java.util.List;

public class SuccessForceRefreshGetNewProductEvent {
    private List<NewProductVO> mNewProductList;

    public SuccessForceRefreshGetNewProductEvent(List<NewProductVO> newProductList) {
        mNewProductList = newProductList;
    }

    public List<NewProductVO> getmNewProductList() {
        return mNewProductList;
    }

}
