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
public class PostStep {

    Boolean EnablePostStep;
    Integer StepID;
    String EntityName;
    String StepDescription;
    String ParentProperty;
    String ParentPropertyValue;
    String ControlProperty;
    String ControlPropertyValue;
    String StepAction;
    String StepDataValue;

        public PostStep(Boolean EnablePostStep, Integer StepID, String EntityName,String StepDescription,String ParentProperty,String ParentPropertyValue,String ControlProperty,String ControlPropertyValue,String StepAction,String StepDataValue) {
        this.EnablePostStep = EnablePostStep;
        this.StepID = StepID;
        this.EntityName = EntityName;
        this.StepDescription = StepDescription;
        this.ParentProperty = ParentProperty;
        this.ParentPropertyValue = ParentPropertyValue;
        this.ControlProperty = ControlProperty;
        this.ControlPropertyValue = ControlPropertyValue;
        this.StepAction = StepAction;
        this.StepDataValue = StepDataValue;
    }
        
    public Boolean getEnablePostStep() {
        return EnablePostStep;
    }

    public void setEnablePostStep(Boolean EnablePostStep) {
        this.EnablePostStep = EnablePostStep;
    }

    public Integer getStepID() {
        return StepID;
    }

    public void setStepID(Integer StepID) {
        this.StepID = StepID;
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String EntityName) {
        this.EntityName = EntityName;
    }

    public String getStepDescription() {
        return StepDescription;
    }

    public void setStepDescription(String StepDescription) {
        this.StepDescription = StepDescription;
    }

    public String getParentProperty() {
        return ParentProperty;
    }

    public void setParentProperty(String ParentProperty) {
        this.ParentProperty = ParentProperty;
    }

    public String getParentPropertyValue() {
        return ParentPropertyValue;
    }

    public void setParentPropertyValue(String ParentPropertyValue) {
        this.ParentPropertyValue = ParentPropertyValue;
    }

    public String getControlProperty() {
        return ControlProperty;
    }

    public void setControlProperty(String ControlProperty) {
        this.ControlProperty = ControlProperty;
    }

    public String getControlPropertyValue() {
        return ControlPropertyValue;
    }

    public void setControlPropertyValue(String ControlPropertyValue) {
        this.ControlPropertyValue = ControlPropertyValue;
    }

    public String getStepAction() {
        return StepAction;
    }

    public void setStepAction(String StepAction) {
        this.StepAction = StepAction;
    }

    public String getStepDataValue() {
        return StepDataValue;
    }

    public void setStepDataValue(String StepDataValue) {
        this.StepDataValue = StepDataValue;
    }
        
}
