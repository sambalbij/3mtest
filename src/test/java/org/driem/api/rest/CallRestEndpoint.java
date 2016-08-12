package org.driem.api.rest;

import org.junit.Test;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.fail;

/**
 * Created by jettrocoenradie on 12/08/2016.
 */
public class CallRestEndpoint {


    public static final String BASE_URL = "http://localhost:8080/event";

    @Test
    public void removeActivity() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.delete(BASE_URL + "/3/activity/6");
            fail();
        } catch (HttpServerErrorException e) {
            // as expected
        }
    }
}
