package PostMachineApp.EntityInterface.Entity;

import PostMachineApp.EntityInterface.ForumPost;
import PostMachineApp.PostContentEntity;
import PostMachineApp.XMLUtil.PostContentPoolDAO;
import static PostMachineApp.XMLUtil.TextUtil.TextFile2ArrayList;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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
    public final Integer RestWaitPostCountOffset;
    public final String PostUrl;
    public final String PostContent;
    public String tempPostContent;
    public String temp;
    public SimpleDateFormat DateFormat;
    public WebDriver driver;
    public Integer RestWaitPostCountTemp;
    public Integer NextWait;
    public Integer TargetPostCount;

    //构造函数
    public BasicForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent) {
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
        this.RestWaitPostCountOffset = RestWaitPostCountOffset;
        this.PostUrl = PostUrl;
        this.PostContent = PostContent;
    }

    @Override
    public Boolean getEnableThread() {
        return EnableThread;
    }

    @Override
    public Integer getThreadID() {
        return ThreadID;
    }

    @Override
    public String getFirefoxPath() {
        return FirefoxPath;
    }

    @Override
    public String getProfile() {
        return Profile;
    }

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
    public Integer getRestWaitPostCountOffset() {
        return RestWaitPostCountOffset;
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

    //按指定的firefox profile 获取Webdriver 
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

    //从文本获取发贴内容
    public List<String> getFileTextLinesList() {
        String txtFileName = System.getProperty("user.dir") + "\\src\\Configuration\\" + Profile + ".txt";
        List<String> FileTextLinesList;
        FileTextLinesList = TextFile2ArrayList(txtFileName);
        return FileTextLinesList;
    }

    //从Pool获取发贴内容
    public List<PostContentEntity> getPostContentEntitys() {
        List<PostContentEntity> PostContentEntitys;
        //PostContentEntitys = new ArrayList<>();
        PostContentEntitys = PostContentPoolDAO.getPostContentByPofileName(this.Profile);
        return PostContentEntitys;
    }

    //从文本获取发贴内容
    public List<String> getFixedPostsList() {
        String FixedPostsFileName = null;
        // 要验证的字符串
        String str = PostUrl;
        // 正则表达式规则
        String regEx = "[0-9]{7}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        while (matcher.find()) {
            FixedPostsFileName = matcher.group();
        }
        String txtFileName = System.getProperty("user.dir") + "\\src\\Configuration\\Post-" + FixedPostsFileName + ".txt";
        List<String> FixedPostsList;
        FixedPostsList = TextFile2ArrayList(txtFileName);
        return FixedPostsList;
    }

    //发贴入口
    @Override
    public void sendPost() {

        NextWait = 0;

        tempPostContent = this.PostContent;

        RestWaitPostCountTemp = this.RestWaitPostCount;

        DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is starting.");

        List<PostContentEntity> PostContentEntitys = getPostContentEntitys();

        List<String> FileTextLinesList = getFileTextLinesList();

        beforeSendPost(driver);

        sendPostIteration(PostContentEntitys, FileTextLinesList, driver);

        afterSendPost();

        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] Post thread is Stoped.");
    }

    //发贴前置操作
    public void beforeSendPost(WebDriver driver) {
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);
    }

    //发贴循环操作
    public void sendPostIteration(List<PostContentEntity> PostContentEntitys, List<String> FileTextLinesList, WebDriver driver) {

        for (int i = 1; i <= PostCount && (System.currentTimeMillis() < StopTime || !EnableStopTime); i++) {

            getTempPostContent(PostContentEntitys, FileTextLinesList);

            sendPostSteps(driver, i);

            sendPostWait(i, driver);
        }
    }

    //发贴后置操作
    @Override
    public void afterSendPost() {
        driver.quit();
    }

    //获取发贴的随机内容
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

    //从Pool获取随机的发贴内容
    public PostContentEntity getRandomPostContentFromPool(List<PostContentEntity> PostContentEntitys) {
        int MyListIndex = (int) (Math.random() * (PostContentEntitys.size()));
        PostContentEntity value = PostContentEntitys.get(MyListIndex);
        return value;
    }

    //从TextFile获取随机的发贴内容
    public String getRandomPostContentFromTextFile(List<String> FileTextLinesList) {
        int MyListIndex = (int) (Math.random() * (FileTextLinesList.size()));
        String value = FileTextLinesList.get(MyListIndex);
        return value;
    }

    //发贴步骤
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

    //发贴等待时间
    public void sendPostWait(int i, WebDriver driver) {
        try {
            Thread.sleep(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);
            NextWait++;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (i % RefreshPostCount == 0) {
            driver.navigate().refresh();
        }

        if (Objects.equals(RestWaitPostCountTemp, NextWait) && RestWaitPostCountTemp > 0 && RestWaitTime > 0) {
            RestWaitTime(RestWaitTime);
            RestWaitPostCountTemp = (int) (RestWaitPostCount - RestWaitPostCountOffset + Math.random() * (RestWaitPostCount + RestWaitPostCountOffset - (RestWaitPostCount - RestWaitPostCountOffset) + 1));
            System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] will take a next rest after " + RestWaitPostCountTemp + " posts.");
            NextWait = 0;
        }
    }

    //休息等待时间
    public void RestWaitTime(Integer WaitTime) {
        Integer AdjustedWaitTime;
        AdjustedWaitTime = (int) (WaitTime * (1 - 0.2) + Math.random() * (WaitTime * (1 + 0.2) - WaitTime * (1 - 0.2) + 1)) * 1000;
        System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] is taking a rest " + AdjustedWaitTime / 1000 + "s.");
        try {
            Thread.sleep(AdjustedWaitTime);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //固定等待时间
    public void WaitFixedTime(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Integer getCurrentPostCount() {
        int result = 0;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(PostUrl);
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpget, responseHandler);
            String[] sourceStrArray = responseBody.split("共有");
            String[] sourceStrArray2 = sourceStrArray[1].split("条回复");
            result = Integer.parseInt(sourceStrArray2[0]) + 1;
            //System.out.println(DateFormat.format(new Date()) + " [" + Profile + "] 现在的楼层数为： " + result);
        } catch (IOException | NumberFormatException ex) {
            ex.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return result;
    }
}
