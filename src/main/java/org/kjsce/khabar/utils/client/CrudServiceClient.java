package org.kjsce.khabar.utils.client;

import okhttp3.*;
import org.json.JSONObject;
import org.kjsce.khabar.model.twitter.TweetEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static javax.xml.transform.OutputKeys.MEDIA_TYPE;

@Component
public class CrudServiceClient {
    private static final String BASE_URL = "http://localhost:8080/v1/khabar";

    public boolean save(TweetEntity tweetEntity) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        JSONObject postData = new JSONObject();
        int endIndex = tweetEntity.getText().length() > 10 ? 10 : tweetEntity.getText().length();
        postData.put("title", tweetEntity.getText().substring(0, endIndex));
        postData.put("description", tweetEntity.getText());
        postData.put("image", "");
        postData.put("categoryId", "1");
//        RequestBody body = new FormBody.Builder()
//                .add("title", postData.getString("title"))
//                .add("description", postData.getString("description"))
//                .add("image", postData.getString("image"))
//                .add("categoryId", postData.getString("categoryId"))
//                .build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postData.toString());
//        RequestBody body = new MultipartBody.Builder()
//                .addFormDataPart("title", postData.getString("title"))
//                .addFormDataPart("description", postData.getString("description"))
//                .addFormDataPart("image", postData.getString("image"))
//                .addFormDataPart("categoryId", postData.getString("categoryId"))
//                .build();
        Request request = new Request.Builder()
                .url(BASE_URL+"/news")
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        System.out.println("code = "+response.code());
        return response.code() == 201 || response.code() == 200;
    }
}
