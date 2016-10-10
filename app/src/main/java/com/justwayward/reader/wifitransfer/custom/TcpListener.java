package com.justwayward.reader.wifitransfer.custom;

import com.justwayward.reader.utils.LogUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author yuyh.
 * @date 2016/10/10.
 */
public class TcpListener extends Thread {

    private ServerSocket listenSocket;

    public TcpListener(ServerSocket listenSocket) {
        this.listenSocket = listenSocket;
    }

    @Override
    public void run() {
        try {
            while (listenSocket != null && !listenSocket.isClosed()) {
                Socket socket = listenSocket.accept();

                LogUtils.i("new connection");

                SessionThread newSession = new SessionThread(socket);
                newSession.start();
            }
        } catch (IOException e) {
            LogUtils.e("TcpListener:" + e.toString());
        }
    }
}
