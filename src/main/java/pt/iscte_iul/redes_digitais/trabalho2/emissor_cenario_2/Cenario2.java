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
//        0
//        final String[][] ipfw = new String[][] {
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
//        };
        //        10^-5
//        final String[][] ipfw = new String[][] {
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.003673255431 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.004470002069 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.006856421148 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.01082108068 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.0187029818 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.04197385907 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.07953878546 0.002875871137"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.1503075465 0.002875871137"},
//        };
//        10^-4
        final String[][] ipfw = new String[][] {
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.03613288378 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.04381344155 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.06648984741 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.103095098 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.1720557358 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.3487237903 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.5634452329 0.02839063202"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.8038511453 0.02839063202"},
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
