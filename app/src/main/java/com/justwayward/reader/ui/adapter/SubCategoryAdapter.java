package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BooksByCats;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class SubCategoryAdapter extends EasyRVAdapter<BooksByCats.BooksBean> {
    private OnRvItemClickListener itemClickListener;

    public SubCategoryAdapter(Context context, List<BooksByCats.BooksBean> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_sub_category_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BooksByCats.BooksBean item) {
        ImageView ivRecommendCover = holder.getView(R.id.ivSubCateCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.cover)
                .placeholder(R.drawable.cover_default)
                .into(ivRecommendCover);

        holder.setText(R.id.tvSubCateTitle, item.title)
                .setText(R.id.tvSubCateAuthor, item.author + " | " + item.majorCate)
                .setText(R.id.tvSubCateShort, item.shortIntro)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.category_book_msg), item.latelyFollower, item.retentionRatio));

        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }
}
