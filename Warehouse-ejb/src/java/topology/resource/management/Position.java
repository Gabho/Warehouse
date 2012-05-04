/*
 * Reprezentation of a position holder.
 */
package topology.resource.management;

/**
 *
 * @author Martin Lofaj
 */
public class Position {
    private int shelf;
    private int rack;
    private int aisle;

    public Position(int shelf, int rack, int aisle) {
        this.shelf = shelf;
        this.rack = rack;
        this.aisle = aisle;
    }

    /**
     * @return the shelf
     */
    public int getShelf() {
        return shelf;
    }

    /**
     * @return the rack
     */
    public int getRack() {
        return rack;
    }

    /**
     * @return the aisle
     */
    public int getAisle() {
        return aisle;
    }
}
