package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.view.View;

import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class RecommendAdapter extends EasyRVAdapter<Recommend.RecommendBooks> {

    private OnRvItemClickListener itemClickListener;

    public RecommendAdapter(Context context, List<Recommend.RecommendBooks> list, OnRvItemClickListener listener) {
        super(context, list, R.layout.item_recommend_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final Recommend.RecommendBooks item) {

        holder.setRoundImageUrl(R.id.ivRecommendCover, Constant.IMG_BASE_URL + item.cover, R.drawable.cover_default)
                .setText(R.id.tvRecommendTitle, item.title)
                .setText(R.id.tvRecommendShort, item.lastChapter);

        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }
}
