package pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_2;

import pt.iscte_iul.redes_digitais.trabalho2.emissor_cenario_1.EmissorApp;
import pt.iscte_iul.redes_digitais.trabalho2.lab5.myPDU;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import static java.lang.String.format;

/**
 * Cenário 2: TCP
 * Aceita um argumento tamanho, em bytes
 */
@SuppressWarnings("Duplicates")
public class Emissor2App {

    private static byte[] createBytes(int msgSize) {
        final byte[] data = new byte[msgSize];
        data[0] = ';';
        for (int j = 1; j < msgSize; j++) {
            data[j] = 'B';
        }
        return data;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final byte[] data = createBytes(Integer.valueOf(args[0]) + myPDU.HEADER_SIZE);
        // Identificação do SAP local
        final InetAddress loIP = InetAddress.getByName("127.0.0.1");
        // Local SAP - lo
        final InetSocketAddress lo = new InetSocketAddress(loIP, 20000);
        // Criação e binding do soket
        final Socket localSocket = new Socket();
        localSocket.bind(lo);
        System.out.println("Local: " + localSocket.getLocalSocketAddress().toString());

        // Identificação do SAP destino
        final InetAddress IPdestino = InetAddress.getByName("127.0.0.2");
        final InetSocketAddress SAPdestino = new InetSocketAddress(IPdestino, 30000);
        // Faz pedido de ligação ao servidor
        localSocket.connect(SAPdestino);
        System.out.println("Estabelecida ligação com: " + localSocket.getInetAddress().toString()
            + ":" + localSocket.getPort());
        final OutputStream os = localSocket.getOutputStream();
        final InputStream is = localSocket.getInputStream();

        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < EmissorApp.NMSG; i++) {
            // Escrita de dados do socket (via OutputStream)
            os.write(data);
        }

        final byte[] b = new byte[1];
        final int readBytes = is.read(b);
        assert b[0] == 'C';
        assert readBytes == 1;
        // Fecho da ligação
        System.out.println("Fecho da ligação!");
        System.out.println(format("Tempo total: %d ms", System.currentTimeMillis() - startTime));
    }
}
