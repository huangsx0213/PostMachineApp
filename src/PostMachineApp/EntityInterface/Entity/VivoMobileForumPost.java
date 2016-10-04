/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 *
 * @author vhuang1
 */
public class VivoMobileForumPost extends BasicForumPost {

    public VivoMobileForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent,  FixedPostTrigger,  Remark);
    }

    @Override
    public WebDriver getWebDriverWithSpecifiedProfile() {
        // specified firefox's installing path.
        if (!this.FirefoxPath.equals("default")) {
            System.setProperty("webdriver.firefox.bin", FirefoxPath);
        }

        ProfilesIni allProfiles = new ProfilesIni();

        FirefoxProfile FirefoxProfile = allProfiles.getProfile(Profile);
        FirefoxProfile.setPreference("general.useragent.override",
                "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; iphone 6s/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        driver = new FirefoxDriver(FirefoxProfile);
        return driver;
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {
        
        driver.get(PostUrl);
        
        WebElement element = driver.findElement(By.id("fastpostmessage"));
        element.clear();
        
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }
        //element.click();
        element.submit();
        //WebElement fastpostsubmit = driver.findElement(By.id("fastpostsubmit"));
        //fastpostsubmit.click();
        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] message: " + i + " " + tempPostContent);
    }
}
