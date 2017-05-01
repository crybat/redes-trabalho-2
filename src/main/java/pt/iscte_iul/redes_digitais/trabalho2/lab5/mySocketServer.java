package pt.iscte_iul.redes_digitais.trabalho2.lab5;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class mySocketServer extends mySocket {
	static private int counter = 0;
	
	public mySocketServer(InetSocketAddress localAddress) {
		super(localAddress, "./receiver.data");
	}
	
	public void run() {
		while(true) {
			try {
				// Ler a myPDU via UDP
				myPDU p = receiveFromUDP();
				// Escreve log
				log("UDP", "MS", (myPDU.Type.I).toString(), p.getSeq());
				System.out.println("Recebida PDU I, tempo transferência: "+
						           Long.toString(System.currentTimeMillis()-p.getTimestamp())+
						            " miliseg.");
				
				// Coloca a IDU do utilizador na fila
				queue.put(p.getIDU());
				counter++;

				// Implementacao da parte Report do protocolo
				if ((counter % REPORT_INTERVAL) == 0) { // N.o de PDU recebidas é multiplo
					// Criar uma PDU Report
					// com o n.o sequencia da ultima PDU recebida, nao tem dados
					myPDU r = new myPDU(myPDU.Type.R, p.getSeq());
					DatagramPacket iduUDP = new DatagramPacket(r.toBytes(), r.toBytes().length,
													           p.getIDU().getSocketAddress());				
					local.send(iduUDP);
					// Escreve log para a PDU R
					log("MS", "UDP", (myPDU.Type.R).toString(), p.getSeq());
					counter=0; 
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (SocketException e) {
			    e.printStackTrace();	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
	
	private myPDU receiveFromUDP() {
		byte[] pdu = new byte[MAXDATA+(4+4+8)];
		DatagramPacket iduUDP = new DatagramPacket(pdu, pdu.length);
		try {
			// Recebe a IDU vinda do socket UDP.
			local.receive(iduUDP);
			// Da IDU recebida constroi a myPDU que foi enviada
			return new myPDU(iduUDP);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void receive(DatagramPacket myIDU) {
		if (this.isInterrupted()) {this.start();}
		try {
			// Recolhe da fila (bloqueante) a IDU recebida para dar ao user
			DatagramPacket receivedIDU = queue.take();
			myIDU.setData(receivedIDU.getData());
			myIDU.setAddress(receivedIDU.getAddress());
			myIDU.setPort(receivedIDU.getPort());
			log("MS", "APP", (myPDU.Type.I).toString(), counter);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}