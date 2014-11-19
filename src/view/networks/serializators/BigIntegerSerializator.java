package view.networks.serializators;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class BigIntegerSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        BigInteger bigInteger = new BigInteger("445555555555555555555876597658975908756785697859765976597659056");

        byte[] bytes = serializator.debuild(bigInteger);
        System.out.println();
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {
        byte[] value = ((BigInteger) k).toByteArray();
        byte[] res = new byte[5 + value.length];
        res[0] = Serializator.BIGINTEGER;
        byte[] length = Serializator.setLength(res.length);
        System.arraycopy(length, 0, res, 1, 4);
        System.arraycopy(value, 0, res, 5, value.length);
        return res;
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        int length = Serializator.getLength(bytes, 1) - 5;
        byte[] value = new byte[length];
        System.arraycopy(bytes, off + 5, value, 0, length);
        return new BigInteger(value);
    }
}