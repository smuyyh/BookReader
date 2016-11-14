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
package com.justwayward.reader.licenses;

import java.io.*;

/**
 * 统一添加Licenses信息
 */
public class JavaLicenses {

    static String licenseStr = "";

    public static void main(String[] args) {

        try {

            File license = new File("app/license.txt");

            InputStreamReader read = new InputStreamReader(new FileInputStream(license), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = "";
            while ((lineTxt = bufferedReader.readLine()) != null) {
                licenseStr += lineTxt + "\n";
            }
            read.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        File f = new File("app/src/main/java/com/justwayward/reader");
        System.out.println(f.getAbsolutePath());
        print(f);
    }

    public static void print(File f) {

        if (f != null) {
            if (f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {
                    for (int i = 0; i < fileArray.length; i++) {
                        File file = fileArray[i];

                        if (file.isDirectory()) {
                            print(file);
                        } else {
                            addLicense(0, licenseStr, file);
                        }
                    }
                }
            } else {
                addLicense(0, licenseStr, f);
            }
        }
    }

    public static void addLicense(long skip, String str, File file) {
        try {

            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = bufferedReader.readLine();
            read.close();
            if (lineTxt.startsWith("/**")) {
                return;
            }

            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            if (skip < 0 || skip > raf.length()) {
                System.out.println("skip error");
                return;
            }
            byte[] b = str.getBytes();
            raf.setLength(raf.length() + b.length);
            for (long i = raf.length() - 1; i > b.length + skip - 1; i--) {
                raf.seek(i - b.length);
                byte temp = raf.readByte();
                raf.seek(i);
                raf.writeByte(temp);
            }
            raf.seek(skip);
            raf.write(b);
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}