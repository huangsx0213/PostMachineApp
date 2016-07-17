package PostMachineApp.EntityInterface.Entity;

import PostMachineApp.EntityInterface.ForumPost;
import PostMachineApp.PostContentEntity;
import PostMachineApp.XMLUtil.PostContentPoolDAO;
import static PostMachineApp.XMLUtil.TextUtil.TextFile2ArrayList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class BasicForumPost implements ForumPost {

    public final Boolean EnableThread;
    public final Integer ThreadID;
    public final String FirefoxPath;
    public final String Profile;
    public final String PostEntity;
    public final long StartTime;
    public final Boolean EnableStopTime;
    public final long StopTime;
    public final Integer RefreshPostCount;
    public final long PostCount;
    public final Integer FixedWaitTime;
    public final Integer RandomWaitTime;
    public final Integer RestWaitTime;
    public final Integer RestWaitPostCount;
    public final String PostUrl;
    public final String PostContent;
    public String tempPostContent;
    public String temp;
    public SimpleDateFormat DateFormat;
    public WebDriver driver;

    public BasicForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, String PostUrl, String PostContent) {
        this.EnableThread = EnableThread;
        this.ThreadID = ThreadID;
        this.FirefoxPath = FirefoxPath;
        this.Profile = Profile;
        this.PostEntity = PostEntity;
        this.StartTime = StartTime;
        this.EnableStopTime = EnableStopTime;
        this.StopTime = StopTime;
        this.RefreshPostCount = RefreshPostCount;
        this.PostCount = PostCount;
        this.FixedWaitTime = FixedWaitTime;
        this.RandomWaitTime = RandomWaitTime;
        this.RestWaitTime = RestWaitTime;
        this.RestWaitPostCount = RestWaitPostCount;
        this.PostUrl = PostUrl;
        this.PostContent = PostContent;
    }

    /**
     *
     * @param PofileName
     * @return
     */
    @Override
    public Boolean getEnableThread() {
        return EnableThread;
    }

    @Override
    public Integer getThreadID() {
        return ThreadID;
    }

    /**
     *
     * @return
     */
    @Override
    public String getFirefoxPath() {
        return FirefoxPath;
    }

    @Override
    public String getProfile() {
        return Profile;
    }

    /**
     *
     * @return
     */
    @Override
    public String getPostEntity() {
        return PostEntity;
    }

    @Override
    public long getStartTime() {
        return StartTime;
    }

    @Override
    public Boolean getEnableStopTime() {
        return EnableStopTime;
    }

    @Override
    public long getStopTime() {
        return StopTime;
    }

    @Override
    public Integer getRefreshPostCount() {
        return RefreshPostCount;
    }

    @Override
    public long getPostCount() {
        return PostCount;
    }

    @Override
    public Integer getFixedWaitTime() {
        return FixedWaitTime;
    }

    @Override
    public Integer getRandomWaitTime() {
        return RandomWaitTime;
    }

    @Override
    public Integer getRestWaitTime() {
        return RestWaitTime;
    }

    @Override
    public Integer getRestWaitPostCount() {
        return RestWaitPostCount;
    }

    @Override
    public String getPostUrl() {
        return PostUrl;
    }

    @Override
    public String getPostContent() {
        return PostContent;
    }

    @Override
    public void sendPost() {

        tempPostContent = this.PostContent;

        DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is starting.");

        List<PostContentEntity> PostContentEntitys = getPostContentEntitys();

        List<String> FileTextLinesList = getFileTextLinesList();

        driver = getWebDriverWithSpecifiedProfile();

        openUrl(driver);

        sendPostIteration(PostContentEntitys, FileTextLinesList, driver);

        sendPostQuit();

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is Stoped.");
    }

    public void sendPostIteration(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList, WebDriver driver) {

        for (int i = 1; i < PostCount && (System.currentTimeMillis() < StopTime || !EnableStopTime); i++) {

            getTempPostContent(PostContentEntitys, FileTextLinesList);

            sendPostSteps(driver, i);

            sendPostWait(i, driver);
        }
    }
    @Override
    public void sendPostQuit() {
        driver.quit();
    }

    public void sendPostSteps(WebDriver driver, int i) {
        WebElement element = driver.findElement(By.id("fastpostmessage"));

        element.clear();
        if (i % 2 == 0) {
            element.sendKeys(tempPostContent);
        } else {
            element.sendKeys(tempPostContent + " ");
        }
        element.submit();
        //WebElement fastpostsubmit = driver.findElement(By.id("fastpostsubmit"));
        //fastpostsubmit.click();
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] message: " + i + " " + tempPostContent);
    }

    public void sendPostWait(int i, WebDriver driver) {
        try {
            Thread.sleep(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);
        } catch (Exception ex) {
        }
        if (i % RefreshPostCount == 0) {
            driver.navigate().refresh();
        }
        if (i > 1 && RestWaitPostCount > 0 && RestWaitTime > 0 && i % RestWaitPostCount == 0) {
            RestWaitTime(RestWaitTime);
        }
    }

    public void openUrl(WebDriver driver) {
        driver.get(PostUrl);
    }

    public List<String> getFileTextLinesList() {
        String txtFileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\" + ThreadID + ".txt";
        List<String> FileTextLinesList;
        FileTextLinesList = TextFile2ArrayList(txtFileName);
        return FileTextLinesList;
    }

    public List<PostContentEntity> getPostContentEntitys() {
        List<PostContentEntity> PostContentEntitys;
        //PostContentEntitys = new ArrayList<>();
        PostContentEntitys = PostContentPoolDAO.getPostContentByPofileName(this.Profile);
        return PostContentEntitys;
    }

    public WebDriver getWebDriverWithSpecifiedProfile() {
        // specified firefox's installing path.
        if (!this.FirefoxPath.equals("default")) {
            System.setProperty("webdriver.firefox.bin", FirefoxPath);
        }
        ProfilesIni allProfiles = new ProfilesIni();
        FirefoxProfile FirefoxProfile = allProfiles.getProfile(Profile);
        driver = new FirefoxDriver(FirefoxProfile);
        return driver;
    }

    public void RestWaitTime(Integer WaitTime) {
        Integer AdjustedWaitTime;
        AdjustedWaitTime = (int) (WaitTime * (1 - 0.2) + Math.random() * (WaitTime * (1 + 0.2) - WaitTime * (1 - 0.2) + 1)) * 1000;
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] is taking a rest " + AdjustedWaitTime / 1000 + "s.");
        try {
            Thread.sleep(AdjustedWaitTime);
        } catch (Exception ex) {
        }
    }

    public String getTempPostContent(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList) {
        if (this.PostContent.equals("[Pool]")) {
            while (true) {
                temp = getRandomPostContentFromPool(PostContentEntitys).getPoolContent();
                if (!tempPostContent.equals(temp)) {
                    tempPostContent = temp;
                    break;
                }

            }
        } else if (this.PostContent.equals("[TextFile]")) {
            while (true) {
                temp = getRandomPostContentFromTextFile(FileTextLinesList);
                if (!tempPostContent.equals(temp)) {
                    tempPostContent = temp;
                    break;
                }
            }
        }
        return tempPostContent;
    }

    public PostContentEntity getRandomPostContentFromPool(List<PostContentEntity> PostContentEntitys) {
        int MyListIndex = (int) (Math.random() * (PostContentEntitys.size()));
        PostContentEntity value = PostContentEntitys.get(MyListIndex);
        return value;
    }

    public String getRandomPostContentFromTextFile(List<String> FileTextLinesList) {
        int MyListIndex = (int) (Math.random() * (FileTextLinesList.size()));
        String value = FileTextLinesList.get(MyListIndex);
        return value;
    }
        public void WaitFixedTime(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {
        }
    }
}
