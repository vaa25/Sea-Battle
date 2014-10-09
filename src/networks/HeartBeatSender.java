package networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Посылает раз в 20 секунд пустой Message чтобы соединение не отвалилось
 *
 * @author Alexander Vlasov
 */
public class HeartBeatSender implements Runnable {
    private ObjectSender sender;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //    private boolean interrupt;
    public HeartBeatSender(ObjectSender sender) {
        this.sender = sender;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                logger.error("HeartBeatSender (" + Thread.currentThread().getName() + ") InterruptedException: sleep interrupted ");
                break;
            }

//            logger.info("HeartBeatSender (" + Thread.currentThread().getName() + ") interrupt == " + interrupt);
//            if (!interrupt) {
            sender.sendMessage(Special.HeartBeat);
//            } else {
//                logger.info("HeartBeatSender (" + Thread.currentThread().getName() + ") must return = " + interrupt);
//                break;
//            }

        }
//        logger.info("HeartBeatSender (" + Thread.currentThread().getName() + ") returns");
    }

//    public void interrupt() {
//        logger.info("HeartBeatSender (" + Thread.currentThread().getName() + ")set interrupt = true");
//        interrupt = true;
//        Thread.currentThread().interrupt();
//    }
}
