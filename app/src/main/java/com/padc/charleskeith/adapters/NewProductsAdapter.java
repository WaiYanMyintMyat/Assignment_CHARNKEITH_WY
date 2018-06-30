package com.padc.charleskeith.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padc.charleskeith.R;
import com.padc.charleskeith.delegates.NewProductDelegate;
import com.padc.charleskeith.viewholders.NewProductViewHolder;

/**
 * Created by Phyo Thiha on 6/28/18.
 */
public class NewProductsAdapter extends RecyclerView.Adapter {

    private NewProductDelegate mDelegate;

    public NewProductsAdapter(NewProductDelegate delegate){
            mDelegate = delegate;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.view_holder_item,parent,false);
        return new NewProductViewHolder(view,mDelegate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
