package pt.iscte_iul.redes_digitais.trabalho2.producer;

public class Producer {
    public final static int NMSG = 250;

    public static void main(String[] args) {
        System.out.println(args[0]); // ARRIVAL_RATE
        final double arrivalRate = Double.parseDouble(args[0]);
        System.out.println(args[1]);
        final int dataSize = Integer.parseInt(args[0]) * 8;

    }
}
