package org.kjsce.khabar.service.preprocessor;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

@Service
public class Lemmatizer {
    private StanfordCoreNLP pipeline;

    public Lemmatizer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
//        Properties props;
//        props = new Properties();
//        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
//        this.pipeline = new StanfordCoreNLP(props);
    }

    public List<String> lemmatize(String documentText)
    {
//        List<String> lemmas = new LinkedList<String>();
//
//        // create an empty Annotation just with the given text
//        Annotation document = new Annotation(documentText);
//
//        // run all Annotators on this text
//        this.pipeline.annotate(document);
//
//        // Iterate over all of the sentences found
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        for(CoreMap sentence: sentences) {
//            // Iterate over all tokens in a sentence
//            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//                // Retrieve and add the lemma for each word into the list of lemmas
//                lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
//            }
//        }
//        return lemmas;
        List<String> lemmas = new LinkedList<String>();
        String[] words = documentText.split(" ");
        for(int i = 0;i<words.length;i++){
            lemmas.add(words[i]);
        }
        return lemmas;
    }
}
