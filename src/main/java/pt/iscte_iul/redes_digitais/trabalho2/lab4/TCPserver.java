package pt.iscte_iul.redes_digitais.trabalho2.lab4;

import pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1.EmissorApp;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPserver {

    public static void main(String[] args) throws IOException {
        // Identificação do SAP local
        final InetAddress loIP = InetAddress.getByName("127.0.0.2");
        // Local SAP - lo
        final InetSocketAddress lo = new InetSocketAddress(loIP, 30000);
        // Criação e binding do soket
        // Local socket - localSocket
        final ServerSocket localSocket = new ServerSocket();
        localSocket.bind(lo);
        System.out.println("Local: " + localSocket.getLocalSocketAddress().toString());

        while (true) {
            // Espera por pedido de ligação
            final Socket newSocket = localSocket.accept();
            System.out.println("Estabelecida ligação com: " + newSocket.getRemoteSocketAddress().toString());
            final long beginTimeCiclo = System.currentTimeMillis();

            // InputStream do novo socket
            final InputStream is = newSocket.getInputStream();
            final OutputStream os = newSocket.getOutputStream();
            // Ciclo de leitura de blocos até 10000 bytes cada
            for (int i = 0; i != EmissorApp.NMSG; ) {
                final byte[] data = new byte[2016];

                final long beginTime = System.currentTimeMillis();
                // Leitura de dados do socket (via InputStream)
                final int bytesRead = is.read(data);
                final long endTime = System.currentTimeMillis();

                for (byte b : data) {
                    if (b == ';') {
                        i++;
                    }
                }

                System.out.println("Count: " + i);
                if (bytesRead <= 0) { // -1 indica fecho da ligação pelo client
                    System.out.println("Client fechou a ligação!");
                    break;
                } else {
                    System.out.println("Recebidos " + bytesRead + " bytes em " +
                        (endTime - beginTime) + " miliseg.");
                }
            }
            os.write(new byte[]{'C'});
            is.close();
            os.close();
            // Fecho da logação
            newSocket.close();

            long endTimeCiclo = System.currentTimeMillis();
            System.out.println("Fecho da ligação! (Duração ciclo " +
                (endTimeCiclo - beginTimeCiclo) + " miliseg.)");
        }
    }
}
