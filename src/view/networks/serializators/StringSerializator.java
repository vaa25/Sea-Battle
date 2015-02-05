package view.networks.serializators;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * @author Alexander Vlasov
 */
public class StringSerializator implements SerializatorInterface {

    public static void main(String[] args) {
        Serializator serializator = new Serializator();
        byte[] bytes = Serializator.debuild("Саша the best");
        System.out.println(Arrays.toString(bytes));
        System.out.println(Serializator.build(bytes));
    }

    public byte[] debuild(Object value) {
        byte[] stringB = null;
        try {
            stringB = ((String) value).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] res = new byte[5 + stringB.length];
        byte[] len = Util.convertIntToBytes(res.length);
        res[0] = Serializator.STRING;
        System.arraycopy(len, 0, res, 1, 4);
        System.arraycopy(stringB, 0, res, 5, stringB.length);
        return res;

    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        int start = off + 5;
        int len = Util.convertBytesToInt(bytes, off + 1) - 5;
        String res = Charset.forName("UTF-8").decode(ByteBuffer.wrap(bytes, start, len)).toString();
        return res;
    }


}
