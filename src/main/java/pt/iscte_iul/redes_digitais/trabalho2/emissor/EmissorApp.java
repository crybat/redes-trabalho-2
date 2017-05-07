package pt.iscte_iul.redes_digitais.trabalho2.emissor;

import pt.iscte_iul.redes_digitais.trabalho2.lab5.mySocketClient;

import java.net.*;

import static java.lang.String.format;

public class EmissorApp {

    public static final int NMSG = 250;
    private static long totalTime = 0;

    public static void main(String[] args)
        throws UnknownHostException, InterruptedException, SocketException {
//        System.out.println(Arrays.toString(args));
        final double arrivalRate = Double.valueOf(args[0]);
        final Integer msgSize = Integer.valueOf(args[1]);
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

        sendData(localSocket, SAPdestino, data, arrivalRate);
        localSocket.join();

        System.out.println(format("Total time: %dms", localSocket.totalTime));
        System.out.println(format("Total time waiting for ack: %dms", localSocket.totalTimeWaitingForAck));
        System.out.println(format("Total time waiting in queue: %dms", localSocket.totalTimeWaitingInQueue));
        System.out.println(format("Total unique packets sent: %d", localSocket.counter));
        System.out.println(format("Total packets sent: %d", localSocket.repetitionCounter));
    }

    private static void sendData(
        mySocketClient localSocket, InetSocketAddress SAPdestino,
        byte[] data, double arrivalRate
    ) throws UnknownHostException, InterruptedException {

        // Envio de NMSG mensagens com um tamanho de args[1] bytes cada
        for (int i = 0; i < NMSG; i++) {
            // Identificação do SAP destino
            // Criação do datagrama UDP
            final DatagramPacket p = new DatagramPacket(data, data.length, SAPdestino);

            final long beginTime = System.currentTimeMillis();
            // Envio do datagrama UDP
            localSocket.send(p);
            final long endTime = System.currentTimeMillis();
            totalTime += endTime - beginTime;

//            System.out.println("Colocados no SAP " + p.getLength() + " bytes com destino a "
//                + p.getAddress().toString() + ":" + p.getPort() + " em " +
//                (endTime - beginTime) + " miliseg");

            if (arrivalRate > 0) {
                // Tempo de espera entre gerações de pacotes - D (intervalo fixo)
                final double interval = (1.0 / arrivalRate);
                // Tempo de espera entre gerações de pacotes - M (intervalo exponencial negativo)
                //interval = -1.0/ARRIVAL_RATE*Math.log(Math.random());
                Thread.sleep((long) (interval * 1000.0)); // Conversão para miliseg.
            }
        }
    }

    private static byte[] createBytes(int msgSize) {
        final byte[] data = new byte[msgSize];
        for (int j = 0; j < msgSize; j++) {
            data[j] = 'B';
        }
        return data;
    }
}
