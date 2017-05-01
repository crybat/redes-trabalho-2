package pt.iscte_iul.redes_digitais.trabalho2.lab4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPserver {

	public static void main(String[] args) {
		
        // Local SAP - lo
		// Local socket - localSocket
		InetSocketAddress lo = null;
		DatagramSocket localSocket = null;
        try {
        	// Identificação do SAP local
        	InetAddress loIP = InetAddress.getByName("127.0.0.2");
        	lo = new InetSocketAddress(loIP, 30000);
        	
			// Criação e binding do soket 
			localSocket = new DatagramSocket(lo);
			System.out.println("LocalIP: "+localSocket.getLocalAddress().toString());
			System.out.println("LocalPort: "+localSocket.getLocalPort());
		} catch (SocketException e) {
			e.printStackTrace();
	    } catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// Receção de pacotes com tamanho até 10000 bytes 
		while (true) {
			try {
				byte[] data = new byte[10000];
				// Instaciaçáo de um objecto datagrama UDP
				DatagramPacket p = new DatagramPacket(data, data.length);

               	long beginTime = System.currentTimeMillis();
				// Receção de um datagrama UDP
				localSocket.receive(p);
        		long endTime = System.currentTimeMillis();        		

				System.out.println("Recebidos "+p.getLength()+" bytes com origem em "
						                       +p.getSocketAddress().toString() + " em "+
						                       (endTime-beginTime)+ " miliseg.");
			} catch (SocketException e) {
				e.printStackTrace();
			}  catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}