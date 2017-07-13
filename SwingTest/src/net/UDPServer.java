package net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDPServer
 */
public class UDPServer {
	public static void main(String[] args) throws IOException {

		// create socket
		DatagramSocket server = new DatagramSocket(8989);

		byte[] recvBuf = new byte[100];
		DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
		server.receive(recvPacket);
		
		//get client message
		String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
		System.out.println("Hello World!" + recvStr);
		
		//get client IP and Port
		int port = recvPacket.getPort();
		InetAddress addr = recvPacket.getAddress();
		
		//send back message
		String sendStr = "Hello ! I'm Server";
		byte[] sendBuf = sendStr.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
		server.send(sendPacket);
		
		server.close();
	}
}