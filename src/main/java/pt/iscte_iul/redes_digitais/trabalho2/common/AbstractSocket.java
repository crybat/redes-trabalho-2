package pt.iscte_iul.redes_digitais.trabalho2.common;

import pt.iscte_iul.redes_digitais.trabalho2.lab5.mySocket;

import java.net.InetSocketAddress;

public abstract class AbstractSocket extends mySocket {
    public static final int REPORT_INTERVAL = 1;
    public static final int MAXDATA = 2000 * 8;

    public AbstractSocket(InetSocketAddress localAddress, String file) {
        super(localAddress, file);
    }
}
