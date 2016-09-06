package PostMachineApp.XMLUtil;

import PostMachineApp.PostStep;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PostStepManagementDAO {
    private static String fileName = System.getProperty("user.dir") + "\\PostStep.xml";
    private static Boolean EnablePostStep;
    private static Integer StepID;
    private static String EntityName;
    private static String StepDescription;
    private static String ParentProperty;
    private static String ParentPropertyValue;
    private static String ControlProperty;
    private static String ControlPropertyValue;
    private static String StepAction;
    private static String StepDataValue;

    /**
     * 获取XML中所有的用户信息
     *
     * @return
     *
     */
    @SuppressWarnings("unchecked")
    public static List<PostStep> getAllPostStep() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostStepElements = rootElement.elements();
        List<PostStep> PostSteps = new ArrayList<PostStep>();
        for (Element PostStepElement : PostStepElements) {
            EnablePostStep = Boolean.valueOf(PostStepElement.element("EnablePostStep").getTextTrim());
            StepID = Integer.parseInt(PostStepElement.attribute("StepID").getText());
            EntityName = PostStepElement.element("EntityName").getTextTrim();
            StepDescription = PostStepElement.element("StepDescription").getTextTrim();
            ParentProperty = PostStepElement.element("ParentProperty").getTextTrim();
            ParentPropertyValue = PostStepElement.element("ParentPropertyValue").getTextTrim();
            ControlProperty = PostStepElement.element("ControlProperty").getTextTrim();
            ControlPropertyValue = PostStepElement.element("ControlPropertyValue").getTextTrim();
            StepAction = PostStepElement.element("StepAction").getTextTrim();
            StepDataValue = PostStepElement.element("StepDataValue").getTextTrim();
            PostStep PostStep;
            PostStep = new PostStep(EnablePostStep, StepID, EntityName, StepDescription, ParentProperty, ParentPropertyValue, ControlProperty, ControlPropertyValue, StepAction, StepDataValue);

            PostSteps.add(PostStep);
        }
        return PostSteps;
    }

    public static List<PostStep> getRunPostStep() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostStepElements = rootElement.elements();
        List<PostStep> PostSteps = new ArrayList<PostStep>();
        for (Element PostStepElement : PostStepElements) {
            EnablePostStep = Boolean.valueOf(PostStepElement.element("EnablePostStep").getTextTrim());
            if (EnablePostStep) {
                StepID = Integer.parseInt(PostStepElement.attribute("StepID").getText());
                EntityName = PostStepElement.element("EntityName").getTextTrim();
                StepDescription = PostStepElement.element("StepDescription").getTextTrim();
                ParentProperty = PostStepElement.element("ParentProperty").getTextTrim();
                ParentPropertyValue = PostStepElement.element("ParentPropertyValue").getTextTrim();
                ControlProperty = PostStepElement.element("ControlProperty").getTextTrim();
                ControlPropertyValue = PostStepElement.element("ControlPropertyValue").getTextTrim();
                StepAction = PostStepElement.element("StepAction").getTextTrim();
                StepDataValue = PostStepElement.element("StepDataValue").getTextTrim();
                PostStep PostStep;
                PostStep = new PostStep(EnablePostStep, StepID, EntityName, StepDescription, ParentProperty, ParentPropertyValue, ControlProperty, ControlPropertyValue, StepAction, StepDataValue);

                PostSteps.add(PostStep);
            }
        }
        return PostSteps;
    }

    public static PostStep getPostStepByID(String id) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        PostStep PostStep = null;
        List<Element> PostStepElements = rootElement.elements("PostStep");
        for (Element PostStepElement : PostStepElements) {
            if (PostStepElement.attributeValue("StepID").equals(id)) {
                EnablePostStep = Boolean.valueOf(PostStepElement.element("EnablePostStep").getTextTrim());
                StepID = Integer.parseInt(PostStepElement.attribute("StepID").getText());
                EntityName = PostStepElement.element("EntityName").getTextTrim();
                StepDescription = PostStepElement.element("StepDescription").getTextTrim();
                ParentProperty = PostStepElement.element("ParentProperty").getTextTrim();
                ParentPropertyValue = PostStepElement.element("ParentPropertyValue").getTextTrim();
                ControlProperty = PostStepElement.element("ControlProperty").getTextTrim();
                ControlPropertyValue = PostStepElement.element("ControlPropertyValue").getTextTrim();
                StepAction = PostStepElement.element("StepAction").getTextTrim();
                StepDataValue = PostStepElement.element("StepDataValue").getTextTrim();
               PostStep = new PostStep(EnablePostStep, StepID, EntityName, StepDescription, ParentProperty, ParentPropertyValue, ControlProperty, ControlPropertyValue, StepAction, StepDataValue);

            }
        }
        return PostStep;
    }

    public static List<PostStep> getPostStepByEntity(String Entity) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostStepElements = rootElement.elements();
        List<PostStep> PostSteps = new ArrayList<PostStep>();
        for (Element PostStepElement : PostStepElements) {
            if (PostStepElement.element("EntityName").getTextTrim().equals(Entity)) {
                StepID = Integer.parseInt(PostStepElement.attribute("StepID").getText());
                EntityName = PostStepElement.element("EntityName").getTextTrim();
                StepDescription = PostStepElement.element("StepDescription").getTextTrim();
                ParentProperty = PostStepElement.element("ParentProperty").getTextTrim();
                ParentPropertyValue = PostStepElement.element("ParentPropertyValue").getTextTrim();
                ControlProperty = PostStepElement.element("ControlProperty").getTextTrim();
                ControlPropertyValue = PostStepElement.element("ControlPropertyValue").getTextTrim();
                StepAction = PostStepElement.element("StepAction").getTextTrim();
                StepDataValue = PostStepElement.element("StepDataValue").getTextTrim();
                PostStep PostStep;
                PostStep = new PostStep(EnablePostStep, StepID, EntityName, StepDescription, ParentProperty, ParentPropertyValue, ControlProperty, ControlPropertyValue, StepAction, StepDataValue);

                PostSteps.add(PostStep);
            }
        }
        return PostSteps;
    }

    /**
     * 向XML文件中添加一个user
     *
     * @param PostStep
     */
    public static void add(PostStep PostStep) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element PostStepElement = rootElement.addElement("PostStep");
        PostStepElement.addAttribute("StepID", PostStep.getStepID().toString());
        Element EnablePostStepElement = PostStepElement.addElement("EnablePostStep");
        Element EntityNameElement = PostStepElement.addElement("EntityName");
        Element StepDescriptionElement = PostStepElement.addElement("StepDescription");
        Element ParentPropertyElement = PostStepElement.addElement("ParentProperty");
        Element ParentPropertyValueElement = PostStepElement.addElement("ParentPropertyValue");
        Element ControlPropertyElement = PostStepElement.addElement("ControlProperty");
        Element ControlPropertyValueElement = PostStepElement.addElement("ControlPropertyValue");
        Element StepActionElement = PostStepElement.addElement("StepAction");
        Element StepDataValueElement = PostStepElement.addElement("StepDataValue");

        EnablePostStepElement.setText(PostStep.getEnablePostStep().toString());
        EntityNameElement.setText(PostStep.getEntityName());
        StepDescriptionElement.setText(PostStep.getStepDescription());
        ParentPropertyElement.setText(PostStep.getParentProperty());
        ParentPropertyValueElement.setText(PostStep.getParentPropertyValue());
        ControlPropertyElement.setText(PostStep.getControlProperty());
        ControlPropertyValueElement.setText(PostStep.getControlPropertyValue());
        StepActionElement.setText(PostStep.getStepAction());
        StepDataValueElement.setText(PostStep.getStepDataValue());

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

        List<Element> PostStepElements = rootElement.elements("PostStep");
        for (Element PostStepElement : PostStepElements) {
            if (PostStepElement.attributeValue("StepID").equals(id)) {
                //System.out.println("开始删除.....");
                rootElement.remove(PostStepElement);
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
    public static void update(PostStep PostStep) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostStepElements = rootElement.elements();
        for (Element PostStepElement : PostStepElements) {
            if (PostStepElement.attributeValue("StepID").equals(String.valueOf(PostStep.getStepID()))) {
                if (!PostStepElement.element("EnablePostStep").getText().equals(PostStep.getEnablePostStep().toString())) {
                    PostStepElement.element("EnablePostStep").setText(PostStep.getEnablePostStep().toString());
                }
                if (!PostStepElement.element("EntityName").getText().equals(PostStep.getEntityName())) {
                    PostStepElement.element("EntityName").setText(PostStep.getEntityName());
                }
                if (!PostStepElement.element("StepDescription").getText().equals(PostStep.getStepDescription())) {
                    PostStepElement.element("StepDescription").setText(PostStep.getStepDescription());
                }
                if (!PostStepElement.element("ParentProperty").getText().equals(PostStep.getParentProperty())) {
                    PostStepElement.element("ParentProperty").setText(PostStep.getParentProperty());
                }
                if (!PostStepElement.element("ParentPropertyValue").getText().equals(PostStep.getParentPropertyValue())) {
                    PostStepElement.element("ParentPropertyValue").setText(PostStep.getParentPropertyValue());
                }
                if (!PostStepElement.element("ControlProperty").getText().equals(PostStep.getControlProperty())) {
                    PostStepElement.element("ControlProperty").setText(PostStep.getControlProperty());
                }
                if (!PostStepElement.element("ControlPropertyValue").getText().equals(PostStep.getControlPropertyValue())) {
                    PostStepElement.element("ControlPropertyValue").setText(PostStep.getControlPropertyValue());
                }
                if (!PostStepElement.element("StepAction").getText().equals(PostStep.getStepAction())) {
                    PostStepElement.element("StepAction").setText(PostStep.getStepAction());
                }
                if (!PostStepElement.element("StepDataValue").getText().equals(PostStep.getStepDataValue())) {
                    PostStepElement.element("StepDataValue").setText(PostStep.getStepDataValue());
                }
            }
        }
        write2XML(document);
    }

    public static Boolean StepIDisExisting(PostStep PostStep) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> PostStepElements = rootElement.elements();
        for (Element PostStepElement : PostStepElements) {
            if (PostStepElement.attributeValue("StepID").equals(String.valueOf(PostStep.getStepID()))) {
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
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostStep.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\PostStep.xml"));
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
        List<Element> PostStepElements = rootElement.elements();
        for (Element element : PostStepElements) {
            int id = Integer.valueOf(element.attributeValue("StepID"));
            if (maxId < id) {
                maxId = id;
            }
        }
        return maxId;
    }
}
