package org.kjsce.khabar.service.classifier;

import org.kjsce.khabar.exception.InterServiceCallFailedException;
import org.kjsce.khabar.utils.client.TextRazorClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TopicIdentifier {
    public String classify(String sentence) throws InterServiceCallFailedException, IOException {
        TextRazorClient textRazorClient= new TextRazorClient();
        return textRazorClient.classify(sentence);
    }
}
