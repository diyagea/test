package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * UDPClient
 */
public class UDPClient {
	public static void main(String[] args) throws IOException {

		// create socket
		DatagramSocket client = new DatagramSocket();

		// message
		String sendStr = "Hello! I'm Client";
		byte[] sendBuf = sendStr.getBytes();

		// IP Address
		InetAddress addr = InetAddress.getByName("127.0.0.1");
		int port = 8989;

		// send
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
		client.send(sendPacket);

		byte[] recvBuf = new byte[100];
		DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);

		// back message
		client.receive(recvPacket);
		String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());

		System.out.println("收到:" + recvStr);
		client.close();
	}

	/**
	 * send command
	 * 
	 * @param ipStr
	 * @param port
	 * @param command
	 */
	public static String sendCommand(String ipStr, String portStr, String command) {
		// create socket
		DatagramSocket client = null;
		try {
			client = new DatagramSocket();
			byte[] sendBuf = command.getBytes();

			// IP Address
			InetAddress addr = InetAddress.getByName(ipStr);
			int port = Integer.parseInt(portStr);
			// send
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);

			client.send(sendPacket);

			// ===========feedback=================

			byte[] recvBuf = new byte[100];
			DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);

			// back message
			client.receive(recvPacket);
			String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());

			return "return msg: " + recvStr;

		} catch(NumberFormatException e){
			return "Port Input Error:" + e.getMessage();
		} catch (SocketException e) {
			return "Socket Error：" + e.getMessage();
		} catch (UnknownHostException e) {
			return "Unknown Host Error：" + e.getMessage();
		} catch (IOException e) {
			return "Connection Error: " + e.getMessage();
		} finally {
			client.close();
		}

	}

}