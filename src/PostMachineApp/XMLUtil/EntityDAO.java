package PostMachineApp.XMLUtil;

import PostMachineApp.Entity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class EntityDAO {

    private static String fileName = System.getProperty("user.dir") + "\\Entity.xml";
    private static Boolean EnableEntity;
    private static Integer EntityID;
    private static String EntityName;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<Entity> getAllEntity() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> EntityElements = rootElement.elements();
        List<Entity> Entitys = new ArrayList<Entity>();
        for (Element EntityElement : EntityElements) {
            EnableEntity = Boolean.valueOf(EntityElement.element("EnableEntity").getTextTrim());
            EntityID = Integer.parseInt(EntityElement.attribute("EntityID").getText());
            EntityName = EntityElement.element("EntityName").getTextTrim();
           Entity Entity = new Entity(EnableEntity, EntityID, EntityName);

            Entitys.add(Entity);
        }
        return Entitys;
    }
    
    public static Entity getEntityByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        Entity Entity=null;
        List<Element> EntityElements = rootElement.elements("Entity");
        for (Element EntityElement : EntityElements) {
            if (EntityElement.attributeValue("EntityID").equals(id)) {
                EnableEntity = Boolean.valueOf(EntityElement.element("EnableEntity").getTextTrim());
                EntityID = Integer.parseInt(EntityElement.attribute("EntityID").getText());
                EntityName = EntityElement.element("EntityName").getTextTrim();
                Entity = new Entity(EnableEntity, EntityID, EntityName);
            }
        }
        return Entity;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param Entity
     */
    public static void add(Entity Entity) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element EntityElement = rootElement.addElement("Entity");
        EntityElement.addAttribute("EntityID", Entity.getEntityID().toString());
        Element EnableEntityElement = EntityElement.addElement("EnableEntity");
        Element EntityNameElement = EntityElement.addElement("EntityName");

        EnableEntityElement.setText(Entity.getEnableEntity().toString());
        EntityNameElement.setText(Entity.getEntityName());


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

        List<Element> EntityElements = rootElement.elements("Entity");
        for (Element EntityElement : EntityElements) {
            if (EntityElement.attributeValue("EntityID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(EntityElement);
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
    public static void update(Entity Entity) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> EntityElements = rootElement.elements();
        for (Element EntityElement : EntityElements) {
            if (EntityElement.attributeValue("EntityID").equals(String.valueOf(Entity.getEntityID()))) {
                if (!EntityElement.element("EnableEntity").getText().equals(Entity.getEnableEntity().toString())) {
                    EntityElement.element("EnableEntity").setText(Entity.getEnableEntity().toString());
                }
                if (!EntityElement.element("EntityName").getText().equals(Entity.getEntityName())) {
                    EntityElement.element("EntityName").setText(Entity.getEntityName());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean EntityIDisExisting(Entity Entity) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> EntityElements = rootElement.elements();
        for (Element EntityElement : EntityElements) {
            if (EntityElement.attributeValue("EntityID").equals(String.valueOf(Entity.getEntityID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\Entity.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\Entity.xml"));
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
        List<Element> EntityElements = rootElement.elements();
        for (Element element : EntityElements) {
            int id = Integer.valueOf(element.attributeValue("EntityID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
