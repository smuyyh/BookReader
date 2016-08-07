package com.yuyh.library.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;


public class GrabIt {

  private GrabIt() {
  }

  public static Bitmap takeScreenshot(View view, Bitmap.Config config) {
    int width = view.getWidth();
    int height = view.getHeight();

    if (view != null && width > 0 && height > 0) {
      Bitmap bitmap = Bitmap.createBitmap(width, height, config);
      Canvas canvas = new Canvas(bitmap);
      view.draw(canvas);

      //canvas.drawColor(Color.RED, PorterDuff.Mode.DARKEN); //NOTES: debug option

      if (AphidLog.ENABLE_DEBUG) {
        AphidLog.d("create bitmap %dx%d, format %s", width, height, config);
      }

      return bitmap;
    } else {
      return null;
    }
  }
}
