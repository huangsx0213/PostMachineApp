package PostMachineApp.XMLUtil;

import PostMachineApp.GlobalSetting;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class GlobalSettingDAO {

    private static String fileName = System.getProperty("user.dir") + "\\GlobalSetting.xml";
    private static String FirefoxInstallationPath;
    private static String UserAgentString;
    private static String WorkstationName;
private static String id;
    public static GlobalSetting getGlobalSetting() {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        GlobalSetting GlobalSetting = null;
        List<Element> postElements = rootElement.elements("GlobalSetting");
        for (Element postElement : postElements) {

            FirefoxInstallationPath = postElement.element("FirefoxInstallationPath").getTextTrim();
            UserAgentString = postElement.element("UserAgentString").getTextTrim();
            WorkstationName = postElement.element("WorkstationName").getTextTrim();
            GlobalSetting = new GlobalSetting(id,FirefoxInstallationPath, UserAgentString, WorkstationName);
        }

        return GlobalSetting;
    }

    /**
     * GlobalSetting
     *
     * @param GlobalSetting

     */
    public static void add(GlobalSetting GlobalSetting) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        Element GlobalSettingElement = rootElement.addElement("GlobalSetting");

        GlobalSettingElement.addAttribute("id", "1");
        Element FirefoxInstallationPathElement = GlobalSettingElement.addElement("FirefoxInstallationPath");
        Element UserAgentStringElement = GlobalSettingElement.addElement("UserAgentString");
        Element WorkstationNameElement = GlobalSettingElement.addElement("WorkstationName");

        FirefoxInstallationPathElement.setText(GlobalSetting.getFirefoxInstallationPath());
        UserAgentStringElement.setText(GlobalSetting.getUserAgentString());
        WorkstationNameElement.setText(GlobalSetting.getWorkstationName());
        
        write2XML(document);
    }

    /**
     * GlobalSetting
     *
     * @param GlobalSetting
     */
    @SuppressWarnings("unchecked")
    public static void update(GlobalSetting GlobalSetting) {
        Document document = getDocument();
        Element rootElement = document.getRootElement();
        List<Element> postElements = rootElement.elements("GlobalSetting");
        for (Element postElement : postElements) {
             if (postElement.attributeValue("id").equals(String.valueOf(GlobalSetting.getId()))) {
            if (!postElement.element("FirefoxInstallationPath").getText().equals(GlobalSetting.getFirefoxInstallationPath())) {
                postElement.element("FirefoxInstallationPath").setText(GlobalSetting.getFirefoxInstallationPath());
            }
            if (!postElement.element("UserAgentString").getText().equals(GlobalSetting.getUserAgentString())) {
                postElement.element("UserAgentString").setText(GlobalSetting.getUserAgentString());
            }
            if (!postElement.element("WorkstationName").getText().equals(GlobalSetting.getWorkstationName())) {
                postElement.element("WorkstationName").setText(GlobalSetting.getWorkstationName());
            }}
        }

        write2XML(document);
    }

    /**
     * 获取根节点
     *
     * @return rootElement
     */
    public static Document getDocument() {
        try {
            String fileName = System.getProperty("user.dir") + "\\src\\PostMachineApp\\GlobalSetting.xml";
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
            writer.setOutputStream(new FileOutputStream(System.getProperty("user.dir") + "\\src\\PostMachineApp\\GlobalSetting.xml"));
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static Boolean GlobalSettingIDisExisting(GlobalSetting GlobalSetting) {
        Boolean isExisting = false;
        Document document = getDocument();
        Element rootElement = document.getRootElement();

        List<Element> postElements = rootElement.elements();
        for (Element postElement : postElements) {
            if (postElement.attributeValue("id").equals(String.valueOf(GlobalSetting.getId()))) {
                isExisting = true;
            }
        }
        return isExisting;
    }
}
