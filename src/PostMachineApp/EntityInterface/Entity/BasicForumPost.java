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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
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
    public final String StartFrom;
    public final String FixedPostTrigger;
    public final String Remark;
    public String tempPostContent;
    public String temp;
    public SimpleDateFormat DateFormat;
    public WebDriver driver;
    public Integer RestWaitPostCountTemp;
    public Integer NextWait;
    public Integer TargetPostCount;
    public Integer FixedPostSlow;
    public Integer FixedPostFast;
    public Integer FixedPostTriggerNumber;
    public HttpClient httpclient;
    public int PostCountBefore;
    public int PostCountAfter;

    //构造函数
    public BasicForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount, Integer RestWaitPostCountOffset, String PostUrl, String PostContent, String StartFrom, String FixedPostTrigger, String Remark) {
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
        this.FixedPostTrigger = FixedPostTrigger;
        this.StartFrom = StartFrom;
        this.Remark = Remark;
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
    public String getStartFrom() {
        return StartFrom;
    }

    @Override
    public String getFixedPostTrigger() {
        return FixedPostTrigger;
    }

    @Override
    public String getRemark() {
        return Remark;
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

    //设置抢固定楼层的属性值
    public void SetFixedPostProperty() {
        String[] FixedPostTriggerArray = FixedPostTrigger.split("\\|");
        FixedPostSlow = Integer.parseInt(FixedPostTriggerArray[0]);
        FixedPostFast = Integer.parseInt(FixedPostTriggerArray[1]);
        FixedPostTriggerNumber = Integer.parseInt(FixedPostTriggerArray[2]);
    }

    //发贴入口
    @Override
    public void sendPost() {

        SetFixedPostProperty();

        httpclient = getHttpClient();

        NextWait = 0;

        PostCountBefore = 0;

        PostCountAfter = getCurrentPostCount() + RestWaitPostCount - 1;

        tempPostContent = this.PostContent;

        RestWaitPostCountTemp = this.RestWaitPostCount;

        DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Post thread is starting.");

        List<PostContentEntity> PostContentEntitys = getPostContentEntitys();

        List<String> FileTextLinesList = getFileTextLinesList();

        beforeSendPost(driver);

        sendPostIteration(PostContentEntitys, FileTextLinesList, driver);

        afterSendPost();

        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Post thread is Stoped.");
    }

    //发贴前置操作
    public void beforeSendPost(WebDriver driver) {
        StartFromPostPolling();
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
        httpclient.getConnectionManager().shutdown();
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
        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] message: " + i + " " + tempPostContent);

    }

    //发贴等待时间
    public void sendPostWait(int i, WebDriver driver) {

        NextWait++;

        WaitFixedTime(FixedWaitTime * 1000 + (int) (1 + Math.random() * (RandomWaitTime - 1 + 1)) * 1000);

        if (i % RefreshPostCount == 0) {
            driver.navigate().refresh();
        }

        if (Objects.equals(RestWaitPostCountTemp, NextWait) && RestWaitPostCountTemp > 0 && RestWaitTime > 0) {
            RestWaitTime(RestWaitTime);
            RestWaitPostCountTemp = (int) (RestWaitPostCount - RestWaitPostCountOffset + Math.random() * (RestWaitPostCount + RestWaitPostCountOffset - (RestWaitPostCount - RestWaitPostCountOffset) + 1));
            System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] will take a next rest after " + RestWaitPostCountTemp + " posts.");
            NextWait = 0;
        }
    }

    //休息等待时间
    public void RestWaitTime(Integer RestWaitTime) {

        Integer AdjustedRestWaitTime;
        AdjustedRestWaitTime = (int) (RestWaitTime * (1 - 0.2) + Math.random() * (RestWaitTime * (1 + 0.2) - RestWaitTime * (1 - 0.2) + 1)) * 1000;
        long printTime = 0;
        if (!(PostEntity.contains("Common") | PostEntity.contains("Qiku360") | PostEntity.contains("LiCai"))) {

            PostCountBefore = getCurrentPostCount();

            System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] is taking a rest " + AdjustedRestWaitTime / 1000 + "s.");
            WaitFixedTime(AdjustedRestWaitTime);

            PostCountAfter = getCurrentPostCount();

            if (!PostEntity.contains("Fixed")) {
                while (true) {
                    if (PostCountBefore - PostCountAfter >= 0) {
                        WaitFixedTime(20000);
                        printTime += 20;
                    } else if (PostCountBefore - PostCountAfter < 0) {
                        //System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] has been taking a additional rest " + printTime + "s. (Break)Current real time post count is： " + PostCountAfter);
                        break;
                    }
                    if (printTime == 20 | printTime % 300 == 0) {
                        System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] has been taking an additional rest " + printTime + "s. Current real time post count is： " + PostCountAfter);
                    }
                    PostCountAfter = getCurrentPostCount();
                }
            }
        } else {
            System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] is taking a rest " + AdjustedRestWaitTime / 1000 + "s.");
            WaitFixedTime(AdjustedRestWaitTime);
        }

    }

    //固定等待时间
    public void WaitFixedTime(long time) {
        try {
            Thread.currentThread().sleep(time);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public int getCurrentPostCount() {
        int result = 0;
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

            if (PostEntity.contains("Meizu")) {
                String Spliter1 = "</em>#</span>";
                String Spliter2 = "y\"><em>";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Oppo")) {
                String Spliter1 = "<span class=\"Fr MR20\">评论";
                String Spliter2 = "</span>";
                result = getLastPostCountMode1(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Flyme")) {
                String Spliter1 = "</em><em>F</em>";
                String Spliter2 = "<em>";
                result = getLastPostCountMode3(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Vivo")) {
                String Spliter1 = "楼</em>";
                String Spliter2 = "<em>";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("YunOS")) {
                String Spliter1 = "\\\" onmouseover=";
                String Spliter2 = "id=\\\"readFace_";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Huawei")) {
                String Spliter1 = "楼</span>";
                String Spliter2 = "<span class=\\\"hbt-fav r\\\">";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Oneplus")) {
                String Spliter1 = "<sup>F</sup>";
                String Spliter2 = "<em class=\\\"am-fr\\\">";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Lenovo")) {
                String Spliter1 = "</em><sup>#</sup></a>";
                String Spliter2 = "<em>";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else if (PostEntity.contains("Gimi")) {
                String Spliter1 = "<sup>#</sup>";
                String Spliter2 = "<!--楼层号-->";
                result = getLastPostCountMode2(responseBody, Spliter1, Spliter2);
            } else {
                System.out.println("Error!!!getCurrentPostCount does not support this forum.");
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] getCurrentPostCount has an error! Waiting for next getCurrentPostCount running!");
            WaitFixedTime(500);
            result = getCurrentPostCount();
        }
        return result;
    }

    public int getLastPostCountMode1(String responseBody, String Spliter1, String Spliter2) throws NumberFormatException {
        int result;
        String[] sourceStrArray = responseBody.split(Spliter1);
        if (sourceStrArray.length > 1) {
            String[] sourceStrArray2 = sourceStrArray[1].split(Spliter2);
            result = Integer.parseInt(sourceStrArray2[0].trim());
        } else {
            result = 0;
            System.out.println("Warning! responseBody of getCurrentPostCount is not correct.");
        }
        return result;
    }
//切割两次，取最后一楼的层数

    public int getLastPostCountMode2(String responseBody, String Spliter1, String Spliter2) throws NumberFormatException {
        int result;
        String[] sourceStrArray = responseBody.split(Spliter1);
        //System.out.println(responseBody);
        if (sourceStrArray.length > 1) {
            int ResultIndex = sourceStrArray.length - 2;
            String[] sourceStrArray2 = sourceStrArray[ResultIndex].split(Spliter2);
            int ResultIndex2 = sourceStrArray2.length - 1;
            result = Integer.parseInt(sourceStrArray2[ResultIndex2].trim());
        } else {
            result = 0;
            System.out.println("Warning! responseBody of getCurrentPostCount is not correct.");
        }
        return result;
    }    
    public int getLastPostCountMode3(String responseBody, String Spliter1, String Spliter2) throws NumberFormatException {
        int result;
        String[] sourceStrArray = responseBody.split(Spliter1);
        //System.out.println(responseBody);
        if (sourceStrArray.length > 1) {
            //int ResultIndex = sourceStrArray.length - 2;
            String[] sourceStrArray2 = sourceStrArray[0].split(Spliter2);
            int ResultIndex2 = sourceStrArray2.length - 1;
            result = Integer.parseInt(sourceStrArray2[ResultIndex2].trim());
        } else {
            result = 0;
            System.out.println("Warning! responseBody of getCurrentPostCount is not correct.");
        }
        return result;
    } 
    public HttpClient getHttpClient() {
        //ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
        //cm.setMaxTotal(100);
        //HttpClient httpclient = new DefaultHttpClient(cm);
        //httpclient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 1000);
        //httpclient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        return httpclient;
    }

    //抢固定楼层发帖子前的较慢轮询
    public void FixedPostPolling() {
        List<String> FixedPostsList = getFixedPostsList();
        for (int i = 0; i < FixedPostsList.size(); i++) {
            TargetPostCount = 0;
            long printTime = 0;
            int ThisTimeTargetPostCount = Integer.parseInt(FixedPostsList.get(i));
            int CurrentRealTimePostCount = 0;
            while (true) {
                CurrentRealTimePostCount = getCurrentPostCount();
                //>120
                if (ThisTimeTargetPostCount - CurrentRealTimePostCount > FixedPostSlow) {
                    printTime += 100000;
                    WaitFixedTime(100000);
                } //50-120
                else if (ThisTimeTargetPostCount - CurrentRealTimePostCount <= FixedPostSlow && ThisTimeTargetPostCount - CurrentRealTimePostCount > FixedPostFast) {
                    printTime += 5000;
                    WaitFixedTime(5000);
                } //0-50
                else if (ThisTimeTargetPostCount - CurrentRealTimePostCount < FixedPostFast && ThisTimeTargetPostCount - CurrentRealTimePostCount > 0) {
                    TargetPostCount = ThisTimeTargetPostCount;
                    break;
                } else if (ThisTimeTargetPostCount - CurrentRealTimePostCount <= 0) {
                    TargetPostCount = -1;
                    break;
                }
                if (printTime >= 300000) {
                    System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Waiting for start up,current real time post count is： " + CurrentRealTimePostCount);
                    printTime = 0;
                }
            }
            if (TargetPostCount > 0) {
                break;
            }
        }
    }
    //抢固定楼层发帖子前的快速轮询

    public void SentFixedPostPolling() {
        long printTime = 0;
        int CurrentRealTimePostCount = 0;
        while (true) {
            CurrentRealTimePostCount = getCurrentPostCount();
            if (TargetPostCount - CurrentRealTimePostCount < FixedPostTriggerNumber) {
                System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Vying for fixed post,current real time post count is： " + CurrentRealTimePostCount);
                break;
            } //(10+20)-50
            else if (TargetPostCount - CurrentRealTimePostCount <= FixedPostFast && TargetPostCount - CurrentRealTimePostCount >= FixedPostTriggerNumber + 20) {
                WaitFixedTime(500);
                printTime += 500;
            } else {
                WaitFixedTime(200);
                printTime += 200;
            }
            if (printTime >= 30000) {
                System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Vying for fixed post,current real time post count is： " + CurrentRealTimePostCount);
                printTime = 0;
            }
        }
    }

    public void StartFromPostPolling() {
        long printTime = 0;
        int CurrentRealTimePostCount = 0;
        if (!(Integer.parseInt(StartFrom) == 0)) {
            while (true) {
                CurrentRealTimePostCount = getCurrentPostCount();
                if (CurrentRealTimePostCount > Integer.parseInt(StartFrom)) {
                    System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Starting for StartFrom post,current real time post count is： " + CurrentRealTimePostCount);
                    break;
                } else {
                    WaitFixedTime(30000);
                    printTime += 30000;
                }
                if (printTime >= 300000) {
                    System.out.println(DateFormat.format(new Date()) + " [" + Thread.currentThread().getName() + "] [" + Profile + "] Waiting for StartFrom post: " + StartFrom + ",current real time post count is： " + CurrentRealTimePostCount);
                    printTime = 0;
                }
            }
        }
    }
}
