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
package com.justwayward.reader.wifitransfer;

import android.text.TextUtils;

import com.justwayward.reader.bean.Recommend;
import com.justwayward.reader.manager.CollectionsManager;
import com.justwayward.reader.manager.EventManager;
import com.justwayward.reader.utils.FileUtils;
import com.justwayward.reader.utils.LogUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Wifi传书 服务端
 *
 * @author yuyh.
 * @date 2016/10/10.
 */
public class SimpleFileServer extends NanoHTTPD {

    private static SimpleFileServer server;

    public static SimpleFileServer getInstance() {
        if (server == null) {
            server = new SimpleFileServer(Defaults.getPort());
        }
        return server;
    }

    public SimpleFileServer(int port) {
        super(port);
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header, Map<String, String> parms,
                          Map<String, String> files) {
        if (Method.GET.equals(method)) {
            try {
                uri = new String(uri.getBytes("ISO-8859-1"), "UTF-8");
                LogUtils.d("uri= " + uri);
            } catch (UnsupportedEncodingException e) {
                LogUtils.w("URL参数编码转换错误：" + e.toString());
            }

            //return new Response(HtmlConst.HTML_STRING);
            if (uri.contains("index.html") || uri.equals("/")) {
                return new Response(Response.Status.OK, "text/html", new String(FileUtils.readAssets("/index.html")));
            } else if (uri.startsWith("/files/") && uri.endsWith(".txt")) {
                String bookid = uri.substring(7, uri.lastIndexOf("."));
                return new Response(Response.Status.OK, "file", new String(FileUtils.getBytesFromFile(FileUtils.getChapterFile(bookid, 1))));
            } else {
                // 获取文件类型
                String type = Defaults.extensions.get(uri.substring(uri.lastIndexOf(".") + 1));
                if (TextUtils.isEmpty(type))
                    return new Response("");
                // 读取文件
                byte[] b = FileUtils.readAssets(uri);
                if (b == null || b.length < 1)
                    return new Response("");
                // 响应
                return new Response(Response.Status.OK, type, new ByteArrayInputStream(b));
            }
        } else {
            // 读取文件
            for (String s : files.keySet()) {
                try {
                    FileInputStream fis = new FileInputStream(files.get(s));
                    String fileName = parms.get("newfile");
                    if (fileName.lastIndexOf(".") > 0)
                        fileName = fileName.substring(0, fileName.lastIndexOf("."));

                    // 创建临时文件保存
                    File outputFile = FileUtils.createWifiTempFile();
                    LogUtils.i("--" + outputFile.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int byteRead = fis.read(buffer);
                        if (byteRead == -1) {
                            break;
                        }
                        fos.write(buffer, 0, byteRead);
                    }
                    fos.close();

                    // 创建目标文件
                    File desc = FileUtils.createWifiTranfesFile(fileName);
                    LogUtils.i("--" + desc.getAbsolutePath());

                    FileUtils.fileChannelCopy(outputFile, desc);

                    // 添加到收藏
                    addToCollection(fileName);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Response("");
        }
    }

    /**
     * 添加到收藏
     *
     * @param fileName
     */
    private void addToCollection(String fileName) {
        Recommend.RecommendBooks books = new Recommend.RecommendBooks();
        books.isFromSD = true;

        books._id = fileName;
        books.title = fileName;

        //Looper.prepare();
        if (CollectionsManager.getInstance().add(books)) {
            //ToastUtils.showToast(String.format(AppUtils.getAppContext().getString(
            //R.string.book_detail_has_joined_the_book_shelf), books.title));
            EventManager.refreshCollectionList();
        } else {
            //ToastUtils.showSingleToast("书籍已存在");
        }
        //Looper.loop();
    }
}
