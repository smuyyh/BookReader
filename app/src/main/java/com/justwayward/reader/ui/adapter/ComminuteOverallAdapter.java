package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.justwayward.reader.utils.ScreenUtils;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/9/2.
 */
public class ComminuteOverallAdapter extends EasyRVAdapter<DiscussionList.PostsBean> {

    private OnRvItemClickListener listener;

    public ComminuteOverallAdapter(Context context, List<DiscussionList.PostsBean> list) {
        super(context, list, R.layout.item_community_overall_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final DiscussionList.PostsBean item) {

        ImageView ivCover = viewHolder.getView(R.id.ivAvatar);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.author.avatar).placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivCover);

        viewHolder.setText(R.id.tvNickName, item.author.nickname)
                .setText(R.id.tvLv, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvTitle, item.title)
                .setText(R.id.tvPostCount, item.commentCount + "")
                .setText(R.id.tvLikeCount, item.likeCount + "");

        try {
            TextView textView = viewHolder.getView(R.id.tvPostCount);
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
                viewHolder.setVisible(R.id.tvHot, true);
                viewHolder.setVisible(R.id.tvTime, false);
            } else {
                viewHolder.setVisible(R.id.tvHot, false);
                viewHolder.setVisible(R.id.tvTime, true);
                viewHolder.setText(R.id.tvTime, RelativeDateFormat.format(item.created));
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }

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
