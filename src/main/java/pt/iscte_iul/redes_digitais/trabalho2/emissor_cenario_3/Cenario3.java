package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_3;

import java.io.IOException;

public class Cenario3 {
    public static void main(String[] args) throws InterruptedException, IOException {
        final String[] arrivalRates = new String[] {
            "0.1", "0.25", "0.5", "0.75"
        };
        final String timeout = "0.0902";
        final String[][] ipfw = new String[][] {
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.002716317715 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.003513829601 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.005902540753 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.009871008207 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.01776047963 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.04105370777 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.07865471398 0.001279187541"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.149491446 0.001279187541"},
        };
        for (int i = 0; i < arrivalRates.length; i++) {
            Process pr = Runtime.getRuntime().exec(ipfw[i]);
            pr.waitFor();
            Emissor3App.main(new String[]{arrivalRates[i], timeout});
            System.out.println("----------------------------------------------");
        }
    }
}
