package common;

/**
 * интерфейс получателя сообщения из сети. used by MessageReceiver
 *
 * @author Alexander Vlasov
 */
public interface MessageGetter {
    public void getMessage(Message message);

    public String getName();
}
