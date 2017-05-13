package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Cenario1 {
    public static void main(String[] args) throws InterruptedException, IOException {
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
        final String[] timeouts = new String[] {
            "0.0584",
            "0.0604",
            "0.0664",
            "0.0764",
            "0.0964",
            "0.1564",
            "0.2564",
            "0.4564",
        };
//        10^-5
//        final String[][] ipfw = new String[][] {
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.002077848677 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.002875871137 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.005266111562 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.00923711966 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.01713164198 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.04043978264 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.07806486132 0.001279187541"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.1489469436 0.001279187541"},
//        };
//        10^-4
//        final String[][] ipfw = new String[][] {
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.02058619071 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.02839063202 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.05143279693 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.08862847183 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.1587014101 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.3382190317 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.5564038235 0.01271906031"},
//            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0.8006873627 0.01271906031"},
//        };
//        0
        final String[][] ipfw = new String[][] {
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
            {"/bin/bash","-c","echo P@ssw0rd | sudo -S ./config-link.sh 200000 5 0 0"},
        };

        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Pacote " + sizes[i] + " bytes");
            Process pr = Runtime.getRuntime().exec(ipfw[i]);
            pr.waitFor();

//        	BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//    		String line;
//    		while ( ( line = buf.readLine() ) != null )
//    		{
//    		  System.out.println(line);
//    		}

            EmissorApp.main(new String[]{sizes[i], timeouts[i]});
            System.out.println("----------------------------------------------");
        }
    }
}
