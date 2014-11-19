package view.networks.serializators;

/**
 * @author Alexander Vlasov
 */
public class Singleton {
    private static Singleton ourInstance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return ourInstance;
    }
}
