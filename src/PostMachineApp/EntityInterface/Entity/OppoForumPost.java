package PostMachineApp.EntityInterface.Entity;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OppoForumPost extends BasicForumPost {

    public OppoForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent, FixedPostTrigger, Remark);
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        driver.switchTo().frame(0);

        WebElement element = driver.findElement(By.xpath("/html/body"));
        
        if (!element.getText().equals("")) {
            String selectAll = Keys.chord(Keys.CONTROL, "a");
            element.sendKeys(selectAll);
            element.sendKeys(Keys.DELETE);
        }
        
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }

        driver.switchTo().defaultContent();

        WebElement SubmitButton = driver.findElement(By.xpath("//form[@class='Comment Cb Hide ke-form']/button[text()='评论']"));
        SubmitButton.click();

        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "]message: " + i + " " + tempPostContent);
    }
}
