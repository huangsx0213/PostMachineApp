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
    String PoolContentPostEntity;
    String PoolContent;

    public PostContentEntity(Boolean EnablePostContent, Integer PostContentID,String PoolContentPostEntity, String PostContent) {
        this.EnablePoolContent = EnablePostContent;
        this.PoolContentID = PostContentID;
        this.PoolContentPostEntity = PoolContentPostEntity;
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

    public String getPoolContentPostEntity() {
        return PoolContentPostEntity;
    }

    public void setPoolContentPostEntity(String PoolContentPostEntity) {
        this.PoolContentPostEntity = PoolContentPostEntity;
    }

    public String getPoolContent() {
        return PoolContent;
    }

    public void setPoolContent(String PoolContent) {
        this.PoolContent = PoolContent;
    }

       
}
