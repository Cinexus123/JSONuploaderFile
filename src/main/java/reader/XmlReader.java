package reader;

import gates.Output;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlReader {

    List<String> nodes = new ArrayList<>();
    List<String> blockElements = new ArrayList<>();
    List<JSONObject> myJSONObjects = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject addAttributesObject=new JSONObject();
    Output output = new Output();


    public void readXml(String configFile) throws ParserConfigurationException, IOException, SAXException {

        File file = new File(configFile);

        DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder();

        Document doc = dBuilder.parse(file);

        if (doc.hasChildNodes())
            printNote(doc.getChildNodes());

        separateElements();
        createJsonObject();
    }

    private void printNote(NodeList nodeList) {
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                nodes.add(tempNode.getNodeName() + "*" + tempNode.getTextContent());

                if (tempNode.hasAttributes()) {
                    NamedNodeMap nodeMap = tempNode.getAttributes();

                    for (int i = 0; i < nodeMap.getLength(); i++) {

                        Node node = nodeMap.item(i);
                        nodes.add(node.getNodeName() + "*" + node.getNodeValue());
                    }
                }
                if (tempNode.hasChildNodes())
                    printNote(tempNode.getChildNodes());
            }
        }
    }

    private void separateElements() {

        String elements = new String();


        for(int i = 0; i< nodes.size();i++) {
            if(!nodes.get(i).contains("\n"))
            {
                if(nodes.get(i).contains("klucz") && elements.length() != 0)
                {
                    blockElements.add(elements);
                    elements = new String();
                }
                elements+= nodes.get(i) + "*";
            }
        }
        blockElements.add(elements);
    }

    public void createJsonObject() {

        String[] temporary;
        String[] mandatoryOptions = {"klucz", "identyfikator", "typ"};
        for(String name : blockElements)
        {
            temporary = name.split("\\*");
            for(int w = 0; w < temporary.length ; w+=2)
            {
                if(Arrays.asList(mandatoryOptions).contains(temporary[w]))
                    jsonObject.put(temporary[w],temporary[w+1]);
                else
                    addAttributesObject.put(temporary[w],temporary[w+1]);
            }
            jsonArray.add(addAttributesObject);
            jsonObject.put("simpleAttributes", jsonArray);
            myJSONObjects.add(jsonObject);
            jsonObject = new JSONObject();
            jsonArray = new JSONArray();
            addAttributesObject = new JSONObject();
        }
        output.createOutputFile(myJSONObjects);
    }
}
