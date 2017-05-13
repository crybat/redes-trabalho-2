package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_2;

import java.io.IOException;

public class Cenario2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        final String[] sizes = new String[] {
            "10",
            "20",
            "50",
            "100",
            "200",
            "500",
            "1000",
            "2000"
        };
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
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Pacote " + sizes[i] + " bytes");
            Process pr = Runtime.getRuntime().exec(ipfw[i]);
            pr.waitFor();
            Emissor2App.main(new String[]{sizes[i]});
            System.out.println("----------------------------------------------");
        }
    }
}
