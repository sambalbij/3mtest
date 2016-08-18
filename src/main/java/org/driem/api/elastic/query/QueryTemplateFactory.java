package org.driem.api.elastic.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryTemplateFactory {
    private final QueryService queryService;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public QueryTemplateFactory(QueryService queryService, ObjectMapper jacksonObjectMapper) {
        this.queryService = queryService;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public <T> QueryTemplate<T> createQueryTemplate() {
        return new QueryTemplate<T>(queryService, jacksonObjectMapper);
    }
}
