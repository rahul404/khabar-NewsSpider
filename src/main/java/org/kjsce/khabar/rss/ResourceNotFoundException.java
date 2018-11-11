package org.kjsce.khabar.rss;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final String MESSAGE ="Resource %s not found with propery %s with value %s ";

    /**
     * A general exception to be thrown when a resource isnt found
     * @param resource The class of object not found
     * @param property The property by which the query was triggered
     * @param value The value of the property that triggered this
     */
    public ResourceNotFoundException(String resource, String property, String value){
        super(String.format(MESSAGE, resource, property, value));
    }
}
