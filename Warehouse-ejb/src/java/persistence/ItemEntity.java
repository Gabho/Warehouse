package persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 * Serializovateľná trieda predstavujúca položku v databáze
 * @author Gabriel Cervenak
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

    /**
     * Bezparametrický konštruktor - vytvorí položku
     */
    public ItemEntity() {
    }

    /**
     * Vytvorí novú položku
     * @param aisle ulička, v ktorej sa nachádza položka
     * @param rack regál, v ktorom sa nachádza položka
     * @param shelf polička, na ktorej sa nachádza položka
     * @param quantity množstvo
     * @param expDate dátum spotreby
     * @param data master dáta danej položky
     */
    public ItemEntity(int aisle, int rack, int shelf, int quantity, Date expDate, MasterDataEntity data) {
        this.aisle = aisle;
        this.rack = rack;
        this.shelf = shelf;
        this.quantity = quantity;
        this.expDate = expDate;
        this.masterData = data;
    }

    /**
     * Vracia id položky
     * @return celé číslo reprezentujúce id položky
     */
    public int getId() {
        return id;
    }

    /**
     * Vracia id uličky, v ktorej sa položka nachádza
     * @return celé číslo reprezentujúce uličku
     */
    public int getAisle() {
        return aisle;
    }

    /**
     * Vracia dátum spotreby položky
     * @return dátum spotreby
     */
    public Date getExpDate() {
        return expDate;
    }

    /**
     * Vracia množstvo
     * @return celé číslo reprezentujúce množstvo
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Vracia id regálu, v ktorom sa položka nachádza
     * @return celé číslo reprezentujúce regál
     */
    public int getRack() {
        return rack;
    }

    /**
     * Vracia id poličky, na ktorej sa položka nachádza
     * @return celé číslo reprezentujúce poličku
     */
    public int getShelf() {
        return shelf;
    }

    /**
     * Vracia master dáta položky
     * @return master dáta
     */
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