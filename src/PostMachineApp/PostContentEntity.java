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
public class PostContentEntity {

    Boolean EnablePoolContent;
    Integer PoolContentID;
    String PoolFirefoxProfile;
    String PoolContent;

    public PostContentEntity(Boolean EnablePostContent, Integer PostContentID,String PoolFirefoxProfile, String PostContent) {
        this.EnablePoolContent = EnablePostContent;
        this.PoolContentID = PostContentID;
        this.PoolFirefoxProfile = PoolFirefoxProfile;
        this.PoolContent = PostContent;
    }

    public Boolean getEnablePoolContent() {
        return EnablePoolContent;
    }

    public void setEnablePoolContent(Boolean EnablePoolContent) {
        this.EnablePoolContent = EnablePoolContent;
    }

    public Integer getPoolContentID() {
        return PoolContentID;
    }

    public void setPoolContentID(Integer PoolContentID) {
        this.PoolContentID = PoolContentID;
    }

    public String getPoolFirefoxProfile() {
        return PoolFirefoxProfile;
    }

    public void setPoolFirefoxProfile(String PoolFirefoxProfile) {
        this.PoolFirefoxProfile = PoolFirefoxProfile;
    }

    public String getPoolContent() {
        return PoolContent;
    }

    public void setPoolContent(String PoolContent) {
        this.PoolContent = PoolContent;
    }

       
}
