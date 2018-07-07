package com.padc.charleskeith.events;

import com.padc.charleskeith.data.vos.NewProductVO;

import java.util.List;

public class SuccessGetNewProductEvent {
    private List<NewProductVO> newProductList;

    public SuccessGetNewProductEvent(List<NewProductVO> newProductList) {
        this.newProductList = newProductList;
    }

    public List<NewProductVO> getNewProductList() {
        return newProductList;
    }
}
