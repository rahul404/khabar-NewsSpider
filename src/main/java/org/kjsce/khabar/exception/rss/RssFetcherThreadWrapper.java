package org.kjsce.khabar.exception.rss;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.kjsce.khabar.model.rss.RssEntity;
import org.kjsce.khabar.model.rss.RssItem;
import org.kjsce.khabar.service.rss.RssEntityService;
import org.kjsce.khabar.service.rss.RssItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Service
public class RssFetcherThreadWrapper {
    @Autowired
    private RssEntityService rssEntityService;

    @Autowired
    private RssItemService rssItemService;

    private final static String urls[] = {
        "https://timesofindia.indiatimes.com/rssfeeds/-2128838597.cms"
    };
    public void startThreads(){
        for(String url:urls){
            System.out.println("here");
            new RssFetcherThread(new RssFetcher(url));
        }
    }
    public class RssFetcherThread extends Thread{


        private RssFetcher rssFetcher;
        private final static long BASE_TIMEOUT = 5000;
        public RssFetcherThread(RssFetcher rssFetcher){
            this.rssFetcher = rssFetcher;
            this.start();
        }
        public void run(){
            while(true){
                try {
                    Thread.sleep(BASE_TIMEOUT);
                    JSONObject xmlJSON = XML.toJSONObject(this.rssFetcher.fetchResponse());
                    JSONObject channel = xmlJSON.getJSONObject("rss").getJSONObject("channel");
                    JSONArray items = channel.getJSONArray("item");
                    //assuming only 1 channel exists
                    RssEntity rssEntity = new RssEntity();
                    rssEntity.setBase_url(channel.getString("link"));
                    rssEntity.setDescription(channel.getString("description"));

                    try{
                        rssEntity = RssFetcherThreadWrapper.this.rssEntityService.createEntity(rssEntity);

                        for(int i=0; i < items.length(); i++){
                            JSONObject jsonObject = items.getJSONObject(i);
                            RssItem rssItem = new RssItem();
                            rssItem.setTitle(jsonObject.getString("title"));

                            String pubDate = jsonObject.getString("pubDate");
                            rssItem.setPubDate(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH).parse(pubDate));

                            String link = jsonObject.getString("link");
                            RssFetcher fetcher = new RssFetcher(link);
                            rssItem.setLink(link);
                            rssItem.setRssEntity(rssEntity);
                            rssItem.setGuid(jsonObject.getString("guid"));
                            rssItem.setDescription(jsonObject.getString("description"));

                            RssFetcherThreadWrapper.this.rssItemService.createRssItem(rssEntity.getId(), rssItem);

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

//                System.out.println(xmlJSON.toString(4));
                }catch (InterruptedException ie){
                    ie.printStackTrace();
                }catch (JSONException js){
                    js.printStackTrace();
                }
            }
        }

    }



}
