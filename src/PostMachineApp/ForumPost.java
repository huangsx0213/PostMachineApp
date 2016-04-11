package PostMachineApp;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

final public class ForumPost {
        private final Boolean EnableThread;
        private final Integer ThreadID;
	private final String FirefoxPath;
	private final String Profile;
        private final Boolean EnableUserAgent;
        private final String UserAgent;
        private final long StartTime;
        private final Boolean EnableStopTime;
        private final long StopTime;
        private final Integer RefreshPostCount;
        private final long PostCount;
        private final Integer PostInterval;
        private final Integer AdditionalRandomTime;
	private final String PostUrl;
        private final String PostContent;

	public ForumPost(Boolean EnableThread,Integer ThreadID,String FirefoxPath, String Profile, Boolean EnableUserAgent,String UserAgent,long StartTime,Boolean EnableStopTime,long StopTime,Integer RefreshPostCount,long PostCount,Integer PostInterval,Integer AdditionalRandomTime,String PostUrl,String PostContent) {
		this.EnableThread = EnableThread;
		this.ThreadID = ThreadID;
		this.FirefoxPath = FirefoxPath;
		this.Profile = Profile;
		this.EnableUserAgent = EnableUserAgent;
		this.UserAgent = UserAgent;
		this.StartTime = StartTime;
		this.EnableStopTime = EnableStopTime;
		this.StopTime = StopTime;
                this.RefreshPostCount = RefreshPostCount;
		this.PostCount = PostCount;
		this.PostInterval = PostInterval;
		this.AdditionalRandomTime = AdditionalRandomTime;
		this.PostUrl = PostUrl;
		this.PostContent = PostContent;
	}

    public Boolean getEnableThread() {
        return EnableThread;
    }

    public Integer getThreadID() {
        return ThreadID;
    }

    public String getFirefoxPath() {
        return FirefoxPath;
    }

    public String getProfile() {
        return Profile;
    }

    public Boolean getEnableUserAgent() {
        return EnableUserAgent;
    }

    public String getUserAgent() {
        return UserAgent;
    }

    public long getStartTime() {
        return StartTime;
    }

    public Boolean getEnableStopTime() {
        return EnableStopTime;
    }

    public long getStopTime() {
        return StopTime;
    }

    public Integer getRefreshPostCount() {
        return RefreshPostCount;
    }

    public long getPostCount() {
        return PostCount;
    }

    public Integer getPostInterval() {
        return PostInterval;
    }

    public Integer getAdditionalRandomTime() {
        return AdditionalRandomTime;
    }

    public String getPostUrl() {
        return PostUrl;
    }

    public String getPostContent() {
        return PostContent;
    }

   
	public void sentpost() {
		// specified firefox's installing path
                if(!this.FirefoxPath.equals("default")){
                    System.setProperty("webdriver.firefox.bin", FirefoxPath);
                }
                
		ProfilesIni allProfiles = new ProfilesIni();

		FirefoxProfile FirefoxProfile = allProfiles.getProfile(Profile);

		WebDriver driver = new FirefoxDriver(FirefoxProfile);

		driver.get(PostUrl);

		for (int i = 1; i < PostCount && (System.currentTimeMillis()  < StopTime || !EnableStopTime); i++) {

			WebElement element = driver.findElement(By.id("fastpostmessage"));

			element.clear();
			if (i % 2 == 0) {
				element.sendKeys(PostContent);
			} else {
				element.sendKeys(PostContent + " ");
			}
			element.submit();
			SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(DateFormat.format(new Date()) + " [" + Profile + "]message: " + i+" "+PostContent);
			try {
				Thread.sleep(PostInterval * 1000 + (int) (1 + Math.random() * (AdditionalRandomTime - 1 + 1)) * 1000);
			} catch (Exception ex) {
			}
			if (i % RefreshPostCount == 0) {
				driver.navigate().refresh();
			}

		}
		driver.quit();
	}
}