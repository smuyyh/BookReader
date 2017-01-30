/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.justwayward.reader.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.justwayward.reader.R;
import com.justwayward.reader.bean.BookMixAToc;
import com.justwayward.reader.utils.FileUtils;
import com.yuyh.easyadapter.abslistview.EasyLVAdapter;
import com.yuyh.easyadapter.abslistview.EasyLVHolder;

import java.util.List;

/**
 * @author lfh.
 * @date 16/8/11.
 */
public class TocListAdapter extends EasyLVAdapter<BookMixAToc.mixToc.Chapters> {

    private int currentChapter;
    private String bookId;

    private boolean isEpub = false;

    public TocListAdapter(Context context, List<BookMixAToc.mixToc.Chapters> list, String bookId, int currentChapter) {
        super(context, list, R.layout.item_book_read_toc_list);
        this.currentChapter = currentChapter;
        this.bookId = bookId;
    }

    @Override
    public void convert(EasyLVHolder holder, int position, BookMixAToc.mixToc.Chapters chapters) {
        TextView tvTocItem = holder.getView(R.id.tvTocItem);
        tvTocItem.setText(chapters.title);
        Drawable drawable;
        if (currentChapter == position + 1) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_red));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_activated);
        } else if (isEpub || FileUtils.getChapterFile(bookId, position + 1).length() > 10) {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_download);
        } else {
            tvTocItem.setTextColor(ContextCompat.getColor(mContext, R.color.light_black));
            drawable = ContextCompat.getDrawable(mContext, R.drawable.ic_toc_item_normal);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvTocItem.setCompoundDrawables(drawable, null, null, null);
    }

    public void setCurrentChapter(int chapter) {
        currentChapter = chapter;
        notifyDataSetChanged();
    }

    public void setEpub(boolean isEpub) {
        this.isEpub = isEpub;
    }
}
