package com.justwayward.reader.view.pdfview;

import android.graphics.Bitmap;
import android.graphics.Color;

public class SimpleBitmapPool implements BitmapContainer {

    Bitmap[] bitmaps;

    private int poolSize;

    private int width;

    private int height;

    private Bitmap.Config config;

    public SimpleBitmapPool(PdfRendererParams params) {
        this.poolSize = getPoolSize(params.getOffScreenSize());
        this.width = params.getWidth();
        this.height = params.getHeight();
        this.config = params.getConfig();
        this.bitmaps = new Bitmap[poolSize];
    }

    private int getPoolSize(int offScreenSize) {
        return (offScreenSize) * 2 + 1;
    }

    public Bitmap getBitmap(int position) {
        int index = getIndexFromPosition(position);
        if (bitmaps[index] == null) {
            createBitmapAtIndex(index);
        }

        bitmaps[index].eraseColor(Color.TRANSPARENT);

        return bitmaps[index];
    }

    @Override
    public Bitmap get(int position) {
        return getBitmap(position);
    }

    @Override
    public void remove(int position) {
        bitmaps[position].recycle();
        bitmaps[position] = null;
    }

    @Override
    public void clear() {
        recycleAll();
    }

    protected void recycleAll() {
        for (int i = 0; i < poolSize; i++) {
            if (bitmaps[i] != null) {
                bitmaps[i].recycle();
                bitmaps[i] = null;
            }
        }
    }

    protected void createBitmapAtIndex(int index) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        bitmaps[index] = bitmap;
    }

    protected int getIndexFromPosition(int position) {
        return position % poolSize;
    }
}