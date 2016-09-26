/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import javax.swing.JOptionPane;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

/**
 *
 * @author vhuang1
 */
public class FlymeForumPost extends BasicForumPost {

    public FlymeForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent,FixedPostTrigger,Remark);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        driver = getWebDriverWithSpecifiedProfile();
        driver.get("http://bbs.flyme.cn/forum-101-1.html");
        WebElement LoginDiv1 = driver.findElement(By.xpath("//div[@id='loginPanel']/span/a"));
        LoginDiv1.click();
        watiuntilpageloaded(driver);
        driver.findElement(By.xpath("//div[@class='btn_sign_left']/p[contains(text(),'签到')]")).click();
        driver.get(PostUrl);
        watiuntilpageloaded(driver);
    }

    public void watiuntilpageloaded(WebDriver driver) {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(500);
        } catch (Exception ex) {
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String state;
        for (int waitno = 0; waitno < 100; waitno++) {
            state = (String) js.executeScript("return document.readyState; ");
            // System.out.print(state+"\n");
            if (state.equals("complete")) {
                // System.out.print("Page loaded.");
                break;
            }
            try {
                Thread.sleep(200);
            } catch (Exception ex) {
            }
        }
    }
}
