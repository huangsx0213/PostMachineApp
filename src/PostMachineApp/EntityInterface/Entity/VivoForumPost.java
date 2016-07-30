package PostMachineApp.EntityInterface.Entity;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

final public class VivoForumPost extends BasicForumPost {

    public VivoForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        WebElement element = driver.findElement(By.id("fastpostmessage"));

        element.clear();
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }
        //element.submit();
        WebElement fastpostsubmit = driver.findElement(By.id("fastpostsubmit"));
        fastpostsubmit.click();
        System.out.println(DateFormat.format(new Date()) + " [" + this.Profile + "] message: " + i + " " + tempPostContent);
    }
}
