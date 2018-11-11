package org.kjsce.khabar.service.twitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kjsce.khabar.service.preprocessor.BagOfWordsService;
import org.kjsce.khabar.service.preprocessor.Lemmatizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LemmaTest {
    @Autowired
    Lemmatizer lemmatizer;
    @Autowired
    BagOfWordsService bagOfWordsService;
    @Test
    public void showLemmas(){
        List<String> lemmas  = lemmatizer.lemmatize("Fire at Cavalry. Just saw 4 fire brigades go");
        System.out.println(lemmas.toString());
        List<Integer> vector = bagOfWordsService.vectorize("Fire at Cavalry. Just saw 4 fire brigades go");
        System.out.println(vector.toString());
    }
}
