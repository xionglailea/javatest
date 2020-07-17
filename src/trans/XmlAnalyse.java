package trans;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;

/**
 * Created by arctest on 2015/10/28.
 */
public class XmlAnalyse {
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        analyse();
    }

    public static void analyse() throws Exception {
//        FileInputStream is = new FileInputStream("D:\\work\\zxjres\\skillconfig\\风王铁锤.xml");
        FileInputStream is = new FileInputStream(System.getProperty("user.dir") + "/src/config/test.xml");
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        int length = childNodes.getLength();
        System.out.println("根目录为 "+ root.getNodeName());
        System.out.println(root.getTagName());
        System.out.println("长度为 " + length);
        for (int i = 0; i < length; i++) {
            Node n = childNodes.item(i);
            if(n.ELEMENT_NODE != n.getNodeType()){
                continue;
            }
//            Element e = (Element) n;
//            System.out.println(n.getNodeName() +" " +((Element) n).getAttribute("id"));
//            NodeList list = e.getChildNodes();
//            System.out.println(list.item(1).getNodeName() + " " + ((Element) list.item(1)).getAttribute("duration"));
        }
    }

}
