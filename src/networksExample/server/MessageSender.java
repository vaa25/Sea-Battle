package networksExample.server;

import networks.Message;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;

public class MessageSender implements Runnable {

//	private static final Logger LOG = Logger.getLogger(ConnectionHandler.class.getName());

    private BlockingQueue<Message> buffer;
    private Collection<ConnectionHandler> handlers;

    public MessageSender(BlockingQueue<Message> buffer,
                         Collection<ConnectionHandler> handlers) {
        super();
        this.buffer = buffer;
        this.handlers = handlers;
    }

    @Override
    public void run() {
        while (true) {
            Message message;
            try {
                message = buffer.take();
            } catch (InterruptedException e) {
//				LOG.info("Message sender stoped");
                break;
            }
            for (ConnectionHandler handler : handlers) {
                handler.send(message);
            }
        }
    }

}
