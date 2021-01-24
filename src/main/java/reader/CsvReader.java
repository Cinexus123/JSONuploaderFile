package reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import gates.Output;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import validation.Validation;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    List<JSONObject> myJSONObjects = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    JSONObject addAttributesObject=new JSONObject();
    Validation validation = new Validation();

    public void readCsv(String configFile) throws IOException {
        Output output = new Output();
        String[] parts;
        String[] category = splitFirstLine(configFile);
        int lineCounter = 0;

        try (CSVReader reader = new CSVReader(new FileReader(configFile))) {
            String[] lineInArray;

            while ((lineInArray = reader.readNext()) != null) {
                lineInArray[0] = validation.checkCommaInPhrase(lineInArray);
                parts = lineInArray[0].split(";");

                createJsonObject(category,parts);

                if(!jsonObject.isEmpty() && lineCounter > 0) myJSONObjects.add(jsonObject);

                jsonObject = new JSONObject();
                jsonArray = new JSONArray();
                addAttributesObject = new JSONObject();
                lineCounter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        output.createOutputFile(myJSONObjects);
    }

    public String[] splitFirstLine(String configFile) throws IOException {
        String line0 = Files.readAllLines(Paths.get(configFile)).get(0);
        String[] category = line0.split(";");
        return category;
    }

    public void createJsonObject(String[] category, String[] parts) {

        for(int i = 0; i < parts.length;i++)
        {
            if(i < 3) //mandatory fields: type,key,identifier
                jsonObject.put(category[i], parts[i]);
            else //optional fields
                addAttributesObject.put(category[i],parts[i]);
        }
        jsonArray.add(addAttributesObject);
        jsonObject.put("simpleAttributes", jsonArray);
    }



}
