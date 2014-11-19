package view.networks.serializators;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class FloatSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = serializator.debuild(1239.487f);
        System.out.println();
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {
        int l = Float.floatToRawIntBits((float) k);
        byte[] res = new byte[5];
        res[0] = Serializator.FLOAT;
        res[1] = (byte) (l);
        res[2] = (byte) (l >> 8);
        res[3] = (byte) (l >> 16);
        res[4] = (byte) (l >> 24);
        return res;
    }

    public Object build(byte[] bytes) {

        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {

        return Float.intBitsToFloat((Byte.toUnsignedInt(bytes[off + 4]) << 24) +
                (Byte.toUnsignedInt(bytes[off + 3]) << 16) +
                (Byte.toUnsignedInt(bytes[off + 2]) << 8) +
                Byte.toUnsignedInt(bytes[off + 1]));
    }
}