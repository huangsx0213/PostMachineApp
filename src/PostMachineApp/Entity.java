/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp;

/**
 *
 * @author vhuang1
 */
public class Entity {

    Boolean EnableEntity;
    Integer EntityID;
    String EntityName;

    public Entity(Boolean EnableEntity, Integer EntityID, String EntityName) {
        this.EnableEntity = EnableEntity;
        this.EntityID = EntityID;
        this.EntityName = EntityName;
    }

    public Boolean getEnableEntity() {
        return EnableEntity;
    }

    public Integer getEntityID() {
        return EntityID;
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEnableEntity(Boolean EnableEntity) {
        this.EnableEntity = EnableEntity;
    }

    public void setEntityID(Integer EntityID) {
        this.EntityID = EntityID;
    }

    public void setEntityName(String EntityName) {
        this.EntityName = EntityName;
    }
    
}
