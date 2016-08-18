package org.driem.api.elastic;

import org.apache.http.HttpEntity;

public interface ResponseHandler {
    void handle(HttpEntity httpEntity);
}
