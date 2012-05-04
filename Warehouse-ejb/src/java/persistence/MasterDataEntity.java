/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Gabo
 */
@Entity
public class MasterDataEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(mappedBy = "masterData", cascade=CascadeType.REMOVE)
    private List<ItemEntity> itemEntities;
    @Id
    private String id;
    private String description;
    private int quantity;
    
    public MasterDataEntity() {
    }
    
    public MasterDataEntity(String id ,String description){
        this.id = id;
        this.description = description;
    }
    
    public MasterDataEntity(String id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemEntity> getItemEntitys() {
        return itemEntities;
    }

    public void setItemEntitys(List<ItemEntity> itemEntitys) {
        this.itemEntities = itemEntitys;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public void updateQuantity(){
        setQuantity(itemEntities.size());
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
