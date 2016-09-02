package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.DiscussionList;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.LogUtils;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.justwayward.reader.utils.ScreenUtils;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                Date date = format.parse(item.created.replaceAll("T", " ").replaceAll("Z", ""));
                viewHolder.setText(R.id.tvTime, RelativeDateFormat.format(date));
            }
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
    }
}
