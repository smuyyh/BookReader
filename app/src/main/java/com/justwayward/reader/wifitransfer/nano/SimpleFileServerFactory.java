package com.justwayward.reader.wifitransfer.nano;

/**
 * ʵ��SimpleFileServer�ĵ���ģʽ
 * 
 * @author liushuai
 * 
 */
public class SimpleFileServerFactory {
	private static SimpleFileServer server;

	public static SimpleFileServer getInstance(int port) {
		if (server == null) {
			server = new SimpleFileServer(port);
		}
		return server;
	}
}
