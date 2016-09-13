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
public class MeizuFixedForumPost extends MeizuForumPost {

    public MeizuFixedForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount, RestWaitPostCountOffset, PostUrl, PostContent);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {

        List<String> FixedPostsList = getFixedPostsList();
        for (int i = 0; i < FixedPostsList.size(); i++) {
            TargetPostCount = 0;
            long printTime = 0;
            int ThisTimeTargetPostCount = Integer.parseInt(FixedPostsList.get(i));
            while (true) {
                int CurrentRealTimePostCount = 0;
                CurrentRealTimePostCount = getCurrentPostCount();
                if (ThisTimeTargetPostCount - CurrentRealTimePostCount > 100) {
                    printTime += 100000;
                    WaitFixedTime(100000);
                } else if (ThisTimeTargetPostCount - CurrentRealTimePostCount <= 100 && ThisTimeTargetPostCount - CurrentRealTimePostCount > 50) {
                    printTime += 25000;
                    WaitFixedTime(25000);
                } else if (ThisTimeTargetPostCount - CurrentRealTimePostCount <= 50 && ThisTimeTargetPostCount - CurrentRealTimePostCount >= 35) {
                    printTime += 5000;
                    WaitFixedTime(5000);
                } else if (ThisTimeTargetPostCount - CurrentRealTimePostCount < 35 && ThisTimeTargetPostCount - CurrentRealTimePostCount > 0) {
                    TargetPostCount = ThisTimeTargetPostCount;
                    break;
                } else if (ThisTimeTargetPostCount - CurrentRealTimePostCount <= 0) {
                    TargetPostCount = -1;
                    break;
                }
                if (printTime >= 300000) {
                    System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Waiting for start up,current real time post count is： " + CurrentRealTimePostCount);
                    printTime = 0;
                }
            }
            if (TargetPostCount > 0) {
                break;
            }
        }
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);

        WebElement LoginDiv1 = driver.findElement(By.id("mzCust"));
        LoginDiv1.click();

        WebElement LoginButton1 = driver.findElement(By.id("mzLogin"));
        LoginButton1.click();

        watiuntilpageloaded(driver);
    }

    //发贴循环操作
    @Override
    public void sendPostIteration(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList, WebDriver driver) {
        while (true) {
            int CurrentRealTimePostCount = 0;

            CurrentRealTimePostCount = getCurrentPostCount();

            if (TargetPostCount - CurrentRealTimePostCount < 10) {
                System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Vying for fixed post,current real time post count is： " + CurrentRealTimePostCount);
                break;
            } else {
                WaitFixedTime(200);
            }
        }
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
