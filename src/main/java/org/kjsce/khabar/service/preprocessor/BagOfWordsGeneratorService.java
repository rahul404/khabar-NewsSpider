package org.kjsce.khabar.service.preprocessor;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

@Service
public class BagOfWordsGeneratorService {

    private Set<String> vocabulary;
    private boolean initialized = false;
    public void init() throws Exception{
        if(!initialized){
            vocabulary = new HashSet<>();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("vocabulary.txt"))
            ));
            while(true){
                String line = bufferedReader.readLine();
                if(line == null || line.length() == 0){
                    break;
                }
                vocabulary.add(line);
            }
            initialized = true;
        }
    }
    /*this code takes in put a set of sentences and convert it into bag of words*/
    protected Set<String> getVocabulary(File source) throws IOException {
        Set<String> vocabulary = new HashSet<>();
        try(BufferedReader bufferedReader=
                    new BufferedReader(new InputStreamReader(new FileInputStream(source)));){
            while (true){
                String line = bufferedReader.readLine();
                if(line == null || line.length() == 0){
                    break;
                }
                int lastIndex = line.lastIndexOf(",");
                int secondLastIndex = line.lastIndexOf(",",lastIndex-1);
                String classValue = line.substring(lastIndex+1);
                line = line.substring(secondLastIndex+1,lastIndex);
                String[] words = line.split(";");
                for(int i = 0;i<words.length;i++){
                    if(vocabulary.contains(words[i])){
                        continue;
                    }
                    vocabulary.add(words[i]);
                }
            }
        }
        catch (IOException ioe){
            throw ioe;
        }
        return vocabulary;
    }

    public String clean(String line){
        line = line.replace(".","");
        line = line.replace("!","");
        line = line.replace(";","");
        line = line.replace("\"","");
        for(int i = 0;i<9;i++){
            line = line.replace(i+"","");
        }
        line.replace("?","");
        line = line.toLowerCase();
        return line;
    }
    public String getBow(String line,Set<String> vocabulary){
        StringBuffer bow = new StringBuffer();
        line = clean(line);
        String[] words = line.split("(\\s+)");
        LinkedList<String> list = new LinkedList<>();
        vocabulary.forEach(word -> list.add(word));
        HashSet<String> lookup = new HashSet<>();
        for(int i = 0;i<words.length;i++){
            lookup.add(words[1]);
        }
        for(int i = 0;i<list.size()-1;i++){
            if(lookup.contains(list.get(i))){
                bow.append("1,");
            }
            else {
                bow.append("0,");
            }
        }
        if(lookup.contains(list.get(list.size()-1))){
            bow.append("1");
        }
        else {
            bow.append("0");
        }
        //bow.append(classValue+"\n");
        return bow.toString();
    }

    protected String getBow(File source, Set<String> vocabulary) throws IOException{
        LinkedList<String> list = new LinkedList<>();
        vocabulary.forEach(word -> list.add(word) );
        StringBuffer bow = new StringBuffer();
        try(BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(source)));){
            while(true){
                String line = bufferedReader.readLine();
                if(line == null || line.length() == 0){
                    break;
                }
                int lastIndex = line.lastIndexOf(",");
                int secondLastIndex = line.lastIndexOf(",",lastIndex-1);
                String classValue = line.substring(lastIndex+1);
                line = line.substring(secondLastIndex+1,lastIndex);
                String[] words = line.split(";");
                HashSet<String> lookup = new HashSet<>();
                for(int i = 0;i<words.length;i++){
                    lookup.add(words[1]);
                }
                for(int i = 0;i<list.size();i++){
                    if(lookup.contains(list.get(i))){
                        bow.append("1,");
                    }
                    else {
                        bow.append("0,");
                    }
                }
                bow.append(classValue+"\n");
            }
        }
        catch (IOException ioe){
            throw ioe;
        }
        return bow.toString();
    }
    public void convertAndStoreBowTo(File source, File destination) throws IOException{
        Set<String> vocabulary = getVocabulary(source);
        this.vocabulary = vocabulary;
        System.out.println("Bow size = "+vocabulary.size());
        String bow = getBow(source,vocabulary);
        Files.write(destination.toPath(),bow.getBytes());
        Files.write(new File("attribute-info.txt").toPath(),getAttributeInformation(vocabulary).getBytes());
    }
    private String getAttributeInformation(Set<String> vocabulary){
        StringBuffer buffer = new StringBuffer();
        vocabulary.forEach(word -> buffer.append("@attribute "+word+"{0,1}\n"));
        return buffer.toString();
    }


    public Set<String> getVocabulary(){
        return this.vocabulary;
    }
    public static void main(String args[]) throws IOException{
        File source = new File("events.csv");
        File destination = new File("events-bow.arff");
        BagOfWordsGeneratorService bagOfWordsGeneratorService = new BagOfWordsGeneratorService();
        bagOfWordsGeneratorService.convertAndStoreBowTo(source,destination);
    }
}
