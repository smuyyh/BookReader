package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.Recommend;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/5.
 */
public class RecommendAdapter extends EasyRVAdapter<Recommend.RecommendBooks> {

    public RecommendAdapter(Context context, List<Recommend.RecommendBooks> list) {
        super(context, list, R.layout.item_recommend_list);
    }

    @Override
    protected void onBindData(EasyRVHolder holder, int position, Recommend.RecommendBooks item) {
        ImageView ivRecommendCover = holder.getView(R.id.ivRecommendCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.cover).placeholder(R.drawable
                .cover_default).into(ivRecommendCover);

        holder.setText(R.id.tvRecommendTitle, item.title)
                .setText(R.id.tvRecommendShort, item.lastChapter);
    }
}
