package org.driem.api.elastic.query.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jettrocoenradie on 18/08/2016.
 */
public class GetById<T> {
    @JsonProperty(value = "_index")
    private String index;

    @JsonProperty(value = "_type")
    private String type;

    @JsonProperty(value = "_id")
    private String id;

    @JsonProperty(value = "_source")
    private T source;

    @JsonProperty(value = "found")
    private Boolean found;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }
}
