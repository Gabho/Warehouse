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
 * @author Gabo
 */
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

    public ItemEntity() {
    }

    public ItemEntity(int aisle, int rack, int shelf, int quantity, Date expDate, MasterDataEntity data) {
        this.aisle = aisle;
        this.rack = rack;
        this.shelf = shelf;
        this.quantity = quantity;
        this.expDate = expDate;
        this.masterData = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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