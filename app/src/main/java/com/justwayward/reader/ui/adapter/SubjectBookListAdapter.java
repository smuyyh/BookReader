package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class SubjectBookListAdapter extends EasyRVAdapter<BookLists.BookListsBean> {
    private OnRvItemClickListener itemClickListener;

    public SubjectBookListAdapter(Context context, List<BookLists.BookListsBean> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_sub_category_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BookLists.BookListsBean item) {
        ImageView ivRecommendCover = holder.getView(R.id.ivSubCateCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.cover)
                .placeholder(R.drawable.cover_default)
                .into(ivRecommendCover);

        holder.setText(R.id.tvSubCateTitle, item.title)
                .setText(R.id.tvSubCateAuthor, item.author)
                .setText(R.id.tvSubCateShort, item.desc)
                .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.subject_book_msg), item.bookCount, item.collectorCount));

        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }
}
