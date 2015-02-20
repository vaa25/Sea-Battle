package view.networks;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.networks.serializators.Serializator;
import view.networks.serializators.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.BlockingQueue;

/**
 * Принимает байты, преодбразовывает их в объекты с помощью Serializator и кидает в блокирующую очередь.
 * Запускается в отдельном потоке. При получении сиграла отсоединения с другой стороны или другой ошибке кидает
 * соответствующий NetworkSpecial в очередь и завершает свою работу.
 * @author Alexander Vlasov
 */
public class MyObjectInputStream implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private InputStream in;
    private byte[] data;
    private volatile boolean transferComplete;

    private boolean closed;

    private BlockingQueue queue;

    /**
     * Конструктор.
     *
     * @param in    поток, из которого нужно принимать данные
     * @param queue очередь, куда их нужно кидать. Очередь сначала обнуляется.
     */
    public MyObjectInputStream(InputStream in, BlockingQueue queue) {
        this.in = in;
        queue.clear();
        this.queue = queue;
    }

    @Override
    public void run() {
        transferComplete = false;
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            logger.error("Interrupt connection", e);
        }
        while (!transferComplete) {

            try {
                int value = in.read();
                if (value == -1) {
                    closed = true;
                    continue;
                }
                byte code = (byte) value;
                int len = Serializator.getLength(code);
                byte[] length = new byte[0];
                if (len == -1) {
                    length = new byte[4];
                    in.read(length);
                    len = Util.convertBytesToInt(length);
                }
                data = new byte[len];
                data[0] = code;
                System.arraycopy(length, 0, data, 1, length.length);
                in.read(data, 1 + length.length, len - (1 + length.length));
                Object received = Serializator.build(data);
                logger.info("Принял " + received);
                put(received);
                if (received == NetworkSpecial.Disconnect) {
                    closed=true;
                    break;
                }

            } catch (SocketTimeoutException | SocketException e) {
                logger.error("Connection reset", e);
                closed = true;
                put(NetworkSpecial.LostConnection);
                break;
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        logger.info("ends working");
        close();
    }

    private void close() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void put(Object o) {
        try {
            queue.put(o);
        } catch (InterruptedException e) {
            logger.error("Interrupted", e);
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return closed;
    }
}
