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
public class ControlProperty {

    Boolean EnableControlProperty;
    Integer ControlPropertyID;
    String ControlPropertyName;

    public ControlProperty(Boolean EnableControlProperty, Integer ControlPropertyID, String ControlPropertyName) {
        this.EnableControlProperty = EnableControlProperty;
        this.ControlPropertyID = ControlPropertyID;
        this.ControlPropertyName = ControlPropertyName;
    }

    public Boolean getEnableControlProperty() {
        return EnableControlProperty;
    }

    public Integer getControlPropertyID() {
        return ControlPropertyID;
    }

    public String getControlPropertyName() {
        return ControlPropertyName;
    }

    public void setEnableControlProperty(Boolean EnableControlProperty) {
        this.EnableControlProperty = EnableControlProperty;
    }

    public void setControlPropertyID(Integer ControlPropertyID) {
        this.ControlPropertyID = ControlPropertyID;
    }

    public void setControlPropertyName(String ControlPropertyName) {
        this.ControlPropertyName = ControlPropertyName;
    }
    
}
