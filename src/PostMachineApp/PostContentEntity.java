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

    Boolean EnablePostContent;
    Integer PostContentID;
    String PostEntity2;
    String PostContent;

    public PostContentEntity(Boolean EnablePostContent, Integer PostContentID, String PostContent) {
        this.EnablePostContent = EnablePostContent;
        this.PostContentID = PostContentID;
        this.PostContent = PostContent;
    }

    public Boolean getEnablePostContent() {
        return EnablePostContent;
    }

    public void setEnablePostContent(Boolean EnablePostContent) {
        this.EnablePostContent = EnablePostContent;
    }

    public Integer getPostContentID() {
        return PostContentID;
    }

    public void setPostContentID(Integer PostContentID) {
        this.PostContentID = PostContentID;
    }

    public String getPostContent() {
        return PostContent;
    }

    public void setPostContent(String PostContent) {
        this.PostContent = PostContent;
    }
    
}
