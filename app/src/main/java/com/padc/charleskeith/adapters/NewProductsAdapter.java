package com.padc.charleskeith.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.charleskeith.R;
import com.padc.charleskeith.data.vos.NewProductVO;
import com.padc.charleskeith.delegates.NewProductDelegate;
import com.padc.charleskeith.viewholders.NewProductViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phyo Thiha on 6/28/18.
 */
public class NewProductsAdapter extends RecyclerView.Adapter<NewProductViewHolder> {

    private NewProductDelegate mDelegate;
    private List<NewProductVO> mnewProductList;

    public NewProductsAdapter(NewProductDelegate delegate){
            mDelegate = delegate;
            mnewProductList=new ArrayList<>();
    }


    @NonNull
    @Override
    public NewProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_item,parent,false);
        return new NewProductViewHolder(view,mDelegate);
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductViewHolder holder, int position) {
        holder.setNewProductData(mnewProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mnewProductList.size();
    }

    public void setNewProductList(List<NewProductVO> newProductList){
        mnewProductList=newProductList;
        notifyDataSetChanged();
    }

    public void appendNewProductList(List<NewProductVO> newProductList){
        mnewProductList.addAll(newProductList);
        notifyDataSetChanged();
    }
}
