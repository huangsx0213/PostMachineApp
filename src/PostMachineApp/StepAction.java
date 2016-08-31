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
public class StepAction {

    Boolean EnableStepAction;
    Integer StepActionID;
    String StepActionName;

    public StepAction(Boolean EnableStepAction, Integer StepActionID, String StepActionName) {
        this.EnableStepAction = EnableStepAction;
        this.StepActionID = StepActionID;
        this.StepActionName = StepActionName;
    }

    public Boolean getEnableStepAction() {
        return EnableStepAction;
    }

    public Integer getStepActionID() {
        return StepActionID;
    }

    public String getStepActionName() {
        return StepActionName;
    }

    public void setEnableStepAction(Boolean EnableStepAction) {
        this.EnableStepAction = EnableStepAction;
    }

    public void setStepActionID(Integer StepActionID) {
        this.StepActionID = StepActionID;
    }

    public void setStepActionName(String StepActionName) {
        this.StepActionName = StepActionName;
    }
    
}
