package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_3;

import java.io.IOException;

public class Cenario3 {
    public static void main(String[] args) throws InterruptedException, IOException {
        final String[] arrivalRates = new String[] {
            "0.1", "0.25", "0.5", "0.75"
        };
        final String timeout = "0.0964";
        final String[] ipfw = new String[] {
            "/bin/bash",
            "-c",
            "echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.01713164198 0.001279187541"
        };
        for (int i = 0; i < arrivalRates.length; i++) {
            Process pr = Runtime.getRuntime().exec(ipfw);
            pr.waitFor();
            Emissor3App.main(new String[]{arrivalRates[i], timeout});
            System.out.println("----------------------------------------------");
        }
    }
}
