package view.networks.serializators;

/**
 * @author Alexander Vlasov
 */
public class NotExpectedContent extends RuntimeException {
    public NotExpectedContent(String message) {
        super(message);
    }
}
