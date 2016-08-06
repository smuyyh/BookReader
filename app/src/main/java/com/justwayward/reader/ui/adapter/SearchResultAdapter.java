package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.justwayward.reader.R;
import com.justwayward.reader.base.Constant;
import com.justwayward.reader.bean.SearchDetail;
import com.yuyh.easyadapter.recyclerview.EasyRVAdapter;
import com.yuyh.easyadapter.recyclerview.EasyRVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/8/6.
 */
public class SearchResultAdapter extends EasyRVAdapter<SearchDetail.SearchBooks> {
    private ItemClickListener itemClickListener;

    public SearchResultAdapter(Context context, List<SearchDetail.SearchBooks> list,
                               ItemClickListener listener) {
        super(context, list, R.layout.item_search_result_list);
        this.itemClickListener = listener;
    }

    @Override
    protected void onBindData(EasyRVHolder holder, int position, final SearchDetail.SearchBooks item) {
        ImageView ivCover = holder.getView(R.id.ivBookCover);
        Glide.with(mContext).load(Constant.IMG_BASE_URL + item.cover).into(ivCover);

        holder.setText(R.id.tvBookTitle, item.title)
                .setText(R.id.tvLatelyFollower, item.latelyFollower + "人在追 | ")
                .setText(R.id.tvRetentionRatio, (TextUtils.isEmpty(item.retentionRatio) ? "0" :
                        item.retentionRatio) + "%读者留存 | ")
                .setText(R.id.tvAuthor, item.author + "著");
        holder.setOnItemViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item);
            }
        });
    }

    public interface ItemClickListener {
        void onItemClick(SearchDetail.SearchBooks item);
    }

}
