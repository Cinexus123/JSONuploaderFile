package gates;


import validation.Validation;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class Output {

    public void createOutputFile(List myJsonObject) {
        FileWriter file = null;
        Properties prop = new Properties();
        String fileName = "config";
        InputStream is = null;
        Validation validation = new Validation();
        String prettyJson;

        try {
            is = new FileInputStream(fileName);
            prop.load(is);
            file = new FileWriter(prop.getProperty("\"output\""));
            for(int i = 0; i < myJsonObject.size();i++)
            {
                prettyJson = validation.toPrettyJsonFormat(myJsonObject.get(i).toString());
                file.write(prettyJson);
            }
            file.close();
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
