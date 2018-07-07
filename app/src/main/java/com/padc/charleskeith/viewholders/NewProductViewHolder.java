package com.padc.charleskeith.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.padc.charleskeith.R;
import com.padc.charleskeith.data.vos.NewProductVO;
import com.padc.charleskeith.delegates.NewProductDelegate;
import com.padc.charleskeith.utils.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Phyo Thiha on 6/28/18.
 */
public class NewProductViewHolder extends RecyclerView.ViewHolder {
    private NewProductDelegate mDelegate;
    private NewProductVO newProductVO;

    @BindView(R.id.iv_item_image)
    ImageView ivItemImage;

    @BindView(R.id.tv_item_name)
    TextView tvItemName;

    @BindView(R.id.rl_item)
    RelativeLayout rlItem;

    public NewProductViewHolder(View itemView,NewProductDelegate delegate) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        mDelegate = delegate;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.onTapProduct();
            }
        });
    }

    public void setNewProductData(NewProductVO newProductVO){
        this.newProductVO=newProductVO;

        GlideApp.with(ivItemImage.getContext())
                .load(newProductVO.getProductImage())
                .placeholder(R.drawable.placeholderone)
                .error(R.drawable.placeholderone)
                .into(ivItemImage);
        tvItemName.setText(newProductVO.getProductTitle());
    }
}
