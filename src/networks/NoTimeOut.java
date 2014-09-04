package networks;

/**
 * Посылает раз в 20 секунд пустой Message чтобы соединение не отвалилось
 *
 * @author Alexander Vlasov
 */
public class NoTimeOut implements Runnable {
    private MessageSender sender;
    private boolean interrupt;
    public NoTimeOut(MessageSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (!interrupt) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                return;
            }

//            System.out.println("Пытаюсь послать NoTimeOut ");
            if (!interrupt) sender.sendMessage(new Message(MessageType.NOTIMEOUT));
//            System.out.println( "NoTimeOut послан " );

        }
    }

    public void interrupt() {
        interrupt = true;
        Thread.currentThread().interrupt();
    }
}
