package org.kjsce.khabar.service.preprocessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@Service
public class BagOfWordsService {
    @Autowired
    private Lemmatizer lemmatizer;

    private List<String> crimeSet = new LinkedList<>();
    private List<String> sportSet = new LinkedList<>();
    private List<String> politicSet = new LinkedList<>();
    private List<String> calamitySet = new LinkedList<>();
    private HashSet<String> stopWordSet = new HashSet<>();
    public BagOfWordsService(){
        crimeSet.add("abuse");
        crimeSet.add("assult");
        crimeSet.add("alert");
        crimeSet.add("bail");
        crimeSet.add("break");
        crimeSet.add("brutal");
        crimeSet.add("bribe");
        crimeSet.add("burglary");
        crimeSet.add("capture");
        crimeSet.add("case");
        crimeSet.add("cheat");
        crimeSet.add("combat");
        crimeSet.add("conspire");
        crimeSet.add("corrupt");
        crimeSet.add("court");
        crimeSet.add("crime");
        crimeSet.add("danger");
        crimeSet.add("damage");
        crimeSet.add("dead");
        crimeSet.add("detain");
        crimeSet.add("drugs");
        crimeSet.add("emergency");
        crimeSet.add("enforce");
        crimeSet.add("escape");
        crimeSet.add("evidence");
        crimeSet.add("explode");
        crimeSet.add("extort");
        crimeSet.add("handcuff");
        crimeSet.add("harm");
        crimeSet.add("hijack");
        crimeSet.add("homicide");
        crimeSet.add("horror");
        crimeSet.add("immoral");
        crimeSet.add("intrude");
        crimeSet.add("intruder");
        crimeSet.add("interpol");
        crimeSet.add("jurisdiction");
        crimeSet.add("judge");
        crimeSet.add("judgement");
        crimeSet.add("malice");
        crimeSet.add("malpractice");
        crimeSet.add("smuggle");
        crimeSet.add("torture");
        crimeSet.add("murder");
        crimeSet.add("kill");
        crimeSet.add("vandalism");
        crimeSet.add("vandalise");
        crimeSet.add("trafficking");
        crimeSet.add("victim");
        crimeSet.add("violate");
        crimeSet.add("violence");
        crimeSet.add("warn");
        crimeSet.add("wanted");
        crimeSet.add("want");
        crimeSet.add("weapon");
        crimeSet.add("witness");

        sportSet.add("match");
        sportSet.add("ball");
        sportSet.add("bat");
        sportSet.add("audience");
        sportSet.add("fight");
        sportSet.add("host");
        sportSet.add("play");
        sportSet.add("visit");
        sportSet.add("football");
        sportSet.add("soccer");
        sportSet.add("cricket");
        sportSet.add("tennis");
        sportSet.add("basketball");
        sportSet.add("basket");
        sportSet.add("win");
        sportSet.add("loose");
        sportSet.add("draw");

        politicSet.add("modi");
        politicSet.add("PM");
        politicSet.add("election");
        politicSet.add("poll");
        politicSet.add("minister");
        politicSet.add("cabinet");
        politicSet.add("director");
        politicSet.add("department");
        politicSet.add("government");
        politicSet.add("state");
        politicSet.add("central");
        politicSet.add("centre");

        calamitySet.add("cyclone");
        calamitySet.add("earthquake");
        calamitySet.add("avalanche");
        calamitySet.add("blizzard");
        calamitySet.add("drought");
        calamitySet.add("fire");
        calamitySet.add("flood");
        calamitySet.add("hailstorm");
        calamitySet.add("sandstorm");
        calamitySet.add("storm");
        calamitySet.add("snowstorm");
        calamitySet.add("tornado");
        calamitySet.add("tsunami");
        calamitySet.add("disaster");
        calamitySet.add("landslide");

        stopWordSet.add("i");
        stopWordSet.add("a");
        stopWordSet.add("be");
        stopWordSet.add("do");
        stopWordSet.add("does");
        stopWordSet.add("doing");
        stopWordSet.add("for");
        stopWordSet.add("from");
        stopWordSet.add("go");
        stopWordSet.add("got");
        stopWordSet.add("me");
        stopWordSet.add("no");
        stopWordSet.add("can");
        stopWordSet.add("could");
        stopWordSet.add("would");
        stopWordSet.add("this");
        stopWordSet.add("that");
        stopWordSet.add("there");
        stopWordSet.add("here");
        stopWordSet.add("their");
        stopWordSet.add("his");
        stopWordSet.add("her");
        stopWordSet.add("it");
        stopWordSet.add("an");
        stopWordSet.add("on");
        stopWordSet.add("under");
    }

    public List<String> removeStopWords(List<String> lemmas){
        for(int i = 0;i<lemmas.size()&&i>0;i++){
            if(stopWordSet.contains(lemmas.get(i))){
                lemmas.remove(i);
                i--;
            }
        }
        return lemmas;
    }
    public boolean hasCrimeElement(List<String> lemmas){
        for(int i = 0;i<lemmas.size();i++){
            for(int j = 0;j<politicSet.size();j++){
                if(crimeSet.get(j).equals(lemmas.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasSportElement(List<String> lemmas){
        for(int i = 0;i<lemmas.size();i++){
            for(int j = 0;j<politicSet.size();j++){
                if(sportSet.get(j).equals(lemmas.get(i))){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasPoliticElement(List<String> lemmas){
        for(int i = 0;i<lemmas.size();i++){
            for(int j = 0;j<politicSet.size();j++){
                if(politicSet.get(j).equals(lemmas.get(i))){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasCalamityElement(List<String> lemmas){
        for(int i = 0;i<lemmas.size();i++){
            for(int j = 0;j<calamitySet.size();j++){
                if(calamitySet.get(j).equals(lemmas.get(i))){
                    return true;
                }
            }
        }
        return false;
    }

    public List<Integer> vectorize(String text){
        List<String> lemmas = lemmatizer.lemmatize(text);
        lemmas = removeStopWords(lemmas);
        LinkedList<Integer> vector = new LinkedList<>();
        if(hasCalamityElement(lemmas)){
            vector.add(1);
        }
        else vector.add(0);
        if(hasCrimeElement(lemmas)){
            vector.add(1);
        }
        else vector.add(0);

        if(hasPoliticElement(lemmas)){
            vector.add(1);
        }
        else vector.add(0);

        if(hasSportElement(lemmas)){
            vector.add(1);
        }
        else vector.add(0);
        return vector;
    }

    public boolean isRequiredTweet(String tweet){
        List<String> lemmas = lemmatizer.lemmatize(tweet);
        lemmas = removeStopWords(lemmas);
        return hasSportElement(lemmas) || hasPoliticElement(lemmas) || hasCrimeElement(lemmas)
                || hasCalamityElement(lemmas);
    }
}
