package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1;

import java.net.SocketException;
import java.net.UnknownHostException;

public class Cenario1 {
    public static void main(String[] args) throws InterruptedException, SocketException, UnknownHostException {
        final String[] sizes = new String[] {
            "10", "20", "50", "100", "200",
            "500", "1000", "2000"
        };
        for (String size : sizes) {
            EmissorApp.main(new String[]{size});
            System.out.println("-----");
        }
    }
}
