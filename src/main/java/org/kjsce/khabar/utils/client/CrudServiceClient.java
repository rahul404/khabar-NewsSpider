package org.kjsce.khabar.utils.client;

import okhttp3.*;
import org.json.JSONObject;
import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.transform.OutputKeys.MEDIA_TYPE;

@Component
public class CrudServiceClient {
    private static final String BASE_URL = "http://localhost:8080/v1/khabar";

    public boolean save(TweetEntity tweetEntity, List<String> locations) throws IOException, InterServiceCallFailedException {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject postData = new JSONObject();
        int endIndex = tweetEntity.getText().length() > 10 ? 10 : tweetEntity.getText().length();
        postData.put("title", tweetEntity.getText().substring(0, endIndex));
        postData.put("description", tweetEntity.getText());
        postData.put("image", "");
        postData.put("categoryId", "1");
        postData.put("locations", locations);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData.toString());
        Request request = new Request.Builder()
                .url(BASE_URL+"/news")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println("code = "+response.code());
        if (response.code() == 201 || response.code() == 200)
            return true;
        throw new InterServiceCallFailedException("Couldnt save tweet ");
    }
}
