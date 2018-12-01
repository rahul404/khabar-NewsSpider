package org.kjsce.khabar.service.classifier;

import com.paralleldots.paralleldots.App;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

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
            JSONObject jsonObject = createCategory();
            String response = app.custom_classifier(text,jsonObject);
            System.out.println("\n\n\n\n "+response+"\n\n");
        }
        catch (Exception e){ }
        return false;
    }
}

