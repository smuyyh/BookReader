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
import com.justwayward.reader.bean.BookLists;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * 主题书单
 *
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
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setRoundImageUrl(R.id.ivSubCateCover, Constant.IMG_BASE_URL + item.cover,
                            R.drawable.cover_default);
                } else {
                    holder.setImageResource(R.id.ivSubCateCover, R.drawable.cover_default);
                }

                holder.setText(R.id.tvSubCateTitle, item.title)
                        .setText(R.id.tvSubCateAuthor, item.author)
                        .setText(R.id.tvSubCateShort, item.desc)
                        .setText(R.id.tvSubCateMsg, String.format(mContext.getResources().getString(R.string.subject_book_msg), item.bookCount, item.collectorCount));
            }
        };
    }
}
