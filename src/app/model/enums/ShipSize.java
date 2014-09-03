package app.model.enums;

/**
 * Representation of ship size
 */
public enum ShipSize {
    ONE(1), TWO(2), THREE(3), FOUR(4);

    private final int value;

    private ShipSize(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}
