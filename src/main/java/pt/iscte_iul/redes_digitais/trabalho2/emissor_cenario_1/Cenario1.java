package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Cenario1 {
    public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
        final String[] sizes = new String[] {
            "10", "20", "50", "100", "200",
            "500", "1000", "2000"
        };
        final String[] timeouts = new String[] {
            "0.0522",
            "0.0542",
            "0.0602",
            "0.0702",
            "0.0902",
            "0.1502",
            "0.2502",
            "0.4502"
        };
        for (int i = 0; i < sizes.length; i++) {
            EmissorApp.main(new String[]{sizes[i], timeouts[i]});
            System.out.println("-----");
        }
    }
}
