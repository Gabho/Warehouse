package persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Gabriel Cervenak
 */
//Trieda predstavujúca master data
@Entity
public class MasterDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "masterData", cascade=CascadeType.REMOVE)
    private List<ItemEntity> itemEntities;
    @Id
    private String id;
    private String description;
    private int quantity;
    
    //Bezparametrický konštruktor triedy MasterDataEntity
    public MasterDataEntity() {
    }
    
    //Konštruktor s dvoma parametrami - meno a opis
    public MasterDataEntity(String id ,String description){
        this.id = id;
        this.description = description;
    }

    //Vracia opis dat
    public String getDescription() {
        return description;
    }

    //Vracia id master dat
    public String getId() {
        return id;
    }

    //Vracia zoznam položiek prislúchajúcich k master datam
    public List<ItemEntity> getItemEntitys() {
        return itemEntities;
    }

    //Vracia počet položiek
    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MasterDataEntity)) {
            return false;
        }
        MasterDataEntity other = (MasterDataEntity) object;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "database.MasterData[ id=" + id + " ]";
    }
    
}
