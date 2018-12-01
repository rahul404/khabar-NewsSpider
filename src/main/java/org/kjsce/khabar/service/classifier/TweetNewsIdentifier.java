package org.kjsce.khabar.service.classifier;

import com.paralleldots.paralleldots.App;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.json.JsonArray;
import java.util.HashMap;
import java.util.Map;

@Service
public class TweetNewsIdentifier {
    public static final String PARALLELDOTS_API_KEY = "p8Ytf6BMvrNgkHUyu15y0QXb7DPYmpyrHYqZm3pGQuI";
    private App app;
    {
        app = new App(PARALLELDOTS_API_KEY);
    }

    private JSONObject createCategory() throws ParseException{
        String categoryString = "{ \"news\" : [\"politics\",\"calamity\",\"murder\",\"sports\",\"celebration\"" +
                    ",\"entertainment\",\"economics\"]," +
                "\"joke\":[]}";
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(categoryString.trim());
        return jsonObject;
    }
    public boolean isNews(String text){
        try{
            Map<String, Float> map = new HashMap<>();
            JSONObject jsonObject = createCategory();
            String response = app.custom_classifier(text,jsonObject);
            jsonObject = (JSONObject) new JSONParser().parse(response);
            JsonArray jsonArray = ((JsonArray)jsonObject.get("taxonomy"));
            for(Object object : jsonArray) {
                JSONObject jsonObject1 = (JSONObject) object;
                String name = jsonObject1.get("tag").toString();
                float score = (Float) jsonObject1.get("confidence_score");
                map.put(name, score);
            }
            float newsConfidence = map.get("news");
            float jokeConfidence = map.get("joke");
            if(jokeConfidence > 0.5){
                return false;
            }
            if(newsConfidence>0.5){
                return true;
            }
            return false;
        }
        catch (Exception e){ }
        return false;
    }
}

