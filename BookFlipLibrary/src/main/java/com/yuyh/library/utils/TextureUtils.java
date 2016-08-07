package com.yuyh.library.utils;

import com.yuyh.library.bookflip.Texture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class TextureUtils {

  public static boolean isValidTexture(Texture t) {
    return t != null && !t.isDestroyed();
  }

  public static float d2r(float degree) {
    return degree * (float) Math.PI / 180f;
  }

  public static FloatBuffer toFloatBuffer(float[] v) {
    ByteBuffer buf = ByteBuffer.allocateDirect(v.length * 4);
    buf.order(ByteOrder.nativeOrder());
    FloatBuffer buffer = buf.asFloatBuffer();
    buffer.put(v);
    buffer.position(0);
    return buffer;
  }

  public static ShortBuffer toShortBuffer(short[] v) {
    ByteBuffer buf = ByteBuffer.allocateDirect(v.length * 2);
    buf.order(ByteOrder.nativeOrder());
    ShortBuffer buffer = buf.asShortBuffer();
    buffer.put(v);
    buffer.position(0);
    return buffer;
  }
}
