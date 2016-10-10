package com.justwayward.reader.wifitransfer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yuyh.
 * @date 2016/10/10.
 */
public class Defaults {

    public static Map<String, String> extensions = new HashMap<String, String>() {
        {
            put("htm", "text/html");
            put("html", "text/html");
            put("xml", "text/xml");
            put("txt", "text/plain");
            put("json", "text/plain");
            put("css", "text/css");
            put("ico", "image/x-icon");
            put("png", "image/png");
            put("gif", "image/gif");
            put("jpg", "image/jpg");
            put("jpeg", "image/jpeg");
            put("zip", "application/zip");
            put("rar", "application/rar");
            put("js", "text/javascript");
        }
    };

    public static final String HTML_STRING = "<html>"
            + "<head><title>Air Drop</title>"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\">"
            + "</head>"
            + "<body>"
            + "<form action=\"\" method=\"post\" enctype=\"multipart/form-data\" name=\"form1\" id=\"form1\">"
            + "<label><input type=\"file\" name=\"file\" id=\"file\" /></label>"
            + "<input type=\"submit\" name=\"button\" id=\"button\" value=\"Submit\" />"
            + "</form></body></html>";

    private final static String root = "file:///android_asset/uploader/";
    private final static String indexPage = "index.html";
    private final static int port = 8080;

    public static String getRoot() {
        return root;
    }

    public static String getIndexPage() {
        return indexPage;
    }

    public static int getPort() {
        return port;
    }
}
