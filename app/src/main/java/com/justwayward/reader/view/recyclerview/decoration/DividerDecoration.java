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

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.justwayward.reader.view.recyclerview.adapter.RecyclerArrayAdapter;


public class DividerDecoration extends RecyclerView.ItemDecoration{
    private ColorDrawable mColorDrawable;
    private int mHeight;
    private int mPaddingLeft;
    private int mPaddingRight;
    private boolean mDrawLastItem = true;
    private boolean mDrawHeaderFooter = false;

    public DividerDecoration(int color, int height) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
    }
    public DividerDecoration(int color, int height, int paddingLeft, int paddingRight) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = height;
        this.mPaddingLeft = paddingLeft;
        this.mPaddingRight = paddingRight;
    }

    public void setDrawLastItem(boolean mDrawLastItem) {
        this.mDrawLastItem = mDrawLastItem;
    }

    public void setDrawHeaderFooter(boolean mDrawHeaderFooter) {
        this.mDrawHeaderFooter = mDrawHeaderFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int orientation = 0;
        int headerCount = 0,footerCount = 0;
        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof GridLayoutManager){
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }

        if (position>=headerCount&&position<parent.getAdapter().getItemCount()-footerCount||mDrawHeaderFooter){
            if (orientation == OrientationHelper.VERTICAL){
                outRect.bottom = mHeight;
            }else {
                outRect.right = mHeight;
            }
        }
    }

    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {


        int orientation = 0;
        int headerCount = 0,footerCount = 0,dataCount;

        if (parent.getAdapter() instanceof RecyclerArrayAdapter){
            headerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getHeaderCount();
            footerCount = ((RecyclerArrayAdapter) parent.getAdapter()).getFooterCount();
            dataCount = ((RecyclerArrayAdapter) parent.getAdapter()).getCount();
        }else {
            dataCount = parent.getAdapter().getItemCount();
        }
        int dataStartPosition = headerCount;
        int dataEndPosition = headerCount+dataCount;


        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager){
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof GridLayoutManager){
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        }else if (layoutManager instanceof LinearLayoutManager){
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int start,end;
        if (orientation == OrientationHelper.VERTICAL){
            start = parent.getPaddingLeft() + mPaddingLeft;
            end = parent.getWidth() - parent.getPaddingRight() - mPaddingRight;
        }else {
            start = parent.getPaddingTop() + mPaddingLeft;
            end = parent.getHeight() - parent.getPaddingBottom() - mPaddingRight;
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (position>=dataStartPosition&&position<dataEndPosition-1//数据项除了最后一项
                    ||(position == dataEndPosition-1&&mDrawLastItem)//数据项最后一项
                    ||(!(position>=dataStartPosition&&position<dataEndPosition)&&mDrawHeaderFooter)//header&footer且可绘制
                    ){

                if (orientation == OrientationHelper.VERTICAL){
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mHeight;
                    mColorDrawable.setBounds(start,top,end,bottom);
                    mColorDrawable.draw(c);
                }else {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left + mHeight;
                    mColorDrawable.setBounds(left,start,right,end);
                    mColorDrawable.draw(c);
                }
            }
        }
  }
}