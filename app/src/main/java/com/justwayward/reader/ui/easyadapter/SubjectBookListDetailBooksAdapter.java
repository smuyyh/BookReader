/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.easyadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.BookListDetail;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author yuyh.
 * @date 16/9/4.
 */
public class SubjectBookListDetailBooksAdapter extends RecyclerArrayAdapter<BookListDetail.BookListBean.BooksBean> {

    public SubjectBookListDetailBooksAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<BookListDetail.BookListBean.BooksBean>(parent, R.layout.item_subject_book_list_detail) {
            @Override
            public void setData(BookListDetail.BookListBean.BooksBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.getBook().getCover(),
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.cover_default);
                }

                holder.setText(R.id.tvBookListTitle, item.getBook().getTitle())
                        .setText(R.id.tvBookAuthor, item.getBook().getAuthor())
                        .setText(R.id.tvBookLatelyFollower, String.format(mContext.getResources().getString(R.string.subject_book_list_detail_book_lately_follower),
                                item.getBook().getLatelyFollower()))
                        .setText(R.id.tvBookWordCount, String.format(mContext.getResources().getString(R.string.subject_book_list_detail_book_word_count),
                                item.getBook().getWordCount() / 10000))
                        .setText(R.id.tvBookDetail, item.getBook().getLongIntro());
            }
        };
    }
}
