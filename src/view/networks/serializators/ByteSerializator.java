package view.networks.serializators;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class ByteSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = serializator.debuild(123);
        System.out.println();
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {
        return new byte[]{Serializator.BYTE, (byte) k};

    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        return bytes[1 + off];
    }
}