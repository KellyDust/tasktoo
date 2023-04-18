import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;

public class XMLReader {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter comma-separated field names to extract: ");
            String[] fieldNames = scanner.nextLine().split(",");
            scanner.close();

            File inputFile = new File("input.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("record");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element record = (Element) nNode;
                    for (String fieldName : fieldNames) {
                        String value = record.getElementsByTagName(fieldName).item(0).getTextContent();
                        System.out.println(fieldName + ": " + value);
                    }
                    System.out.println("-----------------------------");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
