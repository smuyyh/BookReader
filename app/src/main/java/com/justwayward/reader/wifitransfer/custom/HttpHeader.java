package com.justwayward.reader.wifitransfer.custom;


public class HttpHeader {

    private String _method = "";
    private String _url = "";
    private String _fileName = "";
    private String _contentType = "text/html";

    public String getMethod() {
        return _method;
    }

    public String getUrl() {
        return _url;
    }

    public String getFileName() {
        return _fileName;
    }

    public String getContentType() {
        return _contentType;
    }

    public HttpHeader(String headerStr) {
        analy(headerStr);
    }

    private void analy(String headerStr) {
        if (headerStr == null || headerStr.length() <= 0) {
            return;
        }

        // Method
        _method = headerStr.substring(0, headerStr.indexOf(" "));

        // Url
        int start = headerStr.indexOf(_method) + _method.length() + 1;
        int end = headerStr.lastIndexOf("HTTP") - 1;
        _url = headerStr.substring(start, end);

        // File name and content type
        _fileName = _url.replace("/", "\\")
                .replace("\\..", "")
                .replace("\\", "/");
        start = _fileName.lastIndexOf('.') + 1;
        String fileSuffix = "";
        if (_fileName.length() <= 1) {
            _fileName = Defaults.getIndexPage();
            start = _fileName.lastIndexOf('.') + 1;
        }

        fileSuffix = _fileName.substring(start);

        _contentType = Defaults.extensions.get(fileSuffix);
    }
}
