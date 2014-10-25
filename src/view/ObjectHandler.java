package view;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;

/**
 * @author Alexander Vlasov
 */
public class ObjectHandler extends Service {
    private BlockingQueue queue;

    public ObjectHandler(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                return queue.take();
            }
        };
    }
}