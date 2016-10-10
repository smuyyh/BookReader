package com.justwayward.reader.wifitransfer.custom;

import com.justwayward.reader.utils.LogUtils;

import java.io.UnsupportedEncodingException;

public class DataHandle {

    private String _receiveInfo = "";
    private HttpHeader _httpHeader = null;
    private String _encoding = "utf-8";
    private String _serverName = "Simple Web Server";
    private String _responseCode = "200 OK";
    private String _contentType = "text/html";

    public DataHandle(byte[] recieveData) {
        try {
            this._receiveInfo = new String(recieveData, _encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        _httpHeader = new HttpHeader(_receiveInfo);
    }

    public byte[] fetchContent() {
        byte[] backData = null;
        LogUtils.i("httpHeader.getFileName:" + _httpHeader.getFileName());
        if (_httpHeader.getFileName().contains("index.html")) {
            if (!isSupportMethod()) {
                backData = fetchNotSupportMethodBack();
                return backData;
            }

            String filePath = fetchFilePath();
            boolean hasFile = FileSp.isExist("/" + _httpHeader.getFileName());
            LogUtils.e("FilePath:" + filePath + "   " + hasFile);
            if (!hasFile) {
                backData = fetchNotFoundBack();
                return backData;
            }
            // 判断是否是支持的数据类型，如果不支持，从fetchNotSupportFileBack获取数据进行响应。
            if (!isSupportExtension()) {
                backData = fetchNotSupportFileBack();
                return backData;
            }

            backData = fetchBackFromFile("/" + _httpHeader.getFileName());
        } else {
            if (!isSupportMethod()) {
                backData = fetchNotSupportMethodBack();
                return backData;
            }

            String filePath = fetchFilePath();
            boolean hasFile = FileSp.isExist(_httpHeader.getFileName());
            LogUtils.e("FilePath:" + filePath + "   " + hasFile);
            if (!hasFile) {
                backData = fetchNotFoundBack();
                return backData;
            }
            // 判断是否是支持的数据类型，如果不支持，从fetchNotSupportFileBack获取数据进行响应。
            if (!isSupportExtension()) {
                backData = fetchNotSupportFileBack();
                return backData;
            }

            backData = fetchBackFromFile(_httpHeader.getFileName());
        }
        return backData;
    }

    public byte[] fetchHeader(int contentLength) {
        byte[] header = null;
        try {
            header = ("HTTP/1.1 " + _responseCode + "\r\n" + "Server: "
                    + _serverName + "\r\n" + "Content-Length: " + contentLength
                    + "\r\n" + "Connection: close\r\n" + "Content-Type: "
                    + _contentType + ";charset=" + _encoding + "\r\n\r\n")
                    .getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return header;
    }

    private boolean isSupportMethod() {
        String method = _httpHeader.getMethod();
        if (method == null || method.length() <= 0) {
            return false;
        }
        method = method.toUpperCase();
        if (method.equals("GET") || method.equals("POST")) {
            return true;
        }

        return false;
    }

    private boolean isSupportExtension() {
        String contentType = _httpHeader.getContentType();
        if (contentType == null || contentType.length() <= 0) {
            return false;
        }
        _contentType = contentType;
        return true;
    }

    private byte[] fetchNotSupportMethodBack() {
        byte[] backData = null;
        String notImplementMethod = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName
                + "</h2><div>501 - Method Not Implemented</div></body></html>";
        try {
            backData = notImplementMethod.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return backData;
    }

    private byte[] fetchNotSupportFileBack() {
        byte[] backData = null;
        String notImplementMethod = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName
                + "</h2><div>404.7 Not Found(Type Not Support)</div></body></html>";
        try {
            backData = notImplementMethod.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return backData;
    }

    private byte[] fetchBackFromFile(String filePath) {
        return FileSp.read(filePath);
    }

    private String fetchFilePath() {
        String root = Defaults.getRoot();

        String fileName = _httpHeader.getFileName();
        String filePath = "";

        if (fileName.startsWith("/") || fileName.startsWith("\\")) {
            filePath = root + fileName.substring(1);
        } else {
            filePath = root + fileName;
        }

        return filePath;
    }

    private byte[] fetchNotFoundBack() {
        byte[] notFoundData = null;
        String notFoundStr = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head><body><h2>"
                + _serverName + "</h2><div>404 - Not Found</div></body></html>";
        try {
            notFoundData = notFoundStr.getBytes(_encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return notFoundData;
    }
}
