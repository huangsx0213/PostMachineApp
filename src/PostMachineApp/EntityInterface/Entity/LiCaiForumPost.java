package PostMachineApp.EntityInterface.Entity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

final public class LiCaiForumPost extends BasicForumPost {

    public LiCaiForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, PostUrl, PostContent);
    }

    @Override
    public void openUrl(WebDriver driver) {
        driver.get(PostUrl);

        driver.findElement(By.xpath("//p[@style='float:right;']/a[text()='登录']")).click();
        
        WaitFixedTime(30000);
    }
}
