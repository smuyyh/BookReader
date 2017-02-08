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
package com.justwayward.reader.view.pdfview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.justwayward.reader.R;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class BasePDFPagerAdapter extends PagerAdapter {
    protected static final int FIRST_PAGE = 0;
    protected static final float DEFAULT_QUALITY = 2.0f;
    protected static final int DEFAULT_OFFSCREENSIZE = 1;

    String pdfPath;
    Context context;
    PdfRenderer renderer;
    BitmapContainer bitmapContainer;
    LayoutInflater inflater;

    protected float renderQuality;
    protected int offScreenSize;

    public BasePDFPagerAdapter(Context context, String pdfPath) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = DEFAULT_OFFSCREENSIZE;

        init();
    }

    /**
     * This constructor was added for those who want to customize ViewPager's offScreenSize attr
     */
    public BasePDFPagerAdapter(Context context, String pdfPath, int offScreenSize) {
        this.pdfPath = pdfPath;
        this.context = context;
        this.renderQuality = DEFAULT_QUALITY;
        this.offScreenSize = offScreenSize;

        init();
    }

    @SuppressWarnings("NewApi")
    protected void init() {
        try {
            renderer = new PdfRenderer(getSeekableFileDescriptor(pdfPath));
            inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            PdfRendererParams params = extractPdfParamsFromFirstPage(renderer, renderQuality);
            bitmapContainer = new SimpleBitmapPool(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("NewApi")
    private PdfRendererParams extractPdfParamsFromFirstPage(PdfRenderer renderer, float renderQuality) {
        PdfRenderer.Page samplePage = getPDFPage(renderer, FIRST_PAGE);
        PdfRendererParams params = new PdfRendererParams();

        params.setRenderQuality(renderQuality);
        params.setOffScreenSize(offScreenSize);
        params.setWidth((int) (samplePage.getWidth() * renderQuality));
        params.setHeight((int) (samplePage.getHeight() * renderQuality));

        samplePage.close();

        return params;
    }

    protected ParcelFileDescriptor getSeekableFileDescriptor(String path) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor;

        File pdfCopy = new File(path);

        if (pdfCopy.exists()) {
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
            return parcelFileDescriptor;
        }

        if (isAnAsset(path)) {
            pdfCopy = new File(context.getCacheDir(), path);
            parcelFileDescriptor = ParcelFileDescriptor.open(pdfCopy, ParcelFileDescriptor.MODE_READ_ONLY);
        } else {
            URI uri = URI.create(String.format("file://%s", path));
            parcelFileDescriptor = context.getContentResolver().openFileDescriptor(Uri.parse(uri.toString()), "rw");
        }

        return parcelFileDescriptor;
    }

    private boolean isAnAsset(String path) {
        return !path.startsWith("/");
    }

    @Override
    @SuppressWarnings("NewApi")
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.view_pdf_page, container, false);
        ImageView iv = (ImageView) v.findViewById(R.id.imageView);

        if (renderer == null || getCount() < position) {
            return v;
        }

        PdfRenderer.Page page = getPDFPage(renderer, position);

        Bitmap bitmap = bitmapContainer.get(position);
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        page.close();

        iv.setImageBitmap(bitmap);
        container.addView(v, 0);

        return v;
    }

    @SuppressWarnings("NewApi")
    protected PdfRenderer.Page getPDFPage(PdfRenderer renderer, int position) {
        return renderer.openPage(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // bitmap.recycle() causes crashes if called here.
        // All bitmaps are recycled in close().
    }

    @SuppressWarnings("NewApi")
    public void close() {
        releaseAllBitmaps();
        if (renderer != null) {
            renderer.close();
        }
    }

    protected void releaseAllBitmaps() {
        if (bitmapContainer != null) {
            bitmapContainer.clear();
        }
    }

    @Override
    @SuppressWarnings("NewApi")
    public int getCount() {
        return renderer != null ? renderer.getPageCount() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
