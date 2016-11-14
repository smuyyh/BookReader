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
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.utils.ScreenUtils;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class BookDiscussionAdapter extends RecyclerArrayAdapter<DiscussionList.PostsBean> {


    public BookDiscussionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<DiscussionList.PostsBean>(parent, R.layout.item_community_book_discussion_list) {
            @Override
            public void setData(DiscussionList.PostsBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvHelpfulYes, item.commentCount + "")
                        .setText(R.id.tvLikeCount, item.likeCount + "");

                try {
                    TextView textView = holder.getView(R.id.tvHelpfulYes);
                    if (item.type.equals("vote")) {
                        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_notif_vote);
                        drawable.setBounds(0, 0, ScreenUtils.dpToPxInt(15), ScreenUtils.dpToPxInt(15));
                        textView.setCompoundDrawables(drawable, null, null, null);
                    } else {
                        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_notif_post);
                        drawable.setBounds(0, 0, ScreenUtils.dpToPxInt(15), ScreenUtils.dpToPxInt(15));
                        textView.setCompoundDrawables(drawable, null, null, null);
                    }

                    if (TextUtils.equals(item.state, "hot")) {
                        holder.setVisible(R.id.tvHot, true);
                        holder.setVisible(R.id.tvTime, false);
                        holder.setVisible(R.id.tvDistillate, false);
                    } else if (TextUtils.equals(item.state, "distillate")) {
                        holder.setVisible(R.id.tvDistillate, true);
                        holder.setVisible(R.id.tvHot, false);
                        holder.setVisible(R.id.tvTime, false);
                    } else {
                        holder.setVisible(R.id.tvTime, true);
                        holder.setVisible(R.id.tvHot, false);
                        holder.setVisible(R.id.tvDistillate, false);
                        holder.setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created));
                    }
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
            }
        };
    }
}
