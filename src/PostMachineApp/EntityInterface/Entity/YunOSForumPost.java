package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

final public class YunOSForumPost extends BasicForumPost {

    public YunOSForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        driver.get(PostUrl);

        JOptionPane.showMessageDialog(null, "Please input your login credential.", "Credential", JOptionPane.INFORMATION_MESSAGE);

        WaitFixedTime(60000);
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        
        WebElement element = driver.findElement(By.id("textarea"));

        element.clear();
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }
        element.submit();

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] message: " + i + " " + tempPostContent);
    }
}
