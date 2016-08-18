package org.driem.api.elastic.query;

import org.apache.http.entity.StringEntity;
import org.driem.api.elastic.ResponseHandler;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Hashtable;

/**
 * Service containing utility methods to interact with elasticsearch using the provided {@link RestClient}
 */
@Service
public class QueryService {
    private static final Logger logger = LoggerFactory.getLogger(QueryService.class);

    private final RestClient client;

    @Autowired
    public QueryService(RestClient client) {
        this.client = client;
    }

    public void executeQuery(String indexString, String query, ResponseHandler handler) {
        logger.debug("Query to be executed {}", query);

        try {
            Response response = client.performRequest(
                    "GET",
                    indexString + "/_search",
                    new Hashtable<>(),
                    new StringEntity(query, Charset.defaultCharset()));

            handler.handle(response.getEntity());
        } catch (IOException e) {
            logger.warn("Problem while executing request.", e);
            throw new QueryExecutionException("Error when executing a query");
        }
    }

    public void obtainById(String index, String type, Integer id, ResponseHandler handler) {
        logger.debug("Obtain document by ID {}", id);

        try {
            Response response = client.performRequest(
                    "GET",
                    index + "/" + type + "/" + id);

            handler.handle(response.getEntity());
        } catch (IOException e) {
            logger.warn("Problem while executing request.", e);
            throw new QueryExecutionException("Error when executing a query");
        }
    }
}
