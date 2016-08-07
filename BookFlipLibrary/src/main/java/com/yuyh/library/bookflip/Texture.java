package com.yuyh.library.bookflip;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.yuyh.library.utils.AphidLog;

import junit.framework.Assert;

import javax.microedition.khronos.opengles.GL10;

import static javax.microedition.khronos.opengles.GL10.GL_CLAMP_TO_EDGE;
import static javax.microedition.khronos.opengles.GL10.GL_LINEAR;
import static javax.microedition.khronos.opengles.GL10.GL_RGB;
import static javax.microedition.khronos.opengles.GL10.GL_RGBA;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_2D;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_MAG_FILTER;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_MIN_FILTER;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_S;
import static javax.microedition.khronos.opengles.GL10.GL_TEXTURE_WRAP_T;
import static javax.microedition.khronos.opengles.GL10.GL_UNSIGNED_BYTE;
import static javax.microedition.khronos.opengles.GL10.GL_UNSIGNED_SHORT_4_4_4_4;
import static javax.microedition.khronos.opengles.GL10.GL_UNSIGNED_SHORT_5_6_5;

public class Texture {

  private FlipRenderer renderer;

  private int[] id = {0};

  private int width, height;
  private int contentWidth, contentHeight;

  private boolean destroyed = false;

  private Texture() {
  }

  public static Texture createTexture(Bitmap bitmap, FlipRenderer renderer, GL10 gl) {
    Texture t = new Texture();
    t.renderer = renderer;

    Assert.assertTrue("bitmap should not be null or recycled",
                      bitmap != null && !bitmap.isRecycled());

    int potW = Integer.highestOneBit(bitmap.getWidth() - 1) << 1;
    int potH = Integer.highestOneBit(bitmap.getHeight() - 1) << 1;

    t.contentWidth = bitmap.getWidth();
    t.contentHeight = bitmap.getHeight();
    t.width = potW;
    t.height = potH;

    if (AphidLog.ENABLE_DEBUG) {
      AphidLog.d("createTexture: %d, %d; POT: %d, %d", bitmap.getWidth(), bitmap.getHeight(), potW,
                 potH);
    }

    gl.glGenTextures(1, t.id, 0);
    gl.glBindTexture(GL_TEXTURE_2D, t.id[0]);

    gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    gl.glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

    switch (bitmap.getConfig()) {
      case ARGB_8888:
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, potW, potH, 0, GL_RGBA, GL_UNSIGNED_BYTE, null);
        GLUtils.texSubImage2D(GL_TEXTURE_2D, 0, 0, 0, bitmap);
        break;
      case ARGB_4444:
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, potW, potH, 0, GL_RGBA,
                        GL_UNSIGNED_SHORT_4_4_4_4, null);
        GLUtils.texSubImage2D(GL_TEXTURE_2D, 0, 0, 0, bitmap);
        break;
      case RGB_565:
        gl.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, potW, potH, 0, GL_RGB, GL_UNSIGNED_SHORT_5_6_5,
                        null);
        GLUtils.texSubImage2D(GL_TEXTURE_2D, 0, 0, 0, bitmap);
        break;
      case ALPHA_8:
      default:
        throw new RuntimeException(
            "Unrecognized bitmap format for OpenGL texture: " + bitmap.getConfig());
    }

    FlipRenderer.checkError(gl);

    return t;
  }

  public void postDestroy() {
    renderer.postDestroyTexture(this);
  }

  public void destroy(GL10 gl) {
    if (id[0] != 0) {
      gl.glDeleteTextures(1, id, 0);
      if (AphidLog.ENABLE_DEBUG) {
        AphidLog.d("Destroy texture: %d", id[0]);
      }
    }

    id[0] = 0;
    destroyed = true;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public int[] getId() {
    return id;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public int getContentWidth() {
    return contentWidth;
  }

  public int getContentHeight() {
    return contentHeight;
  }
}
