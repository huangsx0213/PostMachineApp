package PostMachineApp.EntityInterface.Entity;

import PostMachineApp.EntityInterface.ForumPost;
import PostMachineApp.PostContentEntity;
import PostMachineApp.XMLUtil.PostContentPoolDAO;
import static PostMachineApp.XMLUtil.TextUtil.TextFile2ArrayList;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

final public class WangYiForumPost implements ForumPost {

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
    private final String PostUrl;
    private final String PostContent;
    private String tempPostContent;
    private String temp;

    public WangYiForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, String PostUrl, String PostContent) {
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

            getTempPostContent();

            WebElement clearnode = driver.findElement(By.id("clearnode"));
            clearnode.click();

            driver.switchTo().frame(driver.findElement(By.name("editorIframe")));

            WebElement element = driver.findElement(By.xpath("/html/body"));

            //element.clear();
            if (i % 2 == 0) {
                element.sendKeys(tempPostContent);
            } else {
                element.sendKeys(tempPostContent + " ");
            }

            driver.switchTo().defaultContent();
            //element.submit();
            WebElement replyTie = driver.findElement(By.id("replyTie"));
            replyTie.click();

            System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] message: " + i + " " + tempPostContent);
            try {
                Thread.sleep(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);
            } catch (Exception ex) {
            }
            if (i % RefreshPostCount == 0) {
                driver.navigate().refresh();
            }

        }
        driver.quit();
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is Stoped.");
    }

    public String getTempPostContent() {
        if (this.PostContent.equals("[Pool]")) {
            while (true) {
                temp = getRandomPostContentFromPool().getPoolContent();
                if (!tempPostContent.equals(temp)) {
                    tempPostContent = temp;
                    break;
                }

            }
        } else if (this.PostContent.equals("[TextFile]")) {
            while (true) {
                String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\" + Profile + ".txt";
                System.out.println(fileName);
                temp = getRandomPostContentFromTextFile(TextFile2ArrayList(fileName));
                if (!tempPostContent.equals(temp)) {
                    tempPostContent = temp;
                    break;
                }
            }
        }
        return tempPostContent;
    }

    public PostContentEntity getRandomPostContentFromPool() {
        List<PostContentEntity> PostContentEntitys = new ArrayList<PostContentEntity>();
        PostContentEntitys = PostContentPoolDAO.getPostContentByPofileName(this.Profile);
        int MyListIndex = (int) (Math.random() * (PostContentEntitys.size()));
        PostContentEntity value = PostContentEntitys.get(MyListIndex);
        return value;
    }

    public String getRandomPostContentFromTextFile(List<String> TextLineList) {
        int MyListIndex = (int) (Math.random() * (TextLineList.size()));
        String value = TextLineList.get(MyListIndex);
        return value;
    }
}
