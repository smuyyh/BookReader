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
package com.justwayward.reader.view.recyclerview.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;


public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private int halfSpace;
    private int headerCount = -1;
    private int footerCount = Integer.MAX_VALUE;
    private boolean mPaddingEdgeSide = true;
    private boolean mPaddingStart = true;
    private boolean mPaddingHeaderFooter = false;


    public SpaceDecoration(int space) {
        this.halfSpace = space / 2;
    }

    public void setPaddingEdgeSide(boolean mPaddingEdgeSide) {
        this.mPaddingEdgeSide = mPaddingEdgeSide;
    }

    public void setPaddingStart(boolean mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
    }

    public void setPaddingHeaderFooter(boolean mPaddingHeaderFooter) {
        this.mPaddingHeaderFooter = mPaddingHeaderFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = 0;
        int orientation = 0;
        int spanIndex = 0;
        int headerCount = 0,footerCount = 0;
        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
         if (layoutManager instanceof StaggeredGridLayoutManager){
             orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
             spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
             spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        }else if (layoutManager instanceof GridLayoutManager){
             orientation = ((GridLayoutManager) layoutManager).getOrientation();
             spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
             spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        }else if (layoutManager instanceof LinearLayoutManager){
             orientation = ((LinearLayoutManager) layoutManager).getOrientation();
             spanCount = 1;
             spanIndex = 0;
        }

        /**
         * 普通Item的尺寸
         */
        if ((position>=headerCount&&position<parent.getAdapter().getItemCount()-footerCount)){
            int gravity;
            if (spanIndex == 0&&spanCount>1)gravity = Gravity.LEFT;
            else if (spanIndex == spanCount-1&&spanCount>1)gravity = Gravity.RIGHT;
            else if (spanCount == 1)gravity = Gravity.FILL_HORIZONTAL;
            else {
                gravity = Gravity.CENTER;
            }
            if (orientation == OrientationHelper.VERTICAL){
                switch (gravity){
                    case Gravity.LEFT:
                        if (mPaddingEdgeSide)
                            outRect.left = halfSpace*2;
                        outRect.right = halfSpace;
                        break;
                    case Gravity.RIGHT:
                        outRect.left = halfSpace;
                        if (mPaddingEdgeSide)
                            outRect.right = halfSpace*2;
                        break;
                    case Gravity.FILL_HORIZONTAL:
                        if (mPaddingEdgeSide){
                            outRect.left = halfSpace*2;
                            outRect.right = halfSpace*2;
                        }
                        break;
                    case Gravity.CENTER:
                        outRect.left = halfSpace;
                        outRect.right = halfSpace;
                        break;
                }
                if (position - headerCount < spanCount && mPaddingStart)outRect.top =  halfSpace*2;
                outRect.bottom = halfSpace*2;
            }else {
                switch (gravity){
                    case Gravity.LEFT:
                        if (mPaddingEdgeSide)
                            outRect.bottom = halfSpace*2;
                        outRect.top = halfSpace;
                        break;
                    case Gravity.RIGHT:
                        outRect.bottom = halfSpace;
                        if (mPaddingEdgeSide)
                            outRect.top = halfSpace*2;
                        break;
                    case Gravity.FILL_HORIZONTAL:
                        if (mPaddingEdgeSide){
                            outRect.left = halfSpace*2;
                            outRect.right = halfSpace*2;
                        }
                        break;
                    case Gravity.CENTER:
                        outRect.bottom = halfSpace;
                        outRect.top = halfSpace;
                        break;
                }
                if (position - headerCount < spanCount  && mPaddingStart)outRect.left =  halfSpace*2;
                outRect.right = halfSpace*2;
            }
        }else {//只有HeaderFooter进到这里
                if (mPaddingHeaderFooter) {//并且需要padding Header&Footer
                    if (orientation == OrientationHelper.VERTICAL){
                        if (mPaddingEdgeSide) {
                            outRect.left = halfSpace * 2;
                            outRect.right = halfSpace * 2;
                        }
                        outRect.top =  halfSpace*2;
                    }else{
                        if (mPaddingEdgeSide) {
                            outRect.top = halfSpace * 2;
                            outRect.bottom = halfSpace * 2;
                        }
                        outRect.left =  halfSpace*2;
                    }
                }
        }
    }


}