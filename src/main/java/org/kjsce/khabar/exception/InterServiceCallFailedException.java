package org.kjsce.khabar.exception;

public class InterServiceCallFailedException extends Exception{
    public InterServiceCallFailedException(String url){
        super("Imter Service call Failed for "+url);
    }
}
