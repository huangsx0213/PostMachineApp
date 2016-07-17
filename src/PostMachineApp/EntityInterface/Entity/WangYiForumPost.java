package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

final public class WangYiForumPost extends BasicForumPost {

    public WangYiForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, PostUrl, PostContent);
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        WebElement clearnode = driver.findElement(By.id("clearnode"));
        clearnode.click();

        driver.switchTo().frame(driver.findElement(By.name("editorIframe")));

        WebElement element = driver.findElement(By.xpath("/html/body"));

        //element.clear();
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }

        driver.switchTo().defaultContent();
        //element.submit();
        WebElement replyTie = driver.findElement(By.id("replyTie"));
        replyTie.click();

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] message: " + i + " " + tempPostContent);
    }
}
