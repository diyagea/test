package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	private static Socket socket;
	/**
	 * Socket客户端
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		try {
			// 创建Socket对象
			socket = new Socket("localhost", 8888);

			// 根据输入输出流和服务端连接
			OutputStream outputStream = socket.getOutputStream();// 获取一个输出流，向服务端发送信息
			PrintWriter printWriter = new PrintWriter(outputStream);// 将输出流包装成打印流

			InputStream inputStream = socket.getInputStream();// 获取一个输入流，接收服务端的信息
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);// 包装成字符流，提高效率
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);// 缓冲区
			String temp = null;// 临时变量
			boolean flag = true;
			while (flag) {
				temp = bufferedReader.readLine();
				while (temp != null && !temp.equals("")) {
					System.out.println("client got command:" + temp);
					printWriter.print("client got command:" + temp);
					printWriter.flush();
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// 关闭相对应的资源
			socket.shutdownOutput();
			socket.close();
		}

	}

}