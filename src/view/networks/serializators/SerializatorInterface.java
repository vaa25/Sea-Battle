package view.networks.serializators;

/**
 * @author Alexander Vlasov
 */
public interface SerializatorInterface {
    Object build(byte[] bytes);

    byte[] debuild(Object object);
}
