package com.justwayward.reader.wifitransfer.nano;

import com.justwayward.reader.base.Constant;
import com.justwayward.reader.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class SimpleFileServer extends NanoHTTPD {

    public SimpleFileServer(int port) {
        super(port);
    }

    @Override
    public Response serve(String uri, Method method,
                          Map<String, String> header, Map<String, String> parms,
                          Map<String, String> files) {
        if (Method.GET.equals(method)) {
            return new Response(HtmlConst.HTML_STRING);
        } else {
            for (String s : files.keySet()) {
                try {
                    FileInputStream fis = new FileInputStream(files.get(s));
                    File outputFile = new File(Constant.BASE_PATH + "/uploader/" + parms.get("file"));
                    FileUtils.createFile(outputFile);
                    FileOutputStream fos = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int byteRead = fis.read(buffer);
                        if (byteRead == -1) {
                            break;
                        }
                        fos.write(buffer, 0, byteRead);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Response(HtmlConst.HTML_STRING);
        }
    }
}
