package PostMachineApp.XMLUtil;

import PostMachineApp.StepAction;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class StepActionDAO {

    private static String fileName = System.getProperty("user.dir") + "\\StepAction.xml";
    private static Boolean EnableStepAction;
    private static Integer StepActionID;
    private static String StepActionName;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<StepAction> getAllStepAction() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> StepActionElements = rootElement.elements();
        List<StepAction> StepActions = new ArrayList<StepAction>();
        for (Element StepActionElement : StepActionElements) {
            EnableStepAction = Boolean.valueOf(StepActionElement.element("EnableStepAction").getTextTrim());
            StepActionID = Integer.parseInt(StepActionElement.attribute("StepActionID").getText());
            StepActionName = StepActionElement.element("StepActionName").getTextTrim();
           StepAction StepAction = new StepAction(EnableStepAction, StepActionID, StepActionName);

            StepActions.add(StepAction);
        }
        return StepActions;
    }
    
    public static StepAction getStepActionByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        StepAction StepAction=null;
        List<Element> StepActionElements = rootElement.elements("StepAction");
        for (Element StepActionElement : StepActionElements) {
            if (StepActionElement.attributeValue("StepActionID").equals(id)) {
                EnableStepAction = Boolean.valueOf(StepActionElement.element("EnableStepAction").getTextTrim());
                StepActionID = Integer.parseInt(StepActionElement.attribute("StepActionID").getText());
                StepActionName = StepActionElement.element("StepActionName").getTextTrim();
                StepAction = new StepAction(EnableStepAction, StepActionID, StepActionName);
            }
        }
        return StepAction;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param StepAction
     */
    public static void add(StepAction StepAction) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element StepActionElement = rootElement.addElement("StepAction");
        StepActionElement.addAttribute("StepActionID", StepAction.getStepActionID().toString());
        Element EnableStepActionElement = StepActionElement.addElement("EnableStepAction");
        Element StepActionNameElement = StepActionElement.addElement("StepActionName");

        EnableStepActionElement.setText(StepAction.getEnableStepAction().toString());
        StepActionNameElement.setText(StepAction.getStepActionName());


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

        List<Element> StepActionElements = rootElement.elements("StepAction");
        for (Element StepActionElement : StepActionElements) {
            if (StepActionElement.attributeValue("StepActionID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(StepActionElement);
                //System.out.println("删除结束.....");
            }
        }
        write2XML(document);
    }

    /**
     * 修改user信息
     *
     * @param StepAction
     */
    @SuppressWarnings("unchecked")
    public static void update(StepAction StepAction) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> StepActionElements = rootElement.elements();
        for (Element StepActionElement : StepActionElements) {
            if (StepActionElement.attributeValue("StepActionID").equals(String.valueOf(StepAction.getStepActionID()))) {
                if (!StepActionElement.element("EnableStepAction").getText().equals(StepAction.getEnableStepAction().toString())) {
                    StepActionElement.element("EnableStepAction").setText(StepAction.getEnableStepAction().toString());
                }
                if (!StepActionElement.element("StepActionName").getText().equals(StepAction.getStepActionName())) {
                    StepActionElement.element("StepActionName").setText(StepAction.getStepActionName());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean StepActionIDisExisting(StepAction StepAction) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> StepActionElements = rootElement.elements();
        for (Element StepActionElement : StepActionElements) {
            if (StepActionElement.attributeValue("StepActionID").equals(String.valueOf(StepAction.getStepActionID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\StepAction.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\StepAction.xml"));
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
        List<Element> StepActionElements = rootElement.elements();
        for (Element element : StepActionElements) {
            int id = Integer.valueOf(element.attributeValue("StepActionID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
