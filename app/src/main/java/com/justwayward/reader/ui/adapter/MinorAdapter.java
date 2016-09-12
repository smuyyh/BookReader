package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.utils.ScreenUtils;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/8/6.
 */
public class MinorAdapter extends EasyLVAdapter<String> {

    private int current = 0;

    public MinorAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_minor_list);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, String s) {
        holder.setText(R.id.tvMinorItem, s);

        if (current == position) {
            holder.setVisible(R.id.ivMinorChecked, true);
        } else {
            holder.setVisible(R.id.ivMinorChecked, false);
        }

        if (position != 0) { // 子项右移
            TextView textView = holder.getView(R.id.tvMinorItem);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            params.leftMargin = ScreenUtils.dpToPxInt(25);
            textView.setLayoutParams(params);
        }
    }

    public void setChecked(int position) {
        current = position;
        notifyDataSetChanged();
    }
}
