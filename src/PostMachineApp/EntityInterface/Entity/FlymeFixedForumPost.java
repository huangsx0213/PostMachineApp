/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PostMachineApp.EntityInterface.Entity;

import PostMachineApp.PostContentEntity;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 *
 * @author vhuang1
 */
public class FlymeFixedForumPost extends FlymeForumPost {

    public FlymeFixedForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent,FixedPostTrigger,Remark);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
             
        FixedPostPolling();
        
        driver = getWebDriverWithSpecifiedProfile();
        driver.get("http://bbs.flyme.cn/forum-101-1.html");
        WebElement LoginDiv1 = driver.findElement(By.xpath("//div[@id='loginPanel']/span/a"));
        LoginDiv1.click();
        watiuntilpageloaded(driver);
        driver.findElement(By.xpath("//div[@class='btn_sign_left']/p[contains(text(),'签到')]")).click();
        driver.get(PostUrl);
        watiuntilpageloaded(driver);
    }
    //发贴循环操作
    @Override
    public void sendPostIteration(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList, WebDriver driver) {
        
        SentFixedPostPolling();
        
        for (int i = 1; i <= PostCount && TargetPostCount != -1 && (System.currentTimeMillis() < StopTime || !EnableStopTime); i++) {

            getTempPostContent(PostContentEntitys, FileTextLinesList);

            sendPostSteps(driver, i);

            sendPostWait(i, driver);
        }
    }
    @Override
    public void afterSendPost() {
        if (TargetPostCount != -1) {
            driver.quit();
            sendPost();
        } else {
            driver.quit();
        }
    }
}
