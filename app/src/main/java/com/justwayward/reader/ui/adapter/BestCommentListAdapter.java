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
package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/2.
 */
public class BestCommentListAdapter extends EasyRVAdapter<CommentList.CommentsBean> {

    private OnRvItemClickListener listener;

    public BestCommentListAdapter(Context context, List<CommentList.CommentsBean> list) {
        super(context, list, R.layout.item_comment_best_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final CommentList.CommentsBean item) {viewHolder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar, R.drawable.avatar_default)
                .setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvContent, item.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                .setText(R.id.tvLikeCount, String.format(mContext.getString(R.string.comment_like_count), item.likeCount));

        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
    }

    public void setOnItemClickListener(OnRvItemClickListener listener){
        this.listener = listener;
    }
}
