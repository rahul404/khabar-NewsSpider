package org.kjsce.khabar.service;

import org.kjsce.khabar.service.preprocessor.BagOfWordsGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.util.*;

@Service
public class TextClassifierServiceMultilayerPerceptron {
    @Autowired
    private BagOfWordsGeneratorService bagOfWordsGeneratorService;
    private Instances trainingData;
    private MultilayerPerceptron multilayerPerceptron;
    private Evaluation evaluation;

    public void init() throws Exception{
        bagOfWordsGeneratorService.init();
        trainingData = new Instances(new FileReader(new File("events-bow.arff")));
        trainingData.setClassIndex(trainingData.numAttributes()-1);
        multilayerPerceptron = new MultilayerPerceptron();
        multilayerPerceptron.setLearningRate(0.1);
        multilayerPerceptron.setMomentum(0.2);
        multilayerPerceptron.setTrainingTime(2000);
        multilayerPerceptron.setHiddenLayers("2");
        multilayerPerceptron.buildClassifier(trainingData);
        evaluation = new Evaluation(trainingData);
    }
    public String modalSummary() throws Exception {
        evaluation.evaluateModel(multilayerPerceptron, trainingData);
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
        int result = (int)multilayerPerceptron.classifyInstance(trainingData.instance(0));
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
