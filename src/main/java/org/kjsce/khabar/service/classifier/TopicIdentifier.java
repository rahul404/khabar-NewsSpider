package org.kjsce.khabar.service.classifier;

import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.kjsce.khabar.utils.client.ClassifyResponse;
import org.kjsce.khabar.utils.client.ParallelDotsClient;
import org.kjsce.khabar.utils.client.TextRazorClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class TopicIdentifier {
    private static final HashSet<String> NEWS_TOPICS = new HashSet<>(Arrays.asList(getArray()));

    private static String[] getArray(){
        String arr[] = {"news", "crime", "entertainment", "world news", "impact", "politics", "sports", "travel",
                "tech", "religion", "science", "education", "arts and culture", "healthy living"};
        return arr;
    }
    public ClassifyResponse classifyUsingParallelDots(String sentence) throws InterServiceCallFailedException, IOException {
        ParallelDotsClient parallelDotsClient = new ParallelDotsClient();
        return parallelDotsClient.classify(sentence);
    }

    public ClassifyResponse classifyUsingTextRazor(String sentence) throws InterServiceCallFailedException, IOException {
        TextRazorClient textRazorClient= new TextRazorClient();
        return textRazorClient.classify(sentence);
    }

    public boolean isNews(ClassifyResponse classifyResponse){
        return NEWS_TOPICS.contains(classifyResponse.getIntent());
    }
}
