package com.justwayward.reader.ui.easyadapter;

import android.content.Context;
import android.view.ViewGroup;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.view.recyclerview.adapter.BaseViewHolder;
import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * @author yuyh.
 * @date 2016/9/7.
 */
public class RecommendAdapter extends RecyclerArrayAdapter<Recommend.RecommendBooks> {
    public RecommendAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder<Recommend.RecommendBooks>(parent, R.layout.item_recommend_list) {
            @Override
            public void setData(Recommend.RecommendBooks item) {
                super.setData(item);
                holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.cover, R.drawable.cover_default)
                        .setText(R.id.tvRecommendTitle, item.title)
                        .setText(R.id.tvRecommendShort, item.lastChapter);
                holder.setVisible(R.id.ivTopLabel,item.isTop);
            }
        };
    }
}
