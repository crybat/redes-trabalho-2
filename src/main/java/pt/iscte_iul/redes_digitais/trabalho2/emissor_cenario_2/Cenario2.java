package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_2;

import java.io.IOException;

public class Cenario2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String[] sizes = new String[] {
            "10", "20", "50", "100", "200",
            "500", "1000", "2000"
        };
        for (String size : sizes) {
            System.out.println("Current: " + size);
            Emissor2App.main(new String[]{size});
            System.out.println("-----");
        }
    }
}
