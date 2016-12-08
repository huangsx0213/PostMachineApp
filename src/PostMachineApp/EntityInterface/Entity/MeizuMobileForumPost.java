/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import javax.swing.JOptionPane;
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
public class MeizuMobileForumPost extends BasicForumPost {

    public MeizuMobileForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent,String StartFrom, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent,StartFrom,FixedPostTrigger,Remark);
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
                "Mozilla/5.0 (Linux; U; Android 4.4.4; Nexus 5 Build/KTU84P) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
        driver = new FirefoxDriver(FirefoxProfile);
        return driver;
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        StartFromPostPolling();
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);
        WebElement LoginImg = driver.findElement(By.xpath("//img[@src='http://bbs.res.meizu.com/resources/php/bbs/static/mplus/img/icon-profile.png']"));
        LoginImg.click();
        WebElement Username = driver.findElement(By.id("account"));
        Username.sendKeys(Profile);
        WebElement Password = driver.findElement(By.id("password"));
        Password.sendKeys("12345qwert");

        JOptionPane.showMessageDialog(null, "Please input your login credential.", "Credential", JOptionPane.INFORMATION_MESSAGE);

        WaitFixedTime(30000);
        
        WebElement CloseScroll = driver.findElement(By.xpath("//div[@class='app-scroll']/i[@class='close-scroll']"));
        CloseScroll.click();
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {

        //driver.get(PostUrl);
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

        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] message: " + i + " " + tempPostContent);
    }
}
