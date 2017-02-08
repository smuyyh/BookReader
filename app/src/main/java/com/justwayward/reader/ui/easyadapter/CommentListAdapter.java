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
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.manager.SettingManager;
import com.justwayward.reader.utils.FormatUtils;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * 帖子 评论、回复
 *
 * @author lfh.
 * @date 16/9/3.
 */
public class CommentListAdapter extends RecyclerArrayAdapter<CommentList.CommentsBean> {

    public CommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<CommentList.CommentsBean>(parent, R.layout.item_comment_list) {
            @Override
            public void setData(CommentList.CommentsBean item) {
                if (!SettingManager.getInstance().isNoneCover()) {
                    holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,
                            R.drawable.avatar_default);
                } else {
                    holder.setImageResource(R.id.ivBookCover, R.drawable.avatar_default);
                }

                holder.setText(R.id.tvBookTitle, item.author.nickname)
                        .setText(R.id.tvContent, item.content)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                        .setText(R.id.tvTime, FormatUtils.getDescriptionTimeFromDateString(item.created));

                if (item.replyTo == null) {
                    holder.setVisible(R.id.tvReplyNickName, false);
                    holder.setVisible(R.id.tvReplyFloor, false);
                } else {
                    holder.setText(R.id.tvReplyNickName, String.format(mContext.getString(R.string.comment_reply_nickname), item.replyTo.author.nickname))
                            .setText(R.id.tvReplyFloor, String.format(mContext.getString(R.string.comment_reply_floor), item.replyTo.floor));
                    holder.setVisible(R.id.tvReplyNickName, true);
                    holder.setVisible(R.id.tvReplyFloor, true);
                }
            }
        };
    }
}
