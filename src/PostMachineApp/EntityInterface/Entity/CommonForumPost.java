package PostMachineApp.EntityInterface.Entity;

import org.openqa.selenium.WebDriver;

final public class CommonForumPost extends BasicForumPost {

    public CommonForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime,Integer RestWaitPostCountOffset, Integer RestWaitPostCount, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
    }

     public void sendPostSteps(WebDriver driver, int i) {
        super.sendPost();
    }
}
