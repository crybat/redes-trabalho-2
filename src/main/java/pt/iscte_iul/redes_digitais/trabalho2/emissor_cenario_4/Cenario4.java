package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_4;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Cenario4 {
    public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
        final String[] arrivalRates = new String[] {
            "0.1", "0.25", "0.5", "0.75"
        };
        for (String arrivalRate : arrivalRates) {
            Emissor4App.main(new String[]{arrivalRate});
            System.out.println("----");
        }
    }
}
