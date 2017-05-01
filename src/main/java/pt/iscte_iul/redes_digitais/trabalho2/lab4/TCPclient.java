package pt.iscte_iul.redes_digitais.trabalho2.lab4;

import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.io.IOException;

public class TCPclient {

	public static void main(String[] args) {
		
        // Local SAP - lo
		// Local socket - localSocket
		InetSocketAddress lo = null;
		Socket localSocket = null;
		
        try {
        	// Identificação do SAP local
        	InetAddress loIP = InetAddress.getByName("127.0.0.1");
        	lo = new InetSocketAddress(loIP, 20000);
			// Criação e binding do soket 
			localSocket = new Socket();
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
            // Identificação do SAP destino
            InetAddress IPdestino = InetAddress.getByName("127.0.0.2");
            InetSocketAddress SAPdestino = new InetSocketAddress(IPdestino, 30000);
        	// Faz pedido de ligação ao servidor
        	localSocket.connect(SAPdestino);
        	System.out.println("Estabelecida ligação com: "+localSocket.getInetAddress().toString()
        			           +":"+localSocket.getPort());
        	// InputStream do novo socket
        	OutputStream os = (OutputStream) localSocket.getOutputStream();

        	// Ciclo de escrita de 10 mensagens com 10000 bytes
        	byte[] data = new byte[10000];
        	for (int i=0; i<10	; i++) {
        	    for (int j=0; j<10000; j++) {
                   // Escrita de dados do socket (via OutnputStream)
        	       data[j]=(byte)i;
        	    }
        	    
               	long beginTime = System.currentTimeMillis();
                // Escrita de dados do socket (via OutputStream)
     		    os.write(data);
        		long endTime = System.currentTimeMillis();        		

     		    System.out.println("Enviados "+data.length+" bytes em "+
     		    		           (endTime-beginTime)+" miliseg.");
        	}
        	// Fecho da ligação
        	localSocket.close();
        	System.out.println("Fecho da ligação!");
        } catch (SocketException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
     	}
	}
}
