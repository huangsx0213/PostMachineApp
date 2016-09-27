package PostMachineApp.EntityInterface.Entity;

import PostMachineApp.PostContentEntity;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

 public class VivoFixedForumPost extends VivoForumPost {

    public VivoFixedForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent,  FixedPostTrigger,  Remark);
    }
    @Override
    public void beforeSendPost(WebDriver driver) {

        FixedPostPolling();
        
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);
    }
    
       //发贴循环操作
    @Override
    public void sendPostIteration(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList, WebDriver driver) {
        
        SentFixedPostPolling();
        
        for (int i = 1; i <= PostCount && TargetPostCount != -1 && (System.currentTimeMillis() < StopTime || !EnableStopTime); i++) {

            getTempPostContent(PostContentEntitys, FileTextLinesList);

            sendPostSteps(driver, i);

            sendPostWait(i, driver);
        }
    }
    @Override
    public void afterSendPost() {
        if (TargetPostCount != -1) {
            driver.quit();
            sendPost();
        } else {
            driver.quit();
        }
    }
}
