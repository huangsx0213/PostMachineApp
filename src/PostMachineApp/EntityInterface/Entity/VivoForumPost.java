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

final public class VivoForumPost implements ForumPost {

    private final Boolean EnableThread;
    private final Integer ThreadID;
    private final String FirefoxPath;
    private final String Profile;
    private final String PostEntity;
    private final long StartTime;
    private final Boolean EnableStopTime;
    private final long StopTime;
    private final Integer RefreshPostCount;
    private final long PostCount;
    private final Integer FixedWaitTime;
    private final Integer RandomWaitTime;
    private final Integer RestWaitTime;
    private final Integer RestWaitPostCount;
    private final String PostUrl;
    private final String PostContent;
    private String tempPostContent;
    private String temp;

    public VivoForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, String PostUrl, String PostContent) {
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
    public void sentpost() {

        tempPostContent = this.PostContent;

        List<PostContentEntity> PostContentEntitys = new ArrayList<PostContentEntity>();
        PostContentEntitys = PostContentPoolDAO.getPostContentByPofileName(this.Profile);

        String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\" + Profile + ".txt";
        List<String> FileTextLinesList = new ArrayList<>();
        FileTextLinesList = TextFile2ArrayList(fileName);

        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is starting.");

        // specified firefox's installing path.
        if (!this.FirefoxPath.equals("default")) {
            System.setProperty("webdriver.firefox.bin", FirefoxPath);
        }

        ProfilesIni allProfiles = new ProfilesIni();

        FirefoxProfile FirefoxProfile = allProfiles.getProfile(Profile);

        WebDriver driver = new FirefoxDriver(FirefoxProfile);

        driver.get(PostUrl);

        for (int i = 1; i < PostCount && (System.currentTimeMillis() < StopTime || !EnableStopTime); i++) {

            getTempPostContent(PostContentEntitys, FileTextLinesList);

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

            System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] message: " + i + " " + tempPostContent);
            try {
                Thread.sleep(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);
            } catch (Exception ex) {
            }
            if (i % RefreshPostCount == 0) {
                driver.navigate().refresh();
            }
            if (RestWaitPostCount>0 && RestWaitTime>0 && i % RestWaitPostCount == 0) {
                WaitFixedTime(RestWaitTime);
            }

        }
        driver.quit();
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is Stoped.");
    }
    private void WaitFixedTime(Integer WaitTime) {
        try {
            Thread.sleep(WaitTime*1000);
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
}
