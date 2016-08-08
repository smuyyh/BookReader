package com.justwayward.reader.ui.adapter;

import android.content.Context;

import com.justwayward.reader.R;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/8/8.
 */
public class BookReadPageAdapter extends EasyLVAdapter<String> {

    public BookReadPageAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_book_read_page);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String s) {
        holder.setText(R.id.tvBookReadContent, s);
    }
}
