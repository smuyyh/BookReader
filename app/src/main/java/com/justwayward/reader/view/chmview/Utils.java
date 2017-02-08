package com.justwayward.reader.view.chmview;

import android.util.Log;

import org.ccil.cowan.tagsoup.jaxp.SAXParserImpl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Utils {
    public static CHMFile chm = null;

    public static ArrayList<String> domparse(String filePath, String extractPath, String md5) throws IOException {
        final ArrayList<String> listSite = new ArrayList<>();
        listSite.add(md5);
//        Document doc = Jsoup.parse(chm.getResourceAsStream(""), "UTF-8", "");
//        Elements listObject = doc.getElementsByTag("object");
//        for (Element object : listObject) {
//            Elements listParam = object.getElementsByTag("param");
//            if (listParam.size() > 0) {
//                String name = "", local = "";
//                for (Element param : listParam) {
//                    if (param.attributes().getIgnoreCase("name").equalsIgnoreCase("name")) {
//                        name = param.attributes().getIgnoreCase("value");
//                    } else if (param.attributes().getIgnoreCase("name").equalsIgnoreCase("local")) {
//                        local = param.attributes().getIgnoreCase("value");
//                    }
//                }
//                listSite.add(local);
//                object.parent().prepend("<a href=\"" + local + "\">" + name + "</a>");
//                object.remove();
//            }
//        }
//        try {
//            FileOutputStream fosHTMLMap =  new FileOutputStream(extractPath + "/" +md5);
//            fosHTMLMap.write(doc.outerHtml().getBytes());
//            fosHTMLMap.close();
//
//            FileOutputStream fosListSite =  new FileOutputStream(extractPath + "/site_map_" +md5);
//            for(String str: listSite) {
//                fosListSite.write((str+";").getBytes());
//            }
//            fosListSite.close();
//            Log.e("Utils", "write ok " + "/site_map_" +md5);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Utils", "write ok sitemap error");
//        }
        ///////////////////////////////////////////////////
        try {

            final FileOutputStream fosHTMLMap = new FileOutputStream(extractPath + "/" + md5);
            final FileOutputStream fosListSite = new FileOutputStream(extractPath + "/site_map_" + md5);
            try {
                fosListSite.write((md5 + ";").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (chm.getResourceAsStream("") != null) {
                SAXParserImpl.newInstance(null).parse(
                        chm.getResourceAsStream(""),
                        new DefaultHandler() {
                            class MyUrl {
                                public int status = 0;
                                public String name;
                                public String local;

                                public String toString() {
                                    if (status == 1)
                                        return "<a href=\"#\">" + name + "</a>";
                                    else
                                        return "<a href=\"" + local + "\">" + name + "</a>";
                                }
                            }

                            MyUrl url = new MyUrl();
                            HashMap<String, String> myMap = new HashMap<String, String>();
                            int count = 0;

                            public void startElement(String uri, String localName, String qName,
                                                     Attributes attributes) throws SAXException {

                                if (qName.equals("param")) {
                                    count++;
                                    for (int i = 0; i < attributes.getLength(); i++) {
                                        myMap.put(attributes.getQName(i).toLowerCase(), attributes.getValue(i).toLowerCase());
                                    }
                                    if (myMap.get("name").equals("name") && myMap.get("value") != null) {
                                        url.name = myMap.get("value");
                                        url.status = 1;
                                    } else if (myMap.get("name").equals("local") && myMap.get("value") != null) {
                                        url.local = myMap.get("value");
                                        url.status = 2;
                                        listSite.add(url.local.replaceAll("%20", " "));
                                        try {
                                            fosListSite.write((url.local.replaceAll("%20", " ") + ";").getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    if (url.status == 2) {
                                        url.status = 0;
                                        try {
                                            fosHTMLMap.write(url.toString().getBytes());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } else {
                                    if (url.status == 1) {
                                        try {
                                            fosHTMLMap.write(url.toString().getBytes());
                                            url.status = 0;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                if (!qName.equals("object") && !qName.equals("param"))
                                    try {
                                        fosHTMLMap.write(("<" + qName + ">").getBytes());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                            }

                            public void endElement(String uri, String localName,
                                                   String qName) throws SAXException {
                                if (!qName.equals("object") && !qName.equals("param"))
                                    try {
                                        fosHTMLMap.write(("</" + qName + ">").getBytes());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                            }
                        }
                );
            } else {
                fosHTMLMap.write("<HTML> <BODY> <UL>".getBytes());
                for (String fileName : chm.list()) {
                    fileName = fileName.substring(1);
                    if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {
                        fosListSite.write((fileName + ";").getBytes());
                        fosHTMLMap.write(("<li><a href=\"" + fileName + "\">" + fileName + "</a></li>").getBytes());
                        listSite.add(fileName);
                    }
                }
                fosHTMLMap.write("</UL> </BODY> </HTML>".getBytes());
            }
            fosHTMLMap.close();
            fosListSite.close();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////


        return listSite;
    }

    public static ArrayList<String> getListSite(String extractPath, String md5) {
        ArrayList<String> listSite = new ArrayList<>();

        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/site_map_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String[] arrSite = reval.toString().split(";");
        Collections.addAll(listSite, arrSite);
        return listSite;
    }

    public static ArrayList<String> getBookmark(String extractPath, String md5) {
        ArrayList<String> listBookMark = new ArrayList<>();
        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/bookmark_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] arrSite = reval.toString().split(";");
        for (String str : arrSite) {
            if (str.length() > 0) {
                listBookMark.add(str);
            }
        }
        return listBookMark;
    }

    public static int getHistory(String extractPath, String md5) {
        StringBuilder reval = new StringBuilder();
        try {
            InputStream in = new FileInputStream(extractPath + "/history_" + md5);
            byte[] buf = new byte[1024];
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        try {
            return Integer.parseInt(reval.toString());
        } catch (Exception e) {
            return 0;
        }

    }


    public static void saveBookmark(String extractPath, String md5, ArrayList<String> listBookmark) {
        try {
            FileOutputStream fos = new FileOutputStream(extractPath + "/bookmark_" + md5, false);
            for (String str : listBookmark) {
                fos.write((str + ";").getBytes());
            }
            fos.close();
        } catch (IOException ignored) {
        }
    }

    public static void saveHistory(String extractPath, String md5, int index) {
        try {
            FileOutputStream fos = new FileOutputStream(extractPath + "/history_" + md5, false);
            fos.write(("" + index).getBytes());
            fos.close();
        } catch (IOException ignored) {
        }
    }


    private static String getSiteMap(String filePath) {
        StringBuilder reval = new StringBuilder();
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            byte[] buf = new byte[1024];
            InputStream in = chm.getResourceAsStream("");
            int c = 0;
            while ((c = in.read(buf)) >= 0) {
                reval.append(new String(buf, 0, c));
            }
//            chm.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        return reval.toString();
    }

    public static boolean extract(String filePath, String pathExtract) {
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            File filePathTemp = new File(pathExtract);
            if (!filePathTemp.exists()) {
                if (!filePathTemp.mkdirs()) throw new IOException();
            }
//            for (String file : chm.list()) {
//                String temp = pathExtract + file;
//                String tempName = temp.substring(temp.lastIndexOf("/") + 1);
//                String tempPath = temp.substring(0, temp.lastIndexOf("/"));
//                File filePathTemp = new File(tempPath);
//                if (!filePathTemp.exists()) {
//                    if (!filePathTemp.mkdirs()) throw new IOException();
//                }
//                if (tempName.length() > 0) {
//                    FileOutputStream fos = null;
//                    try {
//                        fos = new FileOutputStream(temp);
//                        byte[] buf = new byte[1024];
//                        InputStream in = chm.getResourceAsStream(file);
//                        int c;
//                        while ((c = in.read(buf)) >= 0) {
//                            fos.write(buf, 0, c);
//                        }
//                    } catch (IOException e) {
//                        Log.d("Error extract file: ", file);
//                        e.printStackTrace();
//                    } finally {
//                        if (fos != null) fos.close();
//                    }
//                }
//            }
//            chm.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String checkSum(String path) {
        String checksum = null;
        try {
            FileInputStream fis = new FileInputStream(path);
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Using MessageDigest update() method to provide input
            byte[] buffer = new byte[8192];
            int numOfBytesRead;
            while ((numOfBytesRead = fis.read(buffer)) > 0) {
                md.update(buffer, 0, numOfBytesRead);
            }
            byte[] hash = md.digest();
            checksum = new BigInteger(1, hash).toString(16); //don't use this, truncates leading zero
        } catch (IOException | NoSuchAlgorithmException ignored) {
        }
        assert checksum != null;
        return checksum.trim();
    }

    public static boolean extractSpecificFile(String filePath, String pathExtractFile, String insideFileName) {
        try {
            if (chm == null) {
                chm = new CHMFile(filePath);
            }
            if (new File(pathExtractFile).exists()) return true;
            String path = pathExtractFile.substring(0, pathExtractFile.lastIndexOf("/"));
            File filePathTemp = new File(path);
            if (!filePathTemp.exists()) {
                if (!filePathTemp.mkdirs()) throw new IOException();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pathExtractFile);
                byte[] buf = new byte[1024];
                InputStream in = chm.getResourceAsStream(insideFileName);
                int c;
                while ((c = in.read(buf)) >= 0) {
                    fos.write(buf, 0, c);
                }
            } catch (IOException e) {
                Log.d("Error extract file: ", insideFileName);
                e.printStackTrace();
            } finally {
                if (fos != null) fos.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
