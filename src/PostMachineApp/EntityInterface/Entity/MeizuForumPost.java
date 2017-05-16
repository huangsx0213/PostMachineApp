/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp.EntityInterface.Entity;

import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author vhuang1
 */
public class MeizuForumPost extends BasicForumPost {

    public MeizuForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent,String StartFrom, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent,StartFrom, FixedPostTrigger, Remark);
    }
    
    @Override
    public void SetHttpClient() {
        httpclient = getHttpsClient();
    }
    
    @Override
    public void beforeSendPost(WebDriver driver) {
        StartFromPostPolling();
        driver = getWebDriverWithSpecifiedProfile();

        driver.get("https://bbs.meizu.cn/forum-22-1.html");
        WebElement LoginDiv1 = driver.findElement(By.id("mzCust"));
        LoginDiv1.click();
        WebElement LoginButton1 = driver.findElement(By.id("mzLogin"));
        LoginButton1.click();
        watiuntilpageloaded(driver);

        driver.findElement(By.xpath("//div[@class='btncont_signin btncont_signin_2']")).click();
        driver.get(PostUrl);
        
        //WebElement LoginDiv2 = driver.findElement(By.id("mzCust"));
        //LoginDiv2.click();
        //WebElement LoginButton2 = driver.findElement(By.id("mzLogin"));
        //LoginButton2.click();
        
        watiuntilpageloaded(driver);
    }

    @Override
    public void sendPostSteps(WebDriver driver, int i) {

        driver.switchTo().frame("fastpostiframe");
        WebElement element = driver.findElement(By.xpath("/html/body"));
        element.clear();

        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }
        driver.switchTo().defaultContent();

        WebElement SubmitButton = driver.findElement(By.id("fastpostsubmit"));
        SubmitButton.click();

        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] message: " + i + " " + tempPostContent);
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
