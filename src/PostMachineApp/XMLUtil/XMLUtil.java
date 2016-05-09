package PostMachineApp.XMLUtil;

import PostMachineApp.EntityInterface.ForumPost;
import PostMachineApp.ForumPostFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLUtil {

    private static String fileName = System.getProperty("user.dir") + "\\ForumPost.xml";
    private static Boolean EnableThread;
    private static Integer ThreadID;
    private static String FirefoxPath;
    private static String Profile;
    private static String PostEntity;
    private static String StartTimeString;
    private static long StartTime;
    private static Boolean EnableStopTime;
    private static String StopTimeString;
    private static long StopTime;
    private static Integer RefreshPostCount;
    private static long PostCount;
    private static Integer FixedWaitTime;
    private static Integer RandomWaitTime;
    private static String PostUrl;
    private static String PostContent;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<ForumPost> getAllForumPost() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements();
        List<ForumPost> ForumPosts = new ArrayList<ForumPost>();
        for (Element postElement : postElements) {
            EnableThread = Boolean.valueOf(postElement.element("EnableThread").getTextTrim());
            ThreadID = Integer.parseInt(postElement.attribute("ThreadID").getText());
            FirefoxPath = postElement.element("FirefoxPath").getTextTrim();
            Profile = postElement.element("Profile").getTextTrim();
            PostEntity = postElement.element("PostEntity").getTextTrim();
            StartTime = Long.parseLong(postElement.element("StartTime").getTextTrim());
            EnableStopTime = Boolean.valueOf(postElement.element("EnableStopTime").getTextTrim());
            StopTime = Long.parseLong(postElement.element("StopTime").getTextTrim());
            RefreshPostCount = Integer.parseInt(postElement.element("RefreshPostCount").getText());
            PostCount = Long.parseLong(postElement.element("PostCount").getTextTrim());
            FixedWaitTime = Integer.parseInt(postElement.element("FixedWaitTime").getText());
            RandomWaitTime = Integer.parseInt(postElement.element("RandomWaitTime").getText());
            PostUrl = postElement.element("PostUrl").getTextTrim();
            PostContent = postElement.element("PostContent").getTextTrim();
           ForumPost ForumPost = ForumPostFactory.CreateForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);

            ForumPosts.add(ForumPost);
        }
        return ForumPosts;
    }
    
    public static List<ForumPost> getRunForumPost() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements();
        List<ForumPost> ForumPosts = new ArrayList<ForumPost>();
        for (Element postElement : postElements) {
            EnableThread = Boolean.valueOf(postElement.element("EnableThread").getTextTrim());
            if (EnableThread){
            ThreadID = Integer.parseInt(postElement.attribute("ThreadID").getText());
            FirefoxPath = postElement.element("FirefoxPath").getTextTrim();
            Profile = postElement.element("Profile").getTextTrim();
            PostEntity = postElement.element("PostEntity").getTextTrim();
            StartTime = Long.parseLong(postElement.element("StartTime").getTextTrim());
            EnableStopTime = Boolean.valueOf(postElement.element("EnableStopTime").getTextTrim());
            StopTime = Long.parseLong(postElement.element("StopTime").getTextTrim());
            RefreshPostCount = Integer.parseInt(postElement.element("RefreshPostCount").getText());
            PostCount = Long.parseLong(postElement.element("PostCount").getTextTrim());
            FixedWaitTime = Integer.parseInt(postElement.element("FixedWaitTime").getText());
            RandomWaitTime = Integer.parseInt(postElement.element("RandomWaitTime").getText());
            PostUrl = postElement.element("PostUrl").getTextTrim();
            PostContent = postElement.element("PostContent").getTextTrim();
            ForumPost ForumPost = ForumPostFactory.CreateForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);

            ForumPosts.add(ForumPost);}
        }
        return ForumPosts;
    }

    public static ForumPost getForumPostByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        ForumPost ForumPost=null;
        List<Element> postElements = rootElement.elements("ForumPost");
        for (Element postElement : postElements) {
            if (postElement.attributeValue("ThreadID").equals(id)) {
                EnableThread = Boolean.valueOf(postElement.element("EnableThread").getTextTrim());
                ThreadID = Integer.parseInt(postElement.attribute("ThreadID").getText());
                FirefoxPath = postElement.element("FirefoxPath").getTextTrim();
                Profile = postElement.element("Profile").getTextTrim();
                PostEntity = postElement.element("PostEntity").getTextTrim();
                StartTime = Long.parseLong(postElement.element("StartTime").getTextTrim());
                EnableStopTime = Boolean.valueOf(postElement.element("EnableStopTime").getTextTrim());
                StopTime = Long.parseLong(postElement.element("StopTime").getTextTrim());
                RefreshPostCount = Integer.parseInt(postElement.element("RefreshPostCount").getText());
                PostCount = Long.parseLong(postElement.element("PostCount").getTextTrim());
                FixedWaitTime = Integer.parseInt(postElement.element("FixedWaitTime").getText());
                RandomWaitTime = Integer.parseInt(postElement.element("RandomWaitTime").getText());
                PostUrl = postElement.element("PostUrl").getTextTrim();
                PostContent = postElement.element("PostContent").getTextTrim();
                ForumPost = ForumPostFactory.CreateForumPost(EnableThread, ThreadID, FirefoxPath, Profile, PostEntity, StartTime, EnableStopTime, StopTime, RefreshPostCount, PostCount, FixedWaitTime, RandomWaitTime, PostUrl, PostContent);
            }
        }
        return ForumPost;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param ForumPost
     */
    public static void add(ForumPost ForumPost) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element postElement = rootElement.addElement("ForumPost");
        //postElement.addAttribute("ThreadID", getMaxId() + 1 + "");
        postElement.addAttribute("ThreadID", ForumPost.getThreadID().toString());
        Element EnableThreadElement = postElement.addElement("EnableThread");
        Element FirefoxPathElement = postElement.addElement("FirefoxPath");
        Element ProfileElement = postElement.addElement("Profile");
        Element PostEntityElement = postElement.addElement("PostEntity");
        Element StartTimeElement = postElement.addElement("StartTime");
        Element EnableStopTimeElement = postElement.addElement("EnableStopTime");
        Element StopTimeElement = postElement.addElement("StopTime");
        Element RefreshPostCountElement = postElement.addElement("RefreshPostCount");
        Element PostCountElement = postElement.addElement("PostCount");
        Element FixedWaitTimeElement = postElement.addElement("FixedWaitTime");
        Element RandomWaitTimeElement = postElement.addElement("RandomWaitTime");
        Element PostUrlElement = postElement.addElement("PostUrl");
        Element PostContentElement = postElement.addElement("PostContent");

        EnableThreadElement.setText(ForumPost.getEnableThread().toString());
        FirefoxPathElement.setText(ForumPost.getFirefoxPath());
        ProfileElement.setText(ForumPost.getProfile());
        PostEntityElement.setText(ForumPost.getPostEntity());
        StartTimeElement.setText(Long.toString(ForumPost.getStartTime()));
        EnableStopTimeElement.setText(ForumPost.getEnableStopTime().toString());
        StopTimeElement.setText(Long.toString(ForumPost.getStopTime()));
        RefreshPostCountElement.setText(Integer.toString(ForumPost.getRefreshPostCount()));
        PostCountElement.setText(Long.toString(ForumPost.getPostCount()));
        FixedWaitTimeElement.setText(Integer.toString(ForumPost.getFixedWaitTime()));
        RandomWaitTimeElement.setText(Integer.toString(ForumPost.getRandomWaitTime()));
        PostUrlElement.setText(ForumPost.getPostUrl());
        PostContentElement.setText(ForumPost.getPostContent());

        write2XML(document);
    }

    /**
     * 根据id删除user
     *
     * @param id
     */
    @SuppressWarnings("unchecked")
    public static void deleteById(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements("ForumPost");
        for (Element postElement : postElements) {
            if (postElement.attributeValue("ThreadID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(postElement);
                //System.out.println("删除结束.....");
            }
        }
        write2XML(document);
    }

    /**
     * 修改user信息
     *
     * @param user
     */
    @SuppressWarnings("unchecked")
    public static void update(ForumPost ForumPost) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements();
        for (Element postElement : postElements) {
            if (postElement.attributeValue("ThreadID").equals(String.valueOf(ForumPost.getThreadID()))) {
                if (!postElement.element("EnableThread").getText().equals(ForumPost.getEnableThread().toString())) {
                    postElement.element("EnableThread").setText(ForumPost.getEnableThread().toString());
                }
                if (!postElement.element("FirefoxPath").getText().equals(ForumPost.getFirefoxPath())) {
                    postElement.element("FirefoxPath").setText(ForumPost.getFirefoxPath());
                }
                if (!postElement.element("Profile").getText().equals(ForumPost.getProfile())) {
                    postElement.element("Profile").setText(ForumPost.getProfile());
                }
                if (!postElement.element("PostEntity").getText().equals(ForumPost.getPostEntity())) {
                    postElement.element("PostEntity").setText(ForumPost.getPostEntity());
                }
                if (!postElement.element("StartTime").getText().equals(Long.toString(ForumPost.getStartTime()))) {
                    postElement.element("StartTime").setText(Long.toString(ForumPost.getStartTime()));
                }
                if (!postElement.element("EnableStopTime").getText().equals(ForumPost.getEnableStopTime().toString())) {
                    postElement.element("EnableStopTime").setText(ForumPost.getEnableStopTime().toString());
                }
                if (!postElement.element("StopTime").getText().equals(Long.toString(ForumPost.getStopTime()))) {
                    postElement.element("StopTime").setText(Long.toString(ForumPost.getStopTime()));
                }
                if (!postElement.element("RefreshPostCount").getText().equals(Integer.toString(ForumPost.getRefreshPostCount()))) {
                    postElement.element("RefreshPostCount").setText(Integer.toString(ForumPost.getRefreshPostCount()));
                }
                if (!postElement.element("PostCount").getText().equals(Long.toString(ForumPost.getPostCount()))) {
                    postElement.element("PostCount").setText(Long.toString(ForumPost.getPostCount()));
                }
                if (!postElement.element("FixedWaitTime").getText().equals(Integer.toString(ForumPost.getFixedWaitTime()))) {
                    postElement.element("FixedWaitTime").setText(Integer.toString(ForumPost.getFixedWaitTime()));
                }
                if (!postElement.element("RandomWaitTime").getText().equals(Integer.toString(ForumPost.getRandomWaitTime()))) {
                    postElement.element("RandomWaitTime").setText(Integer.toString(ForumPost.getRandomWaitTime()));
                }
                if (!postElement.element("PostUrl").getText().equals(ForumPost.getPostUrl())) {
                    postElement.element("PostUrl").setText(ForumPost.getPostUrl());
                }
                if (!postElement.element("PostContent").getText().equals(ForumPost.getPostContent())) {
                    postElement.element("PostContent").setText(ForumPost.getPostContent());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean ThreadIDisExisting(ForumPost ForumPost) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements();
        for (Element postElement : postElements) {
            if (postElement.attributeValue("ThreadID").equals(String.valueOf(ForumPost.getThreadID()))) {
                isExisting = true;
            }
        }
        return isExisting;
    }

    /**
     * 获取根节点
     *
     * @return rootElement
     */
    public static Document getDocument() {
        try {
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\ForumPost.xml";
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(fileName));

            return document;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 把内容写到XML文件中
     *
     * @param document
     */
    public static void write2XML(Document document) {
        try {
            OutputFormat format = new OutputFormat("  ", true, "utf-8");
            format.setTrimText(true);
            XMLWriter writer = new XMLWriter(format);
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\ForumPost.xml"));
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取xml文件中userId的最大值
     *
     * @return int
     */
    @SuppressWarnings("unchecked")
    public static int getMaxId() {
        int maxId = 0;
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        List<Element> postElements = rootElement.elements();
        for (Element element : postElements) {
            int id = Integer.valueOf(element.attributeValue("ThreadID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
