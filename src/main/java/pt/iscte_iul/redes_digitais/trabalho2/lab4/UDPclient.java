package pt.iscte_iul.redes_digitais.trabalho2.lab4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPclient {

	public static void main(String[] args) {
		
        // Local SAP - lo; SAP destino . 
		// Local socket - localSocket
		InetSocketAddress lo = null;
		DatagramSocket localSocket = null;
		
        try {
        	// Identificação do SAP local
        	InetAddress loIP = InetAddress.getByName("127.0.0.1");
        	lo = new InetSocketAddress(loIP, 20000);

        	// Criação e binding do soket 
			localSocket = new DatagramSocket(lo);
			System.out.println("LocalIP: "+localSocket.getLocalAddress().toString());
			System.out.println("LocalPort: "+localSocket.getLocalPort());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	    } catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
        
		// Envio de 10 mensagens - datagramas UDP - com um tamanho de 10000 bytes cada
		for (int i=0; i<10; i++) {
			byte[] data = new byte[10000];
			for (int j=0; j<10000; j++) {
				data[j]=(byte)i;
			}
			try {
	        	// Identificação do SAP destino
	        	InetAddress IPdestino = InetAddress.getByName("127.0.0.2");
	        	InetSocketAddress SAPdestino = new InetSocketAddress(IPdestino, 30000);    
	        	// Criação do datagrama UDP
				DatagramPacket p = new DatagramPacket(data, data.length, SAPdestino);

               	long beginTime = System.currentTimeMillis();
				// Envio do datagrama UDP
				localSocket.send(p);
        		long endTime = System.currentTimeMillis();        		
				
				System.out.println("Enviados "+p.getLength()+" bytes com destino a "
						           +p.getAddress().toString()+":"+p.getPort() + " em "+
						           (endTime-beginTime) + " miliseg");
			} catch (SocketException e) {
				e.printStackTrace();
			}  catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
}
