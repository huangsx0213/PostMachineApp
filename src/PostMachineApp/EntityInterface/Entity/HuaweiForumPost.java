package PostMachineApp.EntityInterface.Entity;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;

final public class HuaweiForumPost extends BasicForumPost {

    public HuaweiForumPost(Boolean EnableThread, Integer ThreadID, String FirefoxPath, String Profile, String PostEntity, long StartTime, Boolean EnableStopTime, long StopTime, Integer RefreshPostCount, long PostCount, Integer FixedWaitTime, Integer RandomWaitTime, Integer RestWaitTime, Integer RestWaitPostCount,Integer RestWaitPostCountOffset, String PostUrl, String PostContent,String StartFrom, String FixedPostTrigger, String Remark) {
        super(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, RestWaitTime, RestWaitPostCount,RestWaitPostCountOffset, PostUrl, PostContent,StartFrom,FixedPostTrigger,Remark);
    }

    @Override
    public void beforeSendPost(WebDriver driver) {
        StartFromPostPolling();
        driver = getWebDriverWithSpecifiedProfile();
        driver.get(PostUrl);

        JOptionPane.showMessageDialog(null, "Please input your login credential.", "Credential", JOptionPane.INFORMATION_MESSAGE);

        WaitFixedTime(60000);
    }
}
