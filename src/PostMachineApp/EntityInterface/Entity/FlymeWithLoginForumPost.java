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
public class FlymeWithLoginForumPost extends BasicForumPost {

    public FlymeWithLoginForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent,String StartFrom, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent,StartFrom,FixedPostTrigger, Remark);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        StartFromPostPolling();
        
        driver = getWebDriverWithSpecifiedProfile();
        
        driver.manage().window().maximize();
        
        driver.get("http://bbs.flyme.cn/forum-101-1.html");
        
        WebElement LoginDiv1 = driver.findElement(By.xpath("//div[@id='loginPanel']/span/a"));
        LoginDiv1.click();
        watiuntilpageloaded(driver);
        
        WebElement Input_UserName = driver.findElement(By.xpath("//input[@id='account']"));
        Input_UserName.sendKeys(Profile);
        
        WebElement Input_Password = driver.findElement(By.xpath("//input[@id='password']"));
        Input_Password.sendKeys("12345qwert");
        
        JOptionPane.showMessageDialog(null, "Please input your login credential.", "Credential", JOptionPane.INFORMATION_MESSAGE);

        WaitFixedTime(2000);
        
        WebElement Input_Login = driver.findElement(By.xpath("//a[@id='login']"));
        Input_Login.click();
         
        watiuntilpageloaded(driver);
        
        driver.findElement(By.xpath("//div[@class='btn_sign_left']/p[contains(text(),'签到')]")).click();
        driver.get(PostUrl);
        watiuntilpageloaded(driver);
    }

    public void watiuntilpageloaded(WebDriver driver) {
        // TODO Auto-generated method stub

        WaitFixedTime(500);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String state;
        for (int waitno = 0; waitno < 100; waitno++) {
            state = (String) js.executeScript("return document.readyState; ");
            // System.out.print(state+"\n");
            if (state.equals("complete")) {
                // System.out.print("Page loaded.");
                break;
            }

            WaitFixedTime(200);

        }
    }
}
