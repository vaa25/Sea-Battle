package view.networks.serializators;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class BigDecimalSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        BigDecimal bigDecimal = new BigDecimal("445555555555555555555876597658975908756785697859765976597659056");

        byte[] bytes = serializator.debuild(bigDecimal);
        System.out.println();
        System.out.println(Arrays.toString(bytes));
        System.out.println(serializator.build(bytes));
    }

    public byte[] debuild(Object k) {

        byte[] value = new StringSerializator().debuild(k.toString());
        byte[] res = new byte[5 + value.length];
        res[0] = Serializator.BIGDECIMAL;
        byte[] length = Serializator.setLength(res.length);
        System.arraycopy(length, 0, res, 1, 4);
        System.arraycopy(value, 0, res, 5, value.length);
        return res;
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {

        String value = (String) new StringSerializator().build(bytes, 5 + off);
        return new BigDecimal(value);
    }
}