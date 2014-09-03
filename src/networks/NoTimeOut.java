package networks;

/**
 * Посылает раз в 20 секунд пустой Message чтобы соединение не отвалилось
 *
 * @author Alexander Vlasov
 */
public class NoTimeOut implements Runnable {
    private MessageSender sender;

    public NoTimeOut(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                break;
            }
//            System.out.println("Пытаюсь послать NoTimeOut ");
            sender.sendMessage(new Message(MessageType.NOTIMEOUT));
//            System.out.println( "NoTimeOut послан " );

        }
    }
}
