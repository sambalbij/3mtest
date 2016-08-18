package org.driem.api.elastic.index;

import org.driem.api.elastic.ElasticClientException;

/**
 * Exception thrown when having problems interacting with the index api.
 */
public class IndexApiException extends ElasticClientException {
    public IndexApiException(String message) {
        super(message);
    }

    public IndexApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
