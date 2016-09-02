package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.HelpList;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.justwayward.reader.utils.GlideCircleTransform;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class CommunityBookHelpAdapter extends EasyRVAdapter<HelpList.HelpsBean> {

    private OnRvItemClickListener listener;

    public CommunityBookHelpAdapter(Context context, List<HelpList.HelpsBean> list) {
        super(context, list, R.layout.item_community_book_help_list);
    }

    @Override
    protected void onBindData(final EasyRVHolder viewHolder, final int position, final HelpList
            .HelpsBean item) {

        ImageView ivCover = viewHolder.getView(R.id.ivBookCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.author.avatar).placeholder(R
                .drawable.avatar_default)
                .transform(new GlideCircleTransform(mContext))
                .into(ivCover);

        viewHolder.setText(R.id.tvBookTitle, item.author.nickname)
                .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                        .book_detail_user_lv), item.author.lv))
                .setText(R.id.tvTitle, item.title)
                .setText(R.id.tvHelpfulYes, item.commentCount + "");

        if (TextUtils.equals(item.state, "hot")) {
            viewHolder.setVisible(R.id.tvHot, true);
            viewHolder.setVisible(R.id.tvTime, false);
            viewHolder.setVisible(R.id.tvDistillate, false);
        } else if(TextUtils.equals(item.state, "distillate")){
            viewHolder.setVisible(R.id.tvDistillate, true);
            viewHolder.setVisible(R.id.tvHot, false);
            viewHolder.setVisible(R.id.tvTime, false);
        }else {
            viewHolder.setVisible(R.id.tvTime, true);
            viewHolder.setVisible(R.id.tvHot, false);
            viewHolder.setVisible(R.id.tvDistillate, false);
            viewHolder.setText(R.id.tvTime, RelativeDateFormat.format(item.created));
        }


        viewHolder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onItemClick(viewHolder.getItemView(), position, item);
            }
        });
    }

    public void setOnItemClickListener(OnRvItemClickListener listener) {
        this.listener = listener;
    }
}
