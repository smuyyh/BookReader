package com.justwayward.reader.wifitransfer.custom;

import com.justwayward.reader.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author yuyh.
 * @date 2016/10/10.
 */
public class SessionThread extends Thread {

    private static final int BUFFER_MAX = 8 * 1024;
    private Socket clientSocket = null;
    private DataHandle dataHandle = null;

    public SessionThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream socketInput = clientSocket.getInputStream();

            byte[] buffer = new byte[BUFFER_MAX];
            socketInput.read(buffer);

            LogUtils.i(new String(buffer,"GBK"));

            dataHandle = new DataHandle(buffer);
            byte[] content = dataHandle.fetchContent();

            sendResponse(clientSocket, content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendResponse(Socket clientSocket, byte[] content) {
        try {
            OutputStream socketOut = clientSocket.getOutputStream();

            byte[] header = dataHandle.fetchHeader(content.length);

            socketOut.write(header);
            socketOut.write(content);

            socketOut.close();
            clientSocket.close();
        } catch (Exception e) {
        }
    }
}
