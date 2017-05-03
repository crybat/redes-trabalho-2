package pt.iscte_iul.redes_digitais.trabalho2.client;

import java.net.InetSocketAddress;

public class Client {
    public static final int NMSG = 250;
    public static int msgSize;

    public static void main(String[] args) {
        System.out.println(args[0]); // ARRIVAL_RATE
        final double arrivalRate = Double.parseDouble(args[0]);
        System.out.println(args[1]);
        final int dataSize = Integer.parseInt(args[1] == null ? "0" : args[1]) * 8;
        msgSize = dataSize + 4 + 4 + 8;
//        final ClientSocket cs = new ClientSocket(new InetSocketAddress(0), msgSize);
    }
}
