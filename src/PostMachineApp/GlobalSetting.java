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
public class GlobalSetting {
    
    String FirefoxInstallationPath;
    String UserAgentString;
    String WorkstationName;   
    String id;  

    public GlobalSetting(String id,String FirefoxInstallationPath,String UserAgentString,String WorkstationName) {
        this.id=id;
        this.FirefoxInstallationPath=FirefoxInstallationPath;
        this.UserAgentString=UserAgentString;
        this.WorkstationName=WorkstationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirefoxInstallationPath() {
        return FirefoxInstallationPath;
    }

    public String getUserAgentString() {
        return UserAgentString;
    }

    public String getWorkstationName() {
        return WorkstationName;
    }

    public void setFirefoxInstallationPath(String FirefoxInstallationPath) {
        this.FirefoxInstallationPath = FirefoxInstallationPath;
    }

    public void setUserAgentString(String UserAgentString) {
        this.UserAgentString = UserAgentString;
    }

    public void setWorkstationName(String WorkstationName) {
        this.WorkstationName = WorkstationName;
    }
    
}
