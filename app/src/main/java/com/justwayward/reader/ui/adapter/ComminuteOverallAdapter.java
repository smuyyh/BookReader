package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class ComminuteOverallAdapter extends EasyRVAdapter<DiscussionList.PostsBean> {

    public ComminuteOverallAdapter(Context context, List<DiscussionList.PostsBean> list) {
        super(context, list, R.layout.item_community_overall_list);
    }

    @Override
    protected void onBindData(EasyRVHolder viewHolder, int position, DiscussionList.PostsBean item) {

        ImageView ivCover = viewHolder.getView(R.id.ivAvatar);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.author.avatar).placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivCover);

        viewHolder.setText(R.id.tvNickName, item.author.nickname)
                .setText(R.id.tvLv, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvTitle, item.title)
                .setText(R.id.tvPostCount, item.commentCount + "")
                .setText(R.id.tvLikeCount, item.likeCount + "");
    }
}
