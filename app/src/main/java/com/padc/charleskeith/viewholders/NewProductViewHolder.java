package com.padc.charleskeith.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.padc.charleskeith.delegates.NewProductDelegate;

/**
 * Created by Phyo Thiha on 6/28/18.
 */
public class NewProductViewHolder extends RecyclerView.ViewHolder {
    private NewProductDelegate mDelegate;

    public NewProductViewHolder(View itemView,NewProductDelegate delegate) {
        super(itemView);
        mDelegate = delegate;
        itemView.setOnClickListener((v)->{
            mDelegate.onTapProduct();
        });
    }
}
