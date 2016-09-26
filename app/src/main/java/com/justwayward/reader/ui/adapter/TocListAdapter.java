package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.bean.BookToc;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/8/11.
 */
public class TocListAdapter extends EasyLVAdapter<BookToc.mixToc.Chapters> {

    private int currentChapter;

    public TocListAdapter(Context context, List<BookToc.mixToc.Chapters> list, int currentChapter) {
        super(context, list, R.layout.item_book_read_toc_list);
        this.currentChapter = currentChapter;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, BookToc.mixToc.Chapters chapters) {
        TextView tvTocItem = holder.getView(R.id.tvTocItem);
        tvTocItem.setText(chapters.title);
        if (currentChapter == position + 1) {
            tvTocItem.setSelected(true);
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_red));
        } else {
            tvTocItem.setSelected(false);
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
        }
    }

    public void setCurrentChapter(int chapter) {
        currentChapter = chapter;
        notifyDataSetChanged();
    }
}
