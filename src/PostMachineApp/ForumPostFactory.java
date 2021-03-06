/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp;

import PostMachineApp.EntityInterface.Entity.OppoForumPost;
import PostMachineApp.EntityInterface.Entity.Qiku360ForumPost;
import PostMachineApp.EntityInterface.Entity.VivoForumPost;
import PostMachineApp.EntityInterface.Entity.VivoMobileForumPost;
import PostMachineApp.EntityInterface.ForumPost;

/**
 *
 * @author MyTest
 */
public class ForumPostFactory {

    public static ForumPost CreateForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, String PostUrl, String PostContent) {
        if (PostEntity.equals("Vivo")) {
            return new VivoForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);
        } else if (PostEntity.equals("Oppo")) {
            return new OppoForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);
        } else if (PostEntity.equals("VivoMobile")) {
            return new VivoMobileForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);
        }
         else if (PostEntity.equals("Qiku360")) {
            return new Qiku360ForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);
        }
        return null;
    }

}
