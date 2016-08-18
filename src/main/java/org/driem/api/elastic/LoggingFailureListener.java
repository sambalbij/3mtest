package org.driem.api.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingFailureListener extends RestClient.FailureListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggingFailureListener.class);

    @Override
    public void onFailure(HttpHost host) {
        logger.warn("The following host just failed {}:{}", host.getHostName(), host.getPort());
    }
}
