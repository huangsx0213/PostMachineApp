package PostMachineApp.EntityInterface;

public interface ForumPost {

    public Boolean getEnableThread();

    public Integer getThreadID();

    public String getFirefoxPath();

    public String getProfile();

    public String getPostEntity();

    public long getStartTime();

    public Boolean getEnableStopTime();

    public long getStopTime();

    public Integer getRefreshPostCount();

    public long getPostCount();

    public Integer getFixedWaitTime();

    public Integer getRandomWaitTime();

    public Integer getRestWaitTime();
    
    public Integer getRestWaitPostCount();
    
    public String getPostUrl();

    public String getPostContent();

    public void sentpost();
}
