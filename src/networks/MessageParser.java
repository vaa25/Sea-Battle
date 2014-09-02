package networks;

import common.Coord;
import common.ShootResult;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Собирает сообщения, раскидывает по очередям и позволяет забирать их по отдельности.
 *
 * @author Alexander Vlasov
 */
public class MessageParser {
    private BlockingQueue<ShootResult> shootResults;
    private BlockingQueue<Coord> coords;
    private BlockingQueue<String> texts;
    private BlockingQueue<Double> randoms;

    public MessageParser() {
        shootResults = new LinkedBlockingQueue<>();
        coords = new LinkedBlockingQueue<>();
        texts = new LinkedBlockingQueue<>();
        randoms = new LinkedBlockingQueue<>();
    }

    public synchronized void addMessage(Message message) throws InterruptedException {
        switch (message.getType()) {
            case TEXT:
                texts.put(message.getText());
                break;
            case COORD:
                coords.put(message.getCoord());
                break;
            case SHOOTRESULT:
                shootResults.put(message.getShootResult());
                break;
            case RANDOM:
                randoms.put(message.getRandom());
                break;
        }
    }

    public ShootResult takeShootResult() {
        ShootResult res = null;
        try {
            res = shootResults.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public double takeRandom() {
        double res = 0;
        try {
            res = randoms.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Coord takeCoord() {
        Coord res = null;
        try {
            res = coords.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String takeText() {
        String res = null;
        try {
            res = texts.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
}
