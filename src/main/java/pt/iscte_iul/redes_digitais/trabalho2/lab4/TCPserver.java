package pt.iscte_iul.redes_digitais.trabalho2.lab4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.InputStream;

public class TCPserver {

	public static void main(String[] args) {
		
        // Local SAP - lo
		// Local socket - localSocket
		InetSocketAddress lo = null;
		ServerSocket localSocket = null;
		
        try {
        	// Identificação do SAP local
        	InetAddress loIP = InetAddress.getByName("127.0.0.2");
        	lo = new InetSocketAddress(loIP, 30000);
			// Criação e binding do soket 
			localSocket = new ServerSocket();
			localSocket.bind(lo);
			System.out.println("Local: "+localSocket.getLocalSocketAddress().toString());
		} catch (SocketException e) {
			e.printStackTrace();
	    } catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
     	}

        try {
          while(true) {
        	// Espera por pedido de ligação
        	Socket newSocket = localSocket.accept();
        	System.out.println("Estabelecida ligação com: "+newSocket.getRemoteSocketAddress().toString());
           	long beginTimeCiclo = System.currentTimeMillis();
        	
        	// InputStream do novo socket
        	InputStream is = (InputStream) newSocket.getInputStream();
        	// Ciclo de leitura de blocos até 10000 bytes cada
        	while (true) {
               	byte[] data = new byte[10000];
               	
               	long beginTime = System.currentTimeMillis();
        		// Leitura de dados do socket (via InputStream)
        		int bytesRead = is.read(data);
        		long endTime = System.currentTimeMillis();        		
        		
        		if (bytesRead <= 0) { // -1 indica fecho da ligação pelo producer
        			System.out.println("Producer fechou a ligação!");
        			break;
        		} else {
        			System.out.println("Recebidos "+bytesRead+" bytes em "+
        					           (endTime-beginTime) + " miliseg.");
        		}
        	}
        	// Fecho da logação
        	newSocket.close();

        	long endTimeCiclo = System.currentTimeMillis();        		
        	System.out.println("Fecho da ligação! (Duração ciclo "+
        			           (endTimeCiclo-beginTimeCiclo)+" miliseg.)");
          }
        } catch (SocketException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
     	}
	}
}
