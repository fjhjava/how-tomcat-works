package com.java1234.server;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	public static final String WEB_ROOT = "D:" + File.separator + "webroot";
	private static final String SHUTDOWN_COMMD = "/SHUTDOWN";

	private boolean shutdown = false;

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		System.out.println(WEB_ROOT);
		server.await();
	}

	public void await() {
		ServerSocket serverSocket = null;
		int port = 80;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (!shutdown) {
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			try {
				socket = serverSocket.accept();
				input = socket.getInputStream();
				output = socket.getOutputStream();
				Request request = new Request(input);
				request.parse();
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				socket.close();
				shutdown = request.getUrl().equals(SHUTDOWN_COMMD);

			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}

}
