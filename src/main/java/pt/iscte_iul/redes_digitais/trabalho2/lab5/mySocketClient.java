package pt.iscte_iul.redes_digitais.trabalho2.lab5;

import pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1.EmissorApp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class mySocketClient extends mySocket {
    public static final int WAIT_R = 100; // Espera pela PDU R - em miliseg.
    public int counter = 0;
    public int repetitionCounter = 0;
    public long totalTimeWaitingInQueue = 0;
    public long totalTimeWaitingForAck = 0;
    public long totalTime = 0;
    // Hash para guardar tempos de geracao
    private final Map<DatagramPacket, Long> generationTimes = new ConcurrentHashMap<>();

    public mySocketClient(InetSocketAddress localAddress) throws SocketException {
        super(localAddress, "./sender.data");
        local.setSoTimeout(WAIT_R);
    }

    public void run() {
        long startTime = 0;
        try {
            while (counter != EmissorApp.NMSG) {
                // Receber a IDU vinda do utilizador através da fila - queue
                final DatagramPacket userIDU = queue.take();
                // Tempo de espera na queue
                final long waitingInQueue = System.currentTimeMillis() - generationTimes.remove(userIDU);
                totalTimeWaitingInQueue += waitingInQueue;
//                System.out.println("Tempo na fila: " + waitingInQueue + "ms");
                // Enviar a SDU do utilizador através no mySocket
                startTime = startTime == 0 ? System.currentTimeMillis() : startTime;
                sendToUDP(userIDU);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            totalTime = System.currentTimeMillis() - startTime;
        }
    }

    private void sendToUDP(DatagramPacket myIDU) {
        // Construir a myPDU - tipo I, nSeq dado por counter
        final int seq = counter % 2; // 0 ou 1
        final myPDU p = new myPDU(myIDU, myPDU.Type.I, seq);

        // Construir o datagrama UDP que vai transportar a myPDU
        // A myPDU é a SDU do protocolo UDP que a vai transportar
        final byte[] myPDUbytes = p.toBytes();
        final DatagramPacket iduUDP = new DatagramPacket(
            myPDUbytes, myPDUbytes.length, myIDU.getSocketAddress());

        // Implementação da recepcao de PDU de tipo R
        final byte[] rdata = new byte[myPDU.HEADER_SIZE];
        final DatagramPacket rUDP = new DatagramPacket(rdata, rdata.length);
        // tempo em que foi enviado e contagem única
        final long timeSent = System.currentTimeMillis();
        counter++;
        boolean ackReceived = false;

        try {
            do {
                try {
                    // Pedido para enviar IDU do protocolo UDP
                    local.send(iduUDP);
                    repetitionCounter++; // contagem de envios com repetições
                    // Escreve log
                    log("MS", "UDP", (myPDU.Type.I).toString(), p.getSeq());
                    local.receive(rUDP);
                    ackReceived = true;
                } catch (SocketTimeoutException e) {
                    // Caso em que ocorre o timeout.
                    log("MS", "MS", "T", 1);
                    e.printStackTrace();
                }
            } while (!ackReceived);
            totalTimeWaitingForAck += System.currentTimeMillis() - timeSent;
            // Create log
            final myPDU rPDU = new myPDU(rUDP);
            log("UDP", "MS", (myPDU.Type.R).toString(), rPDU.getSeq());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(DatagramPacket p) {
        try {
            // Colocar no HashMap de tempos de geraçao
            generationTimes.put(p, System.currentTimeMillis());
            // Colocar na fila para emissao pelo protocolo mySocket
            queue.put(p);
            // Criar o log
            log("APP", "MS", (myPDU.Type.I).toString(), counter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}