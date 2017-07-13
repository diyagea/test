package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	public String traceLog = "";

	private OutputStream outputStream;
	private Socket socket;
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private InputStream inputStream;
	private InputStreamReader inputStreamReader;
	private ServerSocket serverSocket;

	public void shutdown() throws Exception {
		socket.shutdownOutput();// 关闭输出流

		// 关闭相对应的资源
		printWriter.close();
		outputStream.close();
		bufferedReader.close();
		inputStream.close();
		socket.close();
	}

	/**
	 * send message
	 * 
	 * @param socket
	 * @return
	 */
	public String send(String command) {
		String temp = null;
		try {
			printWriter.print(command);
			printWriter.flush();


			while ((temp = bufferedReader.readLine()) != null) {
				traceLog += temp + "\n";
			}
		} catch (Exception e) {
			return e.toString() + " : " + e.getMessage();
		}

		return temp;
	}

	/**
	 * run server
	 */
	public String run(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("服务端已启动，等待客户端连接..");
			socket = serverSocket.accept();// 侦听并接受到此套接字的连接,返回一个Socket对象
			// get client IP and port
			String clientIP = socket.getInetAddress().getHostAddress();
			int clientPort = socket.getPort();

			// get Input stream
			inputStream = socket.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);

			// get Output stream
			outputStream = socket.getOutputStream();
			printWriter = new PrintWriter(outputStream);

			String msg = "A Client Connected [IP:" + clientIP + ", port:" + clientPort + "]";
			System.out.println(msg);
			return msg;
		} catch (IOException e) {
			return "Connecting Error: " + e.toString() + "-" + e.getMessage();
		}
	}

}