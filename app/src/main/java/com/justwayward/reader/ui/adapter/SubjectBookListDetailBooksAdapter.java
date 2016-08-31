package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookListDetail;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/8/31.
 */
public class SubjectBookListDetailBooksAdapter extends EasyRVAdapter<BookListDetail.BookListBean.BooksBean
        > {
    private OnRvItemClickListener itemClickListener;

    public SubjectBookListDetailBooksAdapter(Context context, List<BookListDetail
            .BookListBean.BooksBean> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_subject_book_list_detail);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final BookListDetail
            .BookListBean.BooksBean item) {
        ImageView ivBookCover = holder.getView(R.id.ivBookCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.getBook().getCover())
                .placeholder(R.drawable.cover_default)
                .into(ivBookCover);

        holder.setText(R.id.tvBookListTitle, item.getBook().getTitle())
                .setText(R.id.tvBookAuthor, item.getBook().getAuthor())
                .setText(R.id.tvBookLatelyFollower, String.format(mContext.getResources()
                        .getString(R.string.subject_book_list_detail_book_lately_follower), item
                        .getBook().getLatelyFollower()))
                .setText(R.id.tvBookWordCount, String.format(mContext.getResources().getString(R
                        .string.subject_book_list_detail_book_word_count), item.getBook().getWordCount()/10000));

        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null)
                    itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }
}
