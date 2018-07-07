package com.padc.charleskeith.data.models;

import com.padc.charleskeith.data.vos.NewProductVO;
import com.padc.charleskeith.events.SuccessForceRefreshGetNewProductEvent;
import com.padc.charleskeith.events.SuccessGetNewProductEvent;
import com.padc.charleskeith.network.NewProductDataAgent;
import com.padc.charleskeith.network.RetrofitDataAgentImpl;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewProductModel {
    private static NewProductModel objInstance;
    private NewProductDataAgent mDataAgent;
    private int mPage;
    private static final String DUMMY_ACCESS_TOKEN = "b002c7e1a528b7cb460933fc2875e916";

    //Data Repository
    private Map<Integer, NewProductVO> mNewProductMap;

    private NewProductModel() {
        mNewProductMap = new HashMap<>();
        mDataAgent = RetrofitDataAgentImpl.getObjInstance();
        mPage = 1;
        EventBus.getDefault().register(this);
    }

    public static NewProductModel getObjInstance() {
        if (objInstance == null) {
            objInstance = new NewProductModel();
        }
        return objInstance;
    }

    public void loadNewProductList() {
        mDataAgent.loadNewProductsList(mPage, DUMMY_ACCESS_TOKEN,false);
    }

    public void forceRefreshNewProductList() {
        mPage=1;
        mDataAgent.loadNewProductsList(1,DUMMY_ACCESS_TOKEN,true);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessGetNewProduct(SuccessGetNewProductEvent event) {
        //set Data into Repository
        setDataIntoRepository(event.getNewProductList());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSuccessForceRefreshGetNewProduct(SuccessForceRefreshGetNewProductEvent event){
        setDataIntoRepository(event.getmNewProductList());
    }

    private void setDataIntoRepository(List<NewProductVO> newProductList){
        for (NewProductVO newProductVO : newProductList) {
            mNewProductMap.put(newProductVO.getProductId(),newProductVO);
        }
        mPage++;
    }


}
