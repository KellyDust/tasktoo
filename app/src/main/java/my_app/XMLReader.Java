import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.HashMap;
import java.util.Map;

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

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> result = new HashMap<>();

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element record = (Element) nNode;
                    Map<String, Object> recordMap = new HashMap<>();
                    for (String fieldName : fieldNames) {
                        String value = record.getElementsByTagName(fieldName).item(0).getTextContent();
                        recordMap.put(fieldName, value);
                    }
                    result.put("record" + (i + 1), recordMap);
                }
            }

            String json = gson.toJson(result);
            System.out.println(json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
