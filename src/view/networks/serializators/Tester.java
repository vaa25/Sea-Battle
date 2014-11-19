package view.networks.serializators;

import java.util.Arrays;

/**
 * @author Alexander Vlasov
 */
public class Tester {
    public static void main(String[] args) {

        test(new Serializator(), new TestObject());

    }

    private static void test(Serializator serializator, Object object) {
        System.out.println(serializator.getClass().getSimpleName());
        byte[] bytes = serializator.debuild(object);
        System.out.println(bytes.length + " " + Arrays.toString(bytes));

        System.out.println(serializator.build(bytes));
        System.out.println();
    }

}
