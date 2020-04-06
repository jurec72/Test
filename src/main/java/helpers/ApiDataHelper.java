package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class ApiDataHelper {

    private static JsonObject jsonObject;

    private static JsonObject getEnvironmentConfig() {
            try{
                BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/environment.config"));
                Gson gson = new Gson();
                jsonObject = gson.fromJson(reader, JsonObject.class);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to read environment.config");
            }
        return jsonObject;
    }

    public static String getBaseUri() throws Throwable{
        jsonObject = getEnvironmentConfig();
        String baseUri = jsonObject.get("baseUri").getAsString();
        return baseUri;
    }

    /**
     * retrieves request json object from ApiRequestJsonBody file
     */
    public static String retrieveJsonObjectFromRequestJsonBodyFile(String key) throws IOException {
        String json;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/ApiRequestJsonBody.json"));
        JsonObject object = gson.fromJson(reader, JsonObject.class);
        json = object.get(key).toString();
        return json;
    }

    /**
     * retrieves request json object from ApiResponseJsonBody file
     */
    public static String retrieveJsonObjectFromResponseJsonBodyFile(String key) throws IOException {
        String json;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/ApiResponseJsonBody.json"));
        JsonObject object = gson.fromJson(reader, JsonObject.class);
        json = object.get(key).toString();
        return json;
    }

    /**
     * updates an existing json file with new json object
     * */
    public static void saveObjectIntoJsonFile(String key, Object newValue) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/ApiResponseJsonBody.json"));
        JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
        JsonElement element = gson.toJsonTree(newValue);
        Set<String> keys = jsonObject.keySet();
        Iterator<String> iter = keys.iterator();
        while(iter.hasNext()) {
            if (iter.next().equals(key)) {
                //remove existing property in order to add an updated one
                jsonObject.remove(key);
                break;
            }
        }
        // add updated property to the json file
        jsonObject.add(key, element);
        //Write into the file
        try (JsonWriter writer = new JsonWriter(new FileWriter("src/main/resources/ApiResponseJsonBody.json")))
        {
            gson.toJson(jsonObject, writer);
        }
    }
}
