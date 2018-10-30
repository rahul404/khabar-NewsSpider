package org.kjsce.khabar.service;

import org.kjsce.khabar.service.preprocessor.BagOfWordsGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Set;

@Service
public class TextClassifierServiceNaiveBayes {
    @Autowired
    private BagOfWordsGeneratorService bagOfWordsGeneratorService;
    private NaiveBayes naiveBayes;
    private Evaluation evaluation;
    private Instances trainingData;

    public void init() throws Exception{
        bagOfWordsGeneratorService.init();
        naiveBayes = new NaiveBayesUpdateable();
        Instances instances= new Instances(new FileReader(new File("events-bow.arff")));
        instances.setClassIndex(instances.numAttributes()-1);
        Instances structure = instances.stringFreeStructure();
        structure.setClassIndex(structure.numAttributes()-1);
        naiveBayes.buildClassifier(structure);
        Instance current ;
        for(int i = 0;i<instances.size();i++){
            current = instances.get(i);
            naiveBayes.updateClassifier(current);
        }
        evaluation = new Evaluation(instances);
        trainingData = instances;
    }
    public String modalSummary() throws Exception{
        evaluation.evaluateModel(naiveBayes,trainingData);
        return evaluation.toSummaryString();
    }
    public String classify(String text) throws Exception{
        Set<String> vocabulary = bagOfWordsGeneratorService.getVocabulary();
        String bow = bagOfWordsGeneratorService.getBow(text,vocabulary);
        /*making the instances for classification */
        ArrayList<String> valueList = new ArrayList<>();
        valueList.add("misc");
        valueList.add("news");
        valueList.add("event");
        Attribute type = new Attribute("type",valueList);
        ArrayList<Attribute> attributeList= new ArrayList<>();
        vocabulary.forEach(word -> attributeList.add(new Attribute(word)));
        attributeList.add(type);
        String[] attributes= bow.split(",");

        Instances dataset =  new Instances("whateevr",attributeList,0);
        double[] attributeValues = new double[attributes.length];
        for(int i = 0;i<attributes.length;i++){
            double x = (double)Integer.parseInt(attributes[i]);
            attributeValues[i] = x;
        }
        Instance i1 = new DenseInstance(1.0,attributeValues);
        dataset.add(i1);
        dataset.setClassIndex(dataset.numAttributes()-1);
        int result = (int)naiveBayes.classifyInstance(trainingData.instance(0));
        switch (result){
            case 0:
                return "misc";
            case 1:
                return "news";
            case 2:
                return "event";
        }
        return null;
    }
}
