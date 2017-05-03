package pt.iscte_iul.redes_digitais.trabalho2.client;

import pt.iscte_iul.redes_digitais.trabalho2.common.AbstractSocket;
import pt.iscte_iul.redes_digitais.trabalho2.lab5.myPDU;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class ClientSocket extends AbstractSocket {
    private final static int KBPS = 200 * 1000;
    private final int msgSize;
    private final double ta;
    private HashMap<DatagramPacket, Long> generationTimes;
    private int counter = 0;

    public ClientSocket(InetSocketAddress localAddress, int msgSize, double ta) {
        super(localAddress, "./sender.data");
        this.msgSize = msgSize;
        this.ta = ta;
        generationTimes = new HashMap<DatagramPacket, Long>();
    }

    private long sleepMillis() {
        if (ta == 0) {
            return 0;
        }
        return (long) ((1.0 / (ta * (KBPS / msgSize))) * 1000);
    }

    public void run() {
        DatagramPacket userIDU;
        while (Thread.interrupted()) {
            try {
                // Receber a IDU vinda do utilizador através da fila - queue
                userIDU = queue.take();
                // Enviar a SDU do utilizador através no mySocket
                sendToUDP(userIDU);
                Thread.sleep(sleepMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToUDP(DatagramPacket myIDU) {
        // Construir a myPDU - tipo I, nSeq dado por counter
        myPDU p = new myPDU(myIDU, myPDU.Type.I, counter);
        // Verficar quanto tempo na fila - queue
        Long genTime = generationTimes.get(myIDU);
        generationTimes.remove(myIDU);
        System.out.println("Tempo na fila : " +
            Long.toString(System.currentTimeMillis() - genTime) +
            " miliseg.");

        try {
            // Construir o datagrama UDP que vai transportar a myPDU
            // A myPDU é a SDU do protocolo UDP que a vai transportar
            byte[] myPDUbytes = p.toBytes();
            DatagramPacket iduUDP = new DatagramPacket(myPDUbytes, myPDUbytes.length,
                myIDU.getSocketAddress());
            // Pedido para enviar IDU do protocolo UDP
            local.send(iduUDP);
            counter++;
            // Escreve log
            log("MS", "UDP", (myPDU.Type.I).toString(), p.getSeq());
            // Implementação da recepcao de PDU de tipo R
            if ((counter % REPORT_INTERVAL) == 0) {
                byte[] rdata = new byte[4 + 4 + 8];
                DatagramPacket rUDP = new DatagramPacket(rdata, rdata.length);
                //local.setSoTimeout(WAIT_R); // timeout para recepcao, se comentado bloqueia à espera
                local.receive(rUDP);
                myPDU rPDU = new myPDU(rUDP);
                // Create log
                log("UDP", "MS", (myPDU.Type.R).toString(), rPDU.getSeq());
            }
        } catch (SocketTimeoutException e) {
            // Caso em que ocorre o timeout.
            log("MS", "MS", "T", 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(DatagramPacket p) {
        try {
            // Colocar no HashMap de tempos de geraçao
            generationTimes.put(p, Long.valueOf(System.currentTimeMillis()));
            // Colocar na fila para emissao pelo protocolo mySocket
            queue.put(p);
            // Criar o log
            log("APP", "MS", (myPDU.Type.I).toString(), counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
