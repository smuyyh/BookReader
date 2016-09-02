package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.CommentList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/2.
 */
public class CommentListAdapter extends EasyRVAdapter<CommentList.CommentsBean> {

    private OnRvItemClickListener listener;

    public CommentListAdapter(Context context, List<CommentList.CommentsBean> list) {
        super(context, list, R.layout.item_comment);
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final CommentList.CommentsBean item) {

        ImageView ivCover = viewHolder.getView(R.id.ivBookCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.author.avatar).placeholder(R.drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivCover);

        viewHolder.setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvContent, item.content)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string.book_detail_user_lv), item.author.lv))
                .setText(R.id.tvFloor, String.format(mContext.getString(R.string.comment_floor), item.floor))
                .setText(R.id.tvTime, RelativeDateFormat.format(item.created));

        if(item.replyTo==null){
            viewHolder.setVisible(R.id.tvReplyNickName, false);
            viewHolder.setVisible(R.id.tvReplyFloor, false);
        }else{
            viewHolder.setText(R.id.tvReplyNickName, String.format(mContext.getString(R.string.comment_reply_nickname), item.replyTo.author.nickname))
                    .setText(R.id.tvReplyFloor, String.format(mContext.getString(R.string.comment_reply_floor), item.replyTo.floor));
            viewHolder.setVisible(R.id.tvReplyNickName, true);
            viewHolder.setVisible(R.id.tvReplyFloor, true);
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
