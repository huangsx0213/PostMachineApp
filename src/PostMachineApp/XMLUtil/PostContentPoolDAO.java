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

    private static String fileName = System.getProperty("user.dir") + "\\PostContentEntity.xml";
    private static Boolean EnablePoolContent;
    private static Integer PoolContentID;
    private static String PoolContentPostEntity;
        private static String PoolContent;

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
            EnablePoolContent = Boolean.valueOf(PostContentElement.element("EnablePoolContent").getTextTrim());
            PoolContentID = Integer.parseInt(PostContentElement.attribute("PoolContentID").getText());
            PoolContentPostEntity = PostContentElement.element("PoolContentPostEntity").getTextTrim();
            PoolContent = PostContentElement.element("PoolContent").getTextTrim();
           PostContentEntity PostContentEntity = new PostContentEntity(EnablePoolContent, PoolContentID,PoolContentPostEntity, PoolContent);

            PostContents.add(PostContentEntity);
        }
        return PostContents;
    }
    
    public static PostContentEntity getPostContentByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        PostContentEntity PostContentEntity=null;
        List<Element> PostContentElements = rootElement.elements("PoolContent");
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PoolContentID").equals(id)) {
                EnablePoolContent = Boolean.valueOf(PostContentElement.element("EnablePoolContent").getTextTrim());
                PoolContentID = Integer.parseInt(PostContentElement.attribute("PoolContentID").getText());
                PoolContentPostEntity = PostContentElement.element("PoolContentPostEntity").getTextTrim();
                 PoolContent = PostContentElement.element("PoolContent").getTextTrim();
                PostContentEntity = new PostContentEntity(EnablePoolContent, PoolContentID,PoolContentPostEntity, PoolContent);
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

        Element PostContentElement = rootElement.addElement("PoolContent");
        PostContentElement.addAttribute("PoolContentID", PostContentEntity.getPoolContentID().toString());
        Element EnablePoolContentElement = PostContentElement.addElement("EnablePoolContent");
        Element PoolContentPostEntityElement = PostContentElement.addElement("PoolContentPostEntity");
        Element PoolContentElement = PostContentElement.addElement("PoolContent");

        EnablePoolContentElement.setText(PostContentEntity.getEnablePoolContent().toString());
        PoolContentPostEntityElement.setText(PostContentEntity.getPoolContentPostEntity());
        PoolContentElement.setText(PostContentEntity.getPoolContent());

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

        List<Element> PostContentElements = rootElement.elements("PoolContent");
        for (Element PostContentElement : PostContentElements) {
            if (PostContentElement.attributeValue("PoolContentID").equals(id)) {
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
            if (PostContentElement.attributeValue("PoolContentID").equals(String.valueOf(PostContentEntity.getPoolContentID()))) {
                if (!PostContentElement.element("EnablePoolContent").getText().equals(PostContentEntity.getEnablePoolContent().toString())) {
                    PostContentElement.element("EnablePoolContent").setText(PostContentEntity.getEnablePoolContent().toString());
                }
                if (!PostContentElement.element("PoolContentPostEntity").getText().equals(PostContentEntity.getPoolContentPostEntity())) {
                    PostContentElement.element("PoolContentPostEntity").setText(PostContentEntity.getPoolContentPostEntity());
                }
                 if (!PostContentElement.element("PoolContent").getText().equals(PostContentEntity.getPoolContent())) {
                    PostContentElement.element("PoolContent").setText(PostContentEntity.getPoolContent());
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
            if (PostContentElement.attributeValue("PoolContentID").equals(String.valueOf(PostContentEntity.getPoolContentID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostContentEntity.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostContentEntity.xml"));
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
            int id = Integer.valueOf(element.attributeValue("PoolContentID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
