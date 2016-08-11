package com.justwayward.reader.ui.adapter;

import android.content.Context;

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

    public TocListAdapter(Context context, List<BookToc.mixToc.Chapters> list) {
        super(context, list, R.layout.item_book_read_toc_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, BookToc.mixToc.Chapters chapters) {
        holder.setText(R.id.tvTocItem, chapters.title);
    }

}
