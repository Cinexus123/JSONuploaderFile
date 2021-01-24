package gates;

import org.xml.sax.SAXException;
import reader.CsvReader;
import reader.XmlReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Input {

    public void modeSelection() throws IOException, ParserConfigurationException, SAXException {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        String configFile;

        while(flag)
        {
            System.out.print("Please select a mode: \n 1 - xml file \n 2 - csv file \n");
            char number = sc.next().charAt(0);

            switch(number) {
                case '1':
                    XmlReader xmlReader = new XmlReader();
                    configFile = readInputConfigFile('1');
                    xmlReader.readXml(configFile);
                    flag = false;
                    break;

                case '2':
                    CsvReader csvReader = new CsvReader();
                    configFile = readInputConfigFile('2');
                    csvReader.readCsv(configFile);
                    flag = false;
                    break;

                default:
                    System.out.println("Wrong mode. Try again!!!");
            }
        }
    }

    private static String readInputConfigFile(char sign) throws IOException {
        Properties prop = new Properties();
        String fileName = "config";
        InputStream is = null;
        is = new FileInputStream(fileName);
        prop.load(is);
        String name;
        if(sign == '1')
            return  name = prop.getProperty("\"xml\"");
        else
            return  name = prop.getProperty("\"csv\"");
    }
}
