package view.networks.serializators;

import view.Command;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class EnumSerializator implements SerializatorInterface {
    public static void main(String[] args) {
        Enum f = Command.Ready;
        System.out.println(f);
        System.out.println(f.name());
        System.out.println(f.getDeclaringClass().getName());
        System.out.println(f.getClass());
        Serializator serializator = new Serializator();
        byte[] bytes = Serializator.debuild(f);
        System.out.println(Arrays.toString(bytes));
        Enum o = (Enum) Serializator.build(bytes);
        System.out.println(o);
    }

    public byte[] debuild(Object value) {
        Enum anEnum = (Enum) value;
        StringSerializator stringSerializator = new StringSerializator();
        byte[] name = stringSerializator.debuild(anEnum.name());
        byte[] clazz = stringSerializator.debuild(anEnum.getDeclaringClass().getName());
        byte[] res = new byte[5 + name.length + clazz.length];
        byte[] len = Util.convertIntToBytes(5 + name.length + clazz.length);
        res[0] = Serializator.ENUM;
        System.arraycopy(len, 0, res, 1, 4);
        System.arraycopy(clazz, 0, res, 5, clazz.length);
        System.arraycopy(name, 0, res, 5 + clazz.length, name.length);
        return res;

    }

    public Object build(byte[] bytes) {
        return build(bytes, 0);
    }

    public Object build(byte[] bytes, int off) {
        StringSerializator stringSerializator = new StringSerializator();
        String clazz = (String) stringSerializator.build(bytes, 5 + off);
        int clazzL = Util.convertBytesToInt(bytes, 5 + 1 + off);
        System.out.println(clazzL);
        String name = (String) stringSerializator.build(bytes, 5 + clazzL + off);
        Enum res = null;
        try {
            res = Enum.valueOf((Class<? extends Enum>) Class.forName(clazz), name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return res;
    }
}
