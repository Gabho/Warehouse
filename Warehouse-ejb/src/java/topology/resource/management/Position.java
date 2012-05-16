/*
 * Reprezentation of a position holder.
 */
package topology.resource.management;

/**
 * Reprezentácia pozície položky v sklade.
 * Pozostáva z troch čísel, ktoré predstavujú identifikátory
 * jednotlivých úložných objektov (uličky, regále, poličky).
 * @author Martin Lofaj
 */
public class Position {
    private int shelf;
    private int rack;
    private int aisle;

    /**
     * Konštruktor pozície.
     * @param shelf id poličky.
     * @param rack id regálu.
     * @param aisle id uličky.
     */
    public Position(int shelf, int rack, int aisle) {
        this.shelf = shelf;
        this.rack = rack;
        this.aisle = aisle;
    }

    /**
     * Vráti identifikátor poličky.
     * @return id poličky.
     */
    public int getShelf() {
        return shelf;
    }

    /**
     * Vráti identifikátor regálu.
     * @return id regálu.
     */
    public int getRack() {
        return rack;
    }

    /**
     * Vráti identifikátor uličky.
     * @return id uličky.
     */
    public int getAisle() {
        return aisle;
    }
}
