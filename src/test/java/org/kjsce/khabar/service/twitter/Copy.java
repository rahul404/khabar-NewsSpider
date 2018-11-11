package org.kjsce.khabar.service.twitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

public class Copy {
    public static void main(String args[]) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream("india-news-headlines.csv")));
        long count = 0;

        while(count < 2400000){
            count++;
            System.out.println(count);
            br.readLine();
        }
        String line = "";
        String content = "";
        while ((line = br.readLine()) != null){
            count++;
            content += line+System.lineSeparator();
        }
        Files.write(new File("short-dataset.csv").toPath(),content.getBytes());
    }
}
