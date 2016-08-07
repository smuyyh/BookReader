package com.yuyh.library.utils;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

public class IO {

  private IO() {
  }

  public static final byte[] EMPTY_BYTES = new byte[0];

  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static void close(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException e) {
      AphidLog.w(e, "Failed to close a closable");
    }
  }

  public static byte[] readData(InputStream input) {
    if (input == null) {
      return null;
    }
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] buf = new byte[1024];
    int len = -1;

    try {
      while ((len = input.read(buf)) != -1) {
        output.write(buf, 0, len);
      }

      return output.toByteArray();
    } catch (IOException e) {
      AphidLog.e(e, "Failed to readData");
      return null;
    } finally {
      close(input);
    }
  }

  public static String readString(InputStream input) {
    if (input == null) {
      return null;
    }
    return readString(new InputStreamReader(input, UTF_8));
  }

  public static String readString(Reader reader) {
    if (reader == null) {
      return null;
    }
    StringBuilder builder = new StringBuilder();
    char[] buf = new char[1024];
    int len = -1;

    try {
      while ((len = reader.read(buf)) != -1) {
        builder.append(buf, 0, len);
      }

      return builder.toString();
    } catch (IOException e) {
      AphidLog.e(e, "Failed to readString");
      return null;
    } finally {
      close(reader);
    }
  }

  public static Bitmap readBitmap(InputStream input) {
    if (input == null) {
      return null;
    }

    try {
      return BitmapFactory.decodeStream(input);
    } catch (Exception e) {
      AphidLog.e(e, "Failed to read bitmap");
      return null;
    } finally {
      close(input);
    }
  }

  public static Bitmap readBitmap(AssetManager manager, String name) {
    try {
      return readBitmap(manager.open(name));
    } catch (IOException e) {
      AphidLog.e(e, "Failed to read bitmap '%s' from assets", name);
      return null;
    }
  }
}
