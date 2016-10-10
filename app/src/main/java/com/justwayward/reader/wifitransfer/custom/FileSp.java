package com.justwayward.reader.wifitransfer.custom;

import android.content.res.AssetManager;

import com.justwayward.reader.utils.AppUtils;

import java.io.IOException;
import java.io.InputStream;

public class FileSp {

    public static byte[] read(String fileName) {
        if (fileName == null || fileName.length() <= 0) {
            return null;
        }

        byte[] buffer = null;

        try {

            InputStream fin = AppUtils.getAppContext().getAssets().open("uploader" + fileName);
            int length = fin.available();

            buffer = new byte[length];

            fin.read(buffer);

            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return buffer;
        }
    }

    public static boolean isExist(String filePath) {
        AssetManager asset = AppUtils.getAppContext().getAssets();
        try {
            asset.open("uploader" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
