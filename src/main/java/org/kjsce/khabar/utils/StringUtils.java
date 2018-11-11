package org.kjsce.khabar.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtils{
        public static String getSHA256(String data){
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte hash[] = digest.digest(data.getBytes(StandardCharsets.UTF_8));
                return new String(hash);
            }catch (NoSuchAlgorithmException nsae){
                nsae.printStackTrace();
                return null;
            }
        }
}
