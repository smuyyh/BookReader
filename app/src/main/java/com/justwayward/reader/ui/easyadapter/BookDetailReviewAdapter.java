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
import com.justwayward.reader.bean.HotReview;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.view.XLHRatingBar;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookDetailReviewAdapter extends RecyclerArrayAdapter<HotReview.Reviews> {


    public BookDetailReviewAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<HotReview.Reviews>(parent, R.layout.item_book_detai_hot_review_list) {
            @Override
            public void setData(HotReview.Reviews item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                                .book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvContent, String.valueOf(item.content))
                        .setText(R.id.tvHelpfulYes, String.valueOf(item.helpful.yes));
                holder.setVisible(R.id.tvTime, true);
                XLHRatingBar ratingBar = holder.getView(R.id.rating);
                ratingBar.setCountSelected(item.rating);
            }
        };
    }
}
