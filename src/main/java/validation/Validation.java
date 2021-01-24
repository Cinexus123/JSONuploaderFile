package validation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Validation {

    public String checkCommaInPhrase(String[] lineInArray) {

        String authors = "";
        for(String value : lineInArray)
        {
            authors += value;
        }
        return authors;
    }

    public static String toPrettyJsonFormat(String jsonString) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }


}
