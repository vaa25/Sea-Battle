package networks;

import common.Coord;
import common.ShootResult;

import java.io.Serializable;

/**
 * @author Alexander Vlasov
 */
public class Message implements Serializable {

    private MessageType type;
    private String text;
    private Coord coord;
    private ShootResult shootResult;
    private double random;

    public Message(String text) {
        type = MessageType.TEXT;
        this.text = text;
    }

    public Message(MessageType type) {
        if (type == MessageType.NOTIMEOUT) {
            this.type = type;
        } else throw new IllegalArgumentException("Wrong MessageType");
    }

    public Message(Coord coord) {
        type = MessageType.COORD;
        this.coord = coord;
    }

    public Message(double random) {
        type = MessageType.RANDOM;
        this.random = random;
    }

    public Message(ShootResult shootResult) {
        type = MessageType.SHOOTRESULT;
        this.shootResult = shootResult;
    }

    public MessageType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Coord getCoord() {
        return coord;
    }

    public ShootResult getShootResult() {
        return shootResult;
    }

    public double getRandom() {
        return random;
    }

    @Override
    public String toString() {
        switch (type) {
            case TEXT:
                return text;
            case COORD:
                return coord.toString();
            case SHOOTRESULT:
                return shootResult.toString();
            case NOTIMEOUT:
                return "NoTimeout";
            case RANDOM:
                return Double.toString(random);
        }
        return "Unknown message";
    }
}
