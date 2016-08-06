package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.SearchDetail;
import com.justwayward.reader.common.OnRvItemClickListener;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/8/6.
 */
public class SearchResultAdapter extends EasyRVAdapter<SearchDetail.SearchBooks> {
    private OnRvItemClickListener itemClickListener;

    public SearchResultAdapter(Context context, List<SearchDetail.SearchBooks> list,
                               OnRvItemClickListener listener) {
        super(context, list, R.layout.item_search_result_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(final EasyRVHolder holder, final int position, final SearchDetail.SearchBooks
            item) {
        ImageView ivCover = holder.getView(R.id.ivBookCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.cover).placeholder(R.drawable
                .cover_default).into(ivCover);

        holder.setText(R.id.tvBookTitle, item.title)
                .setText(R.id.tvLatelyFollower, String.format(mContext.getString(R.string
                        .search_result_lately_follower), item.latelyFollower))
                .setText(R.id.tvRetentionRatio, (TextUtils.isEmpty(item.retentionRatio) ? String
                        .format(mContext.getString(R.string.search_result_retention_ratio),
                                "0") :
                        String.format(mContext.getString(R.string.search_result_retention_ratio),
                                item.retentionRatio)))
                .setText(R.id.tvAuthor, String.format(mContext.getString(R.string
                        .search_result_author), item.author));
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(holder.getItemView(), position, item);
            }
        });
    }

}
