package org.kjsce.khabar.utils.client;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TextRazorClient {
    private final static String BASE_URL = "http://localhost:8081/textrazor";

    public ClassifyResponse classify(String sentence) throws InterServiceCallFailedException, IOException {
        String url = BASE_URL+"/classify?sentence="+sentence;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if(response.code() == 200){
            Gson gson = new Gson();
            System.out.println("Sentence = "+sentence);
            String responseString = response.body().string();
            System.out.println("response = "+responseString);
            ClassifyResponse classifyResponse = gson.fromJson(responseString, ClassifyResponse.class);
            System.out.println("Intent = "+classifyResponse.getIntent());
            return classifyResponse;
        }
        throw new InterServiceCallFailedException(url);
    }
}
