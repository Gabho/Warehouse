/*
 * Actual item implementation
 */
package topology.resource.management;

import java.util.Date;
/**
 * Implementaion of item.
 * @author Martin Lofaj
 */

public class Item implements IItem{

    private int amount;
    private String type;
    private String decription;
    private Date expirationDate;
    private Position position;
    private int id;

    /**
     * Costructor for internal use of item. Object creation is 
     * based on data from database.
     */
    public Item() {
        //TODO: fetch data from database ?
    }

    /**
     * Constructor for inserting item into storage.
     * @param amount
     * @param type
     * @param decription
     * @param expirationDate
     * @param position 
     */
    public Item(int id, int amount, String type, String decription, 
            Date expirationDate, Position position) {
        this.amount = amount;
        this.type = type;
        this.decription = decription;
        this.expirationDate = expirationDate;
        this.position = position;
    }
        
    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getDescription() {
        return decription;
    }

    @Override
    public Date getExpiration() {
        return expirationDate;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
