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
                System.out.println("NoTimeOut (" + Thread.currentThread().getName() + ") InterruptedException " + interrupt);
                return;
            }

            System.out.println("NoTimeOut (" + Thread.currentThread().getName() + ") interrupt = " + interrupt);
            if (!interrupt) {
                sender.sendMessage(new Message(MessageType.NOTIMEOUT));
            } else {
                System.out.println("NoTimeOut (" + Thread.currentThread().getName() + ") must return = " + interrupt);
                return;
            }

        }
    }

    public void interrupt() {
        System.out.println("Set NoTimeOut (" + Thread.currentThread().getName() + ") interrupt = true");
        interrupt = true;
        Thread.currentThread().interrupt();
    }
}
