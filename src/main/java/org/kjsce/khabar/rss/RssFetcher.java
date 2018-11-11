package org.kjsce.khabar.rss;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RssFetcher {
    private String urlToWatch;
    private OkHttpClient okHttpClient;
    private Request request;

    /**
     * This class fetches data from a url
     * @param url
     */
    public RssFetcher(String url){

        this.urlToWatch = url;
        this.okHttpClient = new OkHttpClient();
        this.request = new Request.Builder().url(this.urlToWatch).build();
    }

    /**
     * This method fetches data from the given url
     * @return String: An empty string is returned if the contents of body is null else some markup is returned
     */
    public String fetchResponse(){
        try {
            Response response = this.okHttpClient.newCall(request).execute();
            return response.body() == null ? "" : response.body().string();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }


}
