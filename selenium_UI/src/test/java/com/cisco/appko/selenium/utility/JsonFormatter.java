package com.cisco.appko.selenium.utility;

import com.google.gson.*;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by Eugene on 2017-01-10.
 */
public class JsonFormatter {
    public static String jsonStringPrettify(String uglyJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJson);

        return gson.toJson(je);
    }

    public static String extractFirstUsedIdFromAlumniUserSearch(String jsonString) {

        Gson gson = new GsonBuilder().create();

        JsonObject userSearch = gson.fromJson(jsonString, JsonObject.class);
        //JsonElement entry = userSearch.getAsJsonObject("alumniUserSearchOutput").getAsJsonObject("map").getAsJsonArray("entry");
        JsonElement entry = userSearch.getAsJsonArray("alumniUserSearchOutput").get(0).getAsJsonObject().get("userId");//.getAsJsonObject("map").getAsJsonArray("entry");

        return entry.toString();
    }


    @Step("checking that  job array is not empty")
    public static boolean checkThatArrayLocatedByJsonKeyNOTEmpty(String jobsJson, String jsonKeyArray) {

        Gson gson = new GsonBuilder().create();

        JsonObject jobs = gson.fromJson(jobsJson, JsonObject.class);
        JsonArray jobsArray = jobs.getAsJsonArray(jsonKeyArray);  //("jobs");

        return (jobsArray.size() > 0);

    }

    @Step("checking that repsonse is not empty")
    public static boolean checkThatJsonArrayNotEmpty(String jsonString) {
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(jsonString);
        return (jsonArray.size() > 0);
    }


}
