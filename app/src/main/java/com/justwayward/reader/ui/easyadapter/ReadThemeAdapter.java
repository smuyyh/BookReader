package com.justwayward.reader.ui.easyadapter;

import android.content.Context;

import com.justwayward.reader.R;
import com.justwayward.reader.bean.support.ReadTheme;
import com.justwayward.reader.manager.ThemeManager;
import com.justwayward.reader.utils.LogUtils;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/9/23.
 */
public class ReadThemeAdapter extends EasyLVAdapter<ReadTheme> {

    private int selected = 0;

    public ReadThemeAdapter(Context context, List<ReadTheme> list, int selected) {
        super(context, list, R.layout.item_read_theme);
        this.selected = selected;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, ReadTheme readTheme) {
        if (readTheme != null) {
            ThemeManager.setReaderTheme(readTheme.theme, holder.getView(R.id.ivThemeBg));
            if (selected == position) {
                holder.setVisible(R.id.ivSelected, true);
            } else {
                holder.setVisible(R.id.ivSelected, false);
            }
        }
    }

    public void select(int position) {
        selected = position;
        LogUtils.i("curtheme=" + selected);
        notifyDataSetChanged();
    }
}
