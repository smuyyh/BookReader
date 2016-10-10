package com.justwayward.reader.wifitransfer.custom;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.justwayward.reader.utils.LogUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

/**
 * @author yuyh.
 * @date 2016/10/10.
 */
public class WebService extends Service implements Runnable {

    private static boolean isRunning = false;

    private static Thread serverThread;

    private ServerSocket listenSocket = null;
    private TcpListener tcpListener = null;

    public WebService() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        listenSocket = new ServerSocket();
        listenSocket.setReuseAddress(true);
        listenSocket.bind(new InetSocketAddress(Defaults.getPort()));
    }

    public static void start(Context context) {
        if (!isRunning) {
            isRunning = true;

            Intent intent = new Intent(context, WebService.class);
            context.startService(intent);
        }
    }

    public static void stop(Context context) {
        if (isRunning) {
            isRunning = false;

            Intent intent = new Intent(context, WebService.class);
            context.stopService(intent);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        serverThread = new Thread(this);
        serverThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {
        if (tcpListener == null) {
            tcpListener = new TcpListener(listenSocket);
            tcpListener.start();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (listenSocket != null) {
            try {
                listenSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (serverThread != null) {
            serverThread.interrupt();

            try {
                serverThread.join(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (serverThread.isAlive()) {
                LogUtils.w("Server thread failed to exit");
            } else {
                LogUtils.w("serverThread joined ok");
                serverThread = null;
            }
        }

        LogUtils.i("Web Service Destroy!");
    }
}
