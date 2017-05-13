package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_3;

import pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1.EmissorApp;
import pt.iscte_iul.redes_digitais.trabalho2.lab5.myPDU;
import pt.iscte_iul.redes_digitais.trabalho2.lab5.mySocketClient;

import java.net.*;

import static java.lang.String.format;
import static pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1.EmissorApp.NMSG;

/**
 * Cenário 3 D/D/1
 * Aceita um argumento, taxa de utilização do sistema, P =  {0.1,0.25,0.5,0.75}
 */
@SuppressWarnings("Duplicates")
public class Emissor3App {

    private static final int DATA_SIZE = 200;
    private static final int KBPS = 200000;

    public static void main(String[] args)
        throws UnknownHostException, InterruptedException, SocketException {
//        System.out.println(Arrays.toString(args));
        final int pduSize = DATA_SIZE * 8 + myPDU.HEADER_SIZE * 8;
        final double serviceRate = KBPS / pduSize;
        final double arrivalRate = Double.valueOf(args[0]) * serviceRate;
        final long messageGenerationInterval = (long) (1000 * (1 / arrivalRate)); // * 1000 para millis
        final byte[] data = EmissorApp.createBytes(DATA_SIZE);

        // Identificação do SAP local
        final InetAddress loIP = InetAddress.getByName("127.0.0.1");
        // Local SAP - lo; SAP destino .
        final InetSocketAddress lo = new InetSocketAddress(loIP, 20000);
        // Local socket - localSocket
        final int timeout = (int) (Double.parseDouble(args[1]) * 1000);
        final mySocketClient localSocket = new mySocketClient(lo, timeout);
        System.out.println("LocalIP: " + localSocket.getLocalAddress().toString());
        System.out.println("LocalPort: " + localSocket.getLocalPort());
        System.out.println(format("PDU Size: %d bits; Data size: %d bits", pduSize, DATA_SIZE * 8));
        System.out.println(format("Sleep time: %d ms", messageGenerationInterval));


        InetAddress IPdestino = InetAddress.getByName("127.0.0.2");
        InetSocketAddress SAPdestino = new InetSocketAddress(IPdestino, 30000);

        sendData(localSocket, SAPdestino, data, messageGenerationInterval);
        localSocket.join();

        System.out.println(format("Total time: %dms", localSocket.totalTime));
        System.out.println(format("Total time waiting for ack: %dms", localSocket.totalTimeWaitingForAck));
        System.out.println(format("Total time waiting in queue: %dms", localSocket.totalTimeWaitingInQueue));
        System.out.println(format("Total unique packets sent: %d", localSocket.counter));
        System.out.println(format("Total packets sent: %d", localSocket.repetitionCounter));
    }

    private static void sendData(
        mySocketClient localSocket, InetSocketAddress SAPdestino,
        byte[] data, long sleepTime
    ) throws UnknownHostException, InterruptedException {

        // Envio de NMSG mensagens com um tamanho de args[1] bytes cada
        for (int i = 0; i < NMSG; i++) {
            // Identificação do SAP destino
            // Criação do datagrama UDP
            final DatagramPacket p = new DatagramPacket(data, data.length, SAPdestino);

            // Envio do datagrama UDP
            localSocket.send(p);

            // Tempo de espera entre gerações de pacotes - M (intervalo exponencial negativo)
            //interval = -1.0/ARRIVAL_RATE*Math.log(Math.random());
            Thread.sleep(sleepTime);
        }
    }
}
