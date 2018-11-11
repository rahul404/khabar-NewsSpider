package org.kjsce.khabar.exception;

public class NoSuchTweetException extends NoSuchResourceException{
    public NoSuchTweetException(Long id){
        super("Tweet of id:"+id+" not found");
    }
}
