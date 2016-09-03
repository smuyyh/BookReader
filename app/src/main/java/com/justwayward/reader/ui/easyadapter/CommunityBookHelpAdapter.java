package com.justwayward.reader.ui.easyadapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.HelpList;
import com.justwayward.reader.utils.RelativeDateFormat;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author lfh.
 * @date 16/9/3.
 */
public class CommunityBookHelpAdapter extends RecyclerArrayAdapter<HelpList.HelpsBean> {


    public CommunityBookHelpAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<HelpList.HelpsBean>(parent, R.layout.item_community_book_help_list) {
            @Override
            public void setData(HelpList.HelpsBean item) {
                holder.setCircleImageUrl(R.id.ivBookCover, Constant.IMG_BASE_URL + item.author.avatar,R.drawable.avatar_default)
                        .setText(R.id.tvBookType, String.format(mContext.getString(R.string
                                .book_detail_user_lv), item.author.lv))
                        .setText(R.id.tvTitle, item.title)
                        .setText(R.id.tvHelpfulYes, item.commentCount + "");

                if (TextUtils.equals(item.state, "hot")) {
                    holder.setVisible(R.id.tvHot, true);
                    holder.setVisible(R.id.tvTime, false);
                    holder.setVisible(R.id.tvDistillate, false);
                } else if(TextUtils.equals(item.state, "distillate")){
                    holder.setVisible(R.id.tvDistillate, true);
                    holder.setVisible(R.id.tvHot, false);
                    holder.setVisible(R.id.tvTime, false);
                }else {
                    holder.setVisible(R.id.tvTime, true);
                    holder.setVisible(R.id.tvHot, false);
                    holder.setVisible(R.id.tvDistillate, false);
                    holder.setText(R.id.tvTime, RelativeDateFormat.format(item.created));
                }
            }
        };
    }
}
