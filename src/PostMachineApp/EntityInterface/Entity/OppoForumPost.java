package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

final public class OppoForumPost extends BasicForumPost {

    public OppoForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);

        driver.findElement(By.id("fastposteditor")).click();
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        WebElement element = driver.findElement(By.id("fastpostmessage"));

        element.clear();

        element.sendKeys(tempPostContent + "\n\n [color=#FFFFFF]发表于" + DateFormat.format(new Date()) + "[/color]");

        element.submit();

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "]message: " + i + " " + tempPostContent);
    }

    @Override
        public void sendPostWait(int i, WebDriver driver) {
        try {
            Thread.sleep(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);
            NextWait++;
        } catch (Exception ex) {
        }
        if (i % RefreshPostCount == 0) {
            driver.navigate().refresh();
            driver.findElement(By.id("fastposteditor")).click();
        }

        if (Objects.equals(RestWaitPostCountTemp, NextWait) && RestWaitPostCountTemp > 0 && RestWaitTime > 0) {
            RestWaitTime(RestWaitTime);
            RestWaitPostCountTemp = (int) (RestWaitPostCount -RestWaitPostCountOffset + Math.random() * (RestWaitPostCount+RestWaitPostCountOffset - (RestWaitPostCount -RestWaitPostCountOffset) + 1));
            System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] will take a next rest after " + RestWaitPostCountTemp + " posts.");
            NextWait = 0;
        }
    }
}
