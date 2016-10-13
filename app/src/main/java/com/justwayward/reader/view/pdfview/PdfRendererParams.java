package com.justwayward.reader.view.pdfview;

import android.graphics.Bitmap;

public class PdfRendererParams {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;

    int width;
    int height;
    float renderQuality;
    int offScreenSize;
    Bitmap.Config config = DEFAULT_CONFIG;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getRenderQuality() {
        return renderQuality;
    }

    public void setRenderQuality(float renderQuality) {
        this.renderQuality = renderQuality;
    }

    public int getOffScreenSize() {
        return offScreenSize;
    }

    public void setOffScreenSize(int offScreenSize) {
        this.offScreenSize = offScreenSize;
    }

    public Bitmap.Config getConfig() {
        return config;
    }

    public void setConfig(Bitmap.Config config) {
        this.config = config;
    }
}
