package view.networks.serializators;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class LongSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = serializator.debuild(-123456789123456789L);
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {
        long l = (long) k;
        byte[] res = new byte[9];
        res[0] = Serializator.LONG;
        res[1] = (byte) (l);
        res[2] = (byte) (l >> 8);
        res[3] = (byte) (l >> 16);
        res[4] = (byte) (l >> 24);
        res[5] = (byte) (l >> 32);
        res[6] = (byte) (l >> 40);
        res[7] = (byte) (l >> 48);
        res[8] = (byte) (l >> 56);
        return res;
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        return (Byte.toUnsignedLong(bytes[off + 8]) << 56) +
                (Byte.toUnsignedLong(bytes[off + 7]) << 48) +
                (Byte.toUnsignedLong(bytes[off + 6]) << 40) +
                (Byte.toUnsignedLong(bytes[off + 5]) << 32) +
                (Byte.toUnsignedLong(bytes[off + 4]) << 24) +
                (Byte.toUnsignedLong(bytes[off + 3]) << 16) +
                (Byte.toUnsignedLong(bytes[off + 2]) << 8) +
                Byte.toUnsignedLong(bytes[off + 1]);

    }
}