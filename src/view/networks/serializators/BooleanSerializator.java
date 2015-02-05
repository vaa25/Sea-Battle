package view.networks.serializators;

import java.util.Arrays;

/**
 * xx - код boolean
 * xx xx xx xx - длина массива поля, извлекается  convertBytesToInt(byte[] bytes)
 * xx - длина имени класса поля (byte)
 * xx ... xx - массив имени класса поля
 * xx ... xx - массив значения поля, может содержать свои поля.
 *
 * @author Alexander Vlasov
 */
public class BooleanSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = Serializator.debuild(false);
        System.out.println(Arrays.toString(bytes));
        System.out.println(Serializator.build(bytes));
    }

    public byte[] debuild(Object l) {
        byte[] res = new byte[2];
        res[0] = Serializator.BOOLEAN;
        res[1] = (byte) ((Boolean) l ? 1 : 0);
        return res;
    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        return bytes[off + 1] == 1;
    }
}