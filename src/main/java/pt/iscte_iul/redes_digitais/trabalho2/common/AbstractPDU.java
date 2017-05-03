package pt.iscte_iul.redes_digitais.trabalho2.common;

import java.net.DatagramPacket;

public abstract class AbstractPDU {
    public enum Type {
        Data(0), Response(0);

        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}
