package view.networks.serializators;


import view.Person;
import view.networks.NetworkSpecial;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Vlasov
 */
public class TestObject extends Person {
    private static String string = "строка";
    private int anInt = 100;
    private List list;
    private NetworkSpecial networkSpecial;

    public TestObject() {
        super("Саша");
        setOnline(false);
        list = new ArrayList<>();
        list.add(new Person("Alex"));
        list.add(4);
        list.add(false);
        networkSpecial = NetworkSpecial.LostConnection;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "anInt=" + anInt +
                ", string=" + string +
                ", list=" + list.toString() +
                ", special=" + networkSpecial +
                '}';
    }
}
