package view.networks.serializators;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class ShortSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = serializator.debuild(1239);
        System.out.println();
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {
        int l = (int) k;
        byte[] res = new byte[3];
        res[0] = Serializator.SHORT;
        res[1] = (byte) (l);
        res[2] = (byte) (l >> 8);

        return res;
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        return (short) (Byte.toUnsignedInt(bytes[off + 2]) << 8) +
                Byte.toUnsignedInt(bytes[off + 1]);
    }
}