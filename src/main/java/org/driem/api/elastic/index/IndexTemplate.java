package org.driem.api.elastic.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class IndexTemplate<T> {
    private final static Logger logger = LoggerFactory.getLogger(IndexTemplate.class);
    private final IndexService indexService;
    private final ObjectMapper jacksonObjectMapper;

    private T docToIndex;
    private String index;
    private String type;
    private Integer id;

    public IndexTemplate(IndexService queryService, ObjectMapper jacksonObjectMapper) {
        this.indexService = queryService;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public void execute() {
        try {
            HttpEntity requestBody = new StringEntity(jacksonObjectMapper.writeValueAsString(docToIndex), Charset.defaultCharset());
            if (id == null) {
                indexService.indexDocument(index, type, requestBody);
            } else {
                indexService.indexDocument(index, type, id, requestBody);
            }
        } catch (JsonProcessingException e) {
            logger.warn("Error parsing doc to json", e);
            throw new IndexDocumentException("Could not transform provided document into json");
        }
    }

    public void setDocToIndex(T docToIndex) {
        this.docToIndex = docToIndex;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(Integer id) { this.id = id; }
}
