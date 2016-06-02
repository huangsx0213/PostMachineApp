package PostMachineApp.XMLUtil;

import PostMachineApp.PostContentEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PostContentPoolDAO {

    private static String fileName = System.getProperty("user.dir") + "\\PostContent.xml";
    private static Boolean EnablePostContent;
    private static Integer PostContentID;
    private static String PostContent;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<PostContentEntity> getAllPostContent() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostContentElements = rootElement.elements();
        List<PostContentEntity> PostContents = new ArrayList<PostContentEntity>();
        for (Element PostContentElement : PostContentElements) {
            EnablePostContent = Boolean.valueOf(PostContentElement.element("EnablePostContent").getTextTrim());
            PostContentID = Integer.parseInt(PostContentElement.attribute("PostContentID").getText());
            PostContent = PostContentElement.element("PostContent").getTextTrim();
           PostContentEntity PostContentEntity = new PostContentEntity(EnablePostContent, PostContentID, PostContent);

            PostContents.add(PostContentEntity);
        }
        return PostContents;
    }
    
    public static PostContentEntity getPostContentByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        PostContentEntity PostContentEntity=null;
        List<Element> PostContentElements = rootElement.elements("PostContent");
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PostContentID").equals(id)) {
                EnablePostContent = Boolean.valueOf(PostContentElement.element("EnablePostContent").getTextTrim());
                PostContentID = Integer.parseInt(PostContentElement.attribute("PostContentID").getText());
                PostContent = PostContentElement.element("PostContent").getTextTrim();
                PostContentEntity = new PostContentEntity(EnablePostContent, PostContentID, PostContent);
            }
        }
        return PostContentEntity;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param PostContent
     */
    public static void add(PostContentEntity PostContentEntity) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element PostContentElement = rootElement.addElement("PostContent");
        PostContentElement.addAttribute("PostContentID", PostContentEntity.getPostContentID().toString());
        Element EnablePostContentElement = PostContentElement.addElement("EnablePostContent");
        Element PostContentNameElement = PostContentElement.addElement("PostContent");

        EnablePostContentElement.setText(PostContentEntity.getEnablePostContent().toString());
        PostContentNameElement.setText(PostContentEntity.getPostContent());


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

        List<Element> PostContentElements = rootElement.elements("PostContent");
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PostContentID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(PostContentElement);
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
    public static void update(PostContentEntity PostContentEntity) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostContentElements = rootElement.elements();
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PostContentID").equals(String.valueOf(PostContentEntity.getPostContentID()))) {
                if (!PostContentElement.element("EnablePostContent").getText().equals(PostContentEntity.getEnablePostContent().toString())) {
                    PostContentElement.element("EnablePostContent").setText(PostContentEntity.getEnablePostContent().toString());
                }
                if (!PostContentElement.element("PostContentName").getText().equals(PostContentEntity.getPostContent())) {
                    PostContentElement.element("PostContentName").setText(PostContentEntity.getPostContent());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean PostContentIDisExisting(PostContentEntity PostContentEntity) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostContentElements = rootElement.elements();
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PostContentID").equals(String.valueOf(PostContentEntity.getPostContentID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostContent.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostContent.xml"));
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
        List<Element> PostContentElements = rootElement.elements();
        for (Element element : PostContentElements) {
            int id = Integer.valueOf(element.attributeValue("PostContentID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
