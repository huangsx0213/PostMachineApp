package PostMachineApp.XMLUtil;

import PostMachineApp.ControlProperty;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class ControlPropertysDAO {

    private static String fileName = System.getProperty("user.dir") + "\\ControlProperty.xml";
    private static Boolean EnableControlProperty;
    private static Integer ControlPropertyID;
    private static String ControlPropertyName;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<ControlProperty> getAllControlProperty() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> ControlPropertyElements = rootElement.elements();
        List<ControlProperty> ControlPropertys = new ArrayList<ControlProperty>();
        for (Element ControlPropertyElement : ControlPropertyElements) {
            EnableControlProperty = Boolean.valueOf(ControlPropertyElement.element("EnableControlProperty").getTextTrim());
            ControlPropertyID = Integer.parseInt(ControlPropertyElement.attribute("ControlPropertyID").getText());
            ControlPropertyName = ControlPropertyElement.element("ControlPropertyName").getTextTrim();
           ControlProperty ControlProperty = new ControlProperty(EnableControlProperty, ControlPropertyID, ControlPropertyName);

            ControlPropertys.add(ControlProperty);
        }
        return ControlPropertys;
    }
    
    public static ControlProperty getControlPropertyByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        ControlProperty ControlProperty=null;
        List<Element> ControlPropertyElements = rootElement.elements("ControlProperty");
        for (Element ControlPropertyElement : ControlPropertyElements) {
            if (ControlPropertyElement.attributeValue("ControlPropertyID").equals(id)) {
                EnableControlProperty = Boolean.valueOf(ControlPropertyElement.element("EnableControlProperty").getTextTrim());
                ControlPropertyID = Integer.parseInt(ControlPropertyElement.attribute("ControlPropertyID").getText());
                ControlPropertyName = ControlPropertyElement.element("ControlPropertyName").getTextTrim();
                ControlProperty = new ControlProperty(EnableControlProperty, ControlPropertyID, ControlPropertyName);
            }
        }
        return ControlProperty;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param ControlProperty
     */
    public static void add(ControlProperty ControlProperty) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element ControlPropertyElement = rootElement.addElement("ControlProperty");
        ControlPropertyElement.addAttribute("ControlPropertyID", ControlProperty.getControlPropertyID().toString());
        Element EnableControlPropertyElement = ControlPropertyElement.addElement("EnableControlProperty");
        Element ControlPropertyNameElement = ControlPropertyElement.addElement("ControlPropertyName");

        EnableControlPropertyElement.setText(ControlProperty.getEnableControlProperty().toString());
        ControlPropertyNameElement.setText(ControlProperty.getControlPropertyName());


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

        List<Element> ControlPropertyElements = rootElement.elements("ControlProperty");
        for (Element ControlPropertyElement : ControlPropertyElements) {
            if (ControlPropertyElement.attributeValue("ControlPropertyID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(ControlPropertyElement);
                //System.out.println("删除结束.....");
            }
        }
        write2XML(document);
    }

    /**
     * 修改user信息
     *
     * @param ControlProperty
     */
    @SuppressWarnings("unchecked")
    public static void update(ControlProperty ControlProperty) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> ControlPropertyElements = rootElement.elements();
        for (Element ControlPropertyElement : ControlPropertyElements) {
            if (ControlPropertyElement.attributeValue("ControlPropertyID").equals(String.valueOf(ControlProperty.getControlPropertyID()))) {
                if (!ControlPropertyElement.element("EnableControlProperty").getText().equals(ControlProperty.getEnableControlProperty().toString())) {
                    ControlPropertyElement.element("EnableControlProperty").setText(ControlProperty.getEnableControlProperty().toString());
                }
                if (!ControlPropertyElement.element("ControlPropertyName").getText().equals(ControlProperty.getControlPropertyName())) {
                    ControlPropertyElement.element("ControlPropertyName").setText(ControlProperty.getControlPropertyName());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean ControlPropertyIDisExisting(ControlProperty ControlProperty) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> ControlPropertyElements = rootElement.elements();
        for (Element ControlPropertyElement : ControlPropertyElements) {
            if (ControlPropertyElement.attributeValue("ControlPropertyID").equals(String.valueOf(ControlProperty.getControlPropertyID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\ControlProperty.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\ControlProperty.xml"));
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
        List<Element> ControlPropertyElements = rootElement.elements();
        for (Element element : ControlPropertyElements) {
            int id = Integer.valueOf(element.attributeValue("ControlPropertyID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
