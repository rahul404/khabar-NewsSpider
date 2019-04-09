package org.kjsce.khabar.utils.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LocationClient {
    private final static String BASE_URL = "http://localhost:8081/location";

    public List<String> getLocations(String sentence) throws InterServiceCallFailedException, IOException {
        String url = BASE_URL+"/fetch-locations?sentence="+sentence;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if(response.code() == 200){
            Gson gson = new Gson();
            System.out.println("Sentence = "+sentence);
            String responseString = response.body().string();
            System.out.println("response = "+responseString);
            List<String> locations = gson.fromJson(responseString, new TypeToken<List<String[]>>() {}.getType());
            for(int i = 0;i < locations.size(); i++){
                System.out.println("locations = "+locations.get(i));
            }
            return locations;
        }
        throw new InterServiceCallFailedException(url);
    }
}
