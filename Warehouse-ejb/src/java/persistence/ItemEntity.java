/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca položku v databáze
@Entity
public class ItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int aisle;
    private int rack;
    private int shelf;
    private int quantity;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date expDate;
    @ManyToOne
    private MasterDataEntity masterData;

    //Bezparametrický konštruktor
    public ItemEntity() {
    }

    //Konštruktor s parametrami - id uličky, id regálu, id poličky, počet, dátum spotreby, prislúchajúce master data
    public ItemEntity(int aisle, int rack, int shelf, int quantity, Date expDate, MasterDataEntity data) {
        this.aisle = aisle;
        this.rack = rack;
        this.shelf = shelf;
        this.quantity = quantity;
        this.expDate = expDate;
        this.masterData = data;
    }

    //Vracia id položky
    public int getId() {
        return id;
    }

    //Vracia id uličky,v ktorej sa nachádza
    public int getAisle() {
        return aisle;
    }

    //Vracia dátum spotreby
    public Date getExpDate() {
        return expDate;
    }

    //Vracia počet položiek
    public int getQuantity() {
        return quantity;
    }

    //Vracia id regálu, v ktorom sa nachádza položka
    public int getRack() {
        return rack;
    }

    //Vracia is poločky, kde sa nachádza položka
    public int getShelf() {
        return shelf;
    }

    public MasterDataEntity getMasterData() {
        return masterData;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemEntity)) {
            return false;
        }
        ItemEntity other = (ItemEntity) object;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "database.ItemEntity[ id=" + id + " ]";
    }
}