/*
 * Actual item implementation
 */
package topology.resource.management;

import java.util.Date;

/**
 * Implementácia položky tovaru.
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
     * Vytvorenie položky.
     * @param amount množstvo tovaru v položke.
     * @param type typ tovaru v položke.
     * @param decription popis tovaru.
     * @param expirationDate dátum spotreby.
     * @param position pozícia položky v sklade.
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
    
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public void setAmount(int amount) {
       this.amount = amount;
    }
}
