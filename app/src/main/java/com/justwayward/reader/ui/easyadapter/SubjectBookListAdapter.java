package com.justwayward.reader.ui.easyadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author yuyh.
 * @date 16/9/3.
 */
public class SubjectBookListAdapter extends RecyclerArrayAdapter<BookLists.BookListsBean> {
    public SubjectBookListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BookLists.BookListsBean>(parent, R.layout.item_sub_category_list) {
            @Override
            public void setData(BookLists.BookListsBean item) {
                super.setData(item);
                holder.setImageUrl(R.id.ivSubCateCover, Constant.IMG_BASE_URL + item.cover, R.drawable.cover_default)
                        .setText(R.id.tvSubCateTitle, item.title)
                        .setText(R.id.tvSubCateAuthor, item.author)
                        .setText(R.id.tvSubCateShort, item.desc)
                        .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.subject_book_msg), item.bookCount, item.collectorCount));
            }
        };
    }
}
