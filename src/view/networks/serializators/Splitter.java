package view.networks.serializators;

/**
 * @author Alexander Vlasov
 */
public class Splitter {
    /**
     * Извлечение отдельных полей в виде массивов из главного массива
     * <p>
     * xx - код примитивного класса или маркер непримитивного
     * xx xx xx xx - длина итогового массива (int), извлекается  convertBytesToInt(byte[] bytes)
     * xx - длина имени класса (byte)
     * xx ... xx - массив имени класса (String)
     * xx - количество полей (byte)
     * xx ... xx - поля
     * поля:
     * xx - определенный примитивный класс или нет
     * xx xx xx xx - длина массива поля, извлекается  convertBytesToInt(byte[] bytes)
     * xx - длина имени класса поля (byte)
     * xx ... xx - массив имени класса поля
     * xx ... xx - массив значения поля, может содержать свои поля.
     * <p>
     * для примитивных
     * xx - код примитивного класса
     * xx ... xx - данные фиксированной для каждого примитивного класса длины
     * *длина имени класса (поля) включает в себя себя и остальные данные, принадлежащие классу (полю)
     *
     * @param bytes главный массив
     *
     * @return массив массивов полей
     */
    public static byte[][] split(byte[] bytes) {
        return split(bytes, 0);
    }

    public static byte[][] split(byte[] bytes, int off) {
        int startIndex = off + 6 + Util.convertBytesToInt(bytes, off + 6);
        int amount = bytes[startIndex - 1];
        byte[][] res = new byte[amount][];
        for (int i = 0; i < amount; i++) {
            int len = selectLength(bytes, startIndex);
            byte[] body = new byte[len];
            System.arraycopy(bytes, startIndex, body, 0, len);
            res[i] = body;
            startIndex += len;
        }
        return res;
    }

    private static int selectLength(byte[] bytes, int startIndex) {
        int len = Serializator.getLength(bytes[startIndex]);
        if (len == -1) {
            len = Util.convertBytesToInt(bytes, startIndex + 1);
        }
        return len;
    }
}
