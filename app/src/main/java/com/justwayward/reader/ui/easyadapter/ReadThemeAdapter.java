package com.justwayward.reader.ui.easyadapter;

import android.content.Context;

import com.justwayward.reader.R;
import com.justwayward.reader.bean.support.ReadTheme;
import com.justwayward.reader.manager.ThemeManager;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/9/23.
 */
public class ReadThemeAdapter extends EasyLVAdapter<ReadTheme> {

    public ReadThemeAdapter(Context context, List<ReadTheme> list) {
        super(context, list, R.layout.item_read_theme);
    }

    @Override
    public void convert(EasyLVHolder holder, int position, ReadTheme readTheme) {
        if (readTheme != null) {
            ThemeManager.setReaderTheme(readTheme.theme, holder.getView(R.id.ivThemeBg));
            if (readTheme.selected) {
                holder.setVisible(R.id.ivSelected, true);
            } else {
                holder.setVisible(R.id.ivSelected, false);
            }
        }
    }
}
