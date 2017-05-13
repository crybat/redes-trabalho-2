package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_3;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Cenario3 {
    public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
        final String[] arrivalRates = new String[] {
            "0.1", "0.25", "0.5", "0.75"
        };
        final String timeout = "0.0902";
        for (String arrivalRate : arrivalRates) {
            Emissor3App.main(new String[]{arrivalRate, timeout});
            System.out.println("-----");
        }
    }
}
