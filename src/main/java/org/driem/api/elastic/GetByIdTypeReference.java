package org.driem.api.elastic;

import com.fasterxml.jackson.core.type.TypeReference;
import org.driem.api.Event;
import org.driem.api.elastic.query.response.GetById;

/**
 * Created by jettrocoenradie on 18/08/2016.
 */
public class GetByIdTypeReference extends TypeReference<GetById<Event>> {
}
