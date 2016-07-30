/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp;

import PostMachineApp.EntityInterface.Entity.*;
import PostMachineApp.EntityInterface.ForumPost;

/**
 *
 * @author MyTest
 */
public class ForumPostFactory {

    public static ForumPost CreateForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset,String PostUrl, String PostContent) {
        switch (PostEntity) {
            case "Common":
                return new CommonForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "Vivo":
                return new VivoForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset,PostUrl, PostContent);
            case "Oppo":
                return new OppoForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "VivoMobile":
                return new VivoMobileForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset,PostUrl, PostContent);
            case "Qiku360":
                return new Qiku360ForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "YunOS":
                return new YunOSForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "Huawei":
                return new HuaweiForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset,PostUrl, PostContent);
            case "WangYi":
                return new WangYiForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "LiCai":
                return new LiCaiForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "MeizuMobile":
                return new MeizuMobileForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            case "Meizu":
                return new MeizuForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
            default:
                break;
        }
        return null;
    }

}
