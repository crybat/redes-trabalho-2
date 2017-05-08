package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1;

import pt.iscte_iul.redes_digitais.trabalho2.lab5.mySocketClient;

import java.net.*;

import static java.lang.String.format;

@SuppressWarnings("Duplicates")
public class EmissorApp {

    public static final int NMSG = 250;

    public static void main(String[] args)
        throws UnknownHostException, InterruptedException, SocketException {
//        System.out.println(Arrays.toString(args));
        final Integer msgSize = Integer.valueOf(args[0]);
        final byte[] data = createBytes(msgSize);

        // Local SAP - lo; SAP destino . 
        // Local socket - localSocket
        InetSocketAddress lo;
        mySocketClient localSocket;

        // Identificação do SAP local
        InetAddress loIP = InetAddress.getByName("127.0.0.1");
        lo = new InetSocketAddress(loIP, 20000);

        // Criação e binding do soket
        localSocket = new mySocketClient(lo);
        System.out.println("LocalIP: " + localSocket.getLocalAddress().toString());
        System.out.println("LocalPort: " + localSocket.getLocalPort());


        InetAddress IPdestino = InetAddress.getByName("127.0.0.2");
        InetSocketAddress SAPdestino = new InetSocketAddress(IPdestino, 30000);

        sendData(localSocket, SAPdestino, data);
        localSocket.join();

        System.out.println(format("Total time: %dms", localSocket.totalTime));
        System.out.println(format("Total time waiting for ack: %dms", localSocket.totalTimeWaitingForAck));
        System.out.println(format("Total time waiting in queue: %dms", localSocket.totalTimeWaitingInQueue));
        System.out.println(format("Total unique packets sent: %d", localSocket.counter));
        System.out.println(format("Total packets sent: %d", localSocket.repetitionCounter));
    }

    private static void sendData(
        mySocketClient localSocket, InetSocketAddress SAPdestino,
        byte[] data
    ) throws UnknownHostException, InterruptedException {

        // Envio de NMSG mensagens com um tamanho de args[1] bytes cada
        for (int i = 0; i < NMSG; i++) {
            // Identificação do SAP destino
            // Criação do datagrama UDP
            final DatagramPacket p = new DatagramPacket(data, data.length, SAPdestino);

            // Envio do datagrama UDP
            localSocket.send(p);
        }
    }

    public static byte[] createBytes(int msgSize) {
        final byte[] data = new byte[msgSize];
        for (int j = 0; j < msgSize; j++) {
            data[j] = 'B';
        }
        return data;
    }
}
