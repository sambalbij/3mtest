package org.driem.api.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.driem.api.Activity;
import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.driem.api.Item;
import org.driem.api.NonUniqueParticipantNameException;
import org.driem.api.Participant;
import org.driem.api.elastic.index.IndexService;
import org.driem.api.elastic.index.IndexTemplate;
import org.driem.api.elastic.index.IndexTemplateFactory;
import org.driem.api.elastic.query.QueryService;
import org.driem.api.elastic.query.QueryTemplate;
import org.driem.api.elastic.query.QueryTemplateFactory;
import org.driem.api.elastic.query.response.GetById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Profile("elastic")
@Repository
public class ElasticEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(ElasticEventRepository.class);

    public static final String INDEX = "events";
    public static final String TYPE = "event";
    private IndexService indexService;
    private QueryService queryService;
    private QueryTemplateFactory queryTemplateFactory;
    private IndexTemplateFactory indexTemplateFactory;
    private final ObjectMapper jacksonObjectMapper;

    private int generatedEventId = 1;
    private int generatedParticipantId = 1;
    private int generatedActivityId = 1;
    private int generatedItemId = 1;

    @Autowired
    public ElasticEventRepository(IndexService indexService,
                                  QueryService queryService,
                                  QueryTemplateFactory queryTemplateFactory,
                                  IndexTemplateFactory indexTemplateFactory,
                                  ObjectMapper jacksonObjectMapper) {
        this.indexService = indexService;
        this.queryService = queryService;
        this.queryTemplateFactory = queryTemplateFactory;
        this.indexTemplateFactory = indexTemplateFactory;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Override
    public List<Event> loadAllEvents() {
        QueryTemplate<Event> queryTemplate = queryTemplateFactory.createQueryTemplate();
        queryTemplate.setIndexString(INDEX);
        queryTemplate.setQueryFromTemplate("all_events.twig", null);
        queryTemplate.setQueryTypeReference(new EventTypeReference());

        return queryTemplate.execute();
    }

    @Override
    public int storeEvent(Event event) {
        IndexTemplate<Event> indexTemplate = indexTemplateFactory.createIndexTemplate();
        indexTemplate.setDocToIndex(event);
        indexTemplate.setIndex(INDEX);
        indexTemplate.setType(TYPE);
        if (event.getID() == 0) {
            int newId = generatedEventId++;
            event.setID(newId);
        }
        indexTemplate.setId(event.getID());
        indexTemplate.execute();
        return event.getID();
    }

    @Override
    public void addParticipantToEvent(int eventID, Participant participant) throws NonUniqueParticipantNameException {
        Event event = loadEvent(eventID);
        int newId = generatedParticipantId++;
        participant.setId(newId);
        event.addParticipant(participant);
        storeEvent(event);
    }

    @Override
    public void removeParticipantFromEvent(int eventID, int participantID) {
        Event event = loadEvent(eventID);
        event.removeParticipant(participantID);
        storeEvent(event);
    }

    @Override
    public void addActivityToEvent(int eventID, Activity activity) {
        Event event = loadEvent(eventID);
        int newId = generatedActivityId++;
        activity.setID(newId);
        event.addActivity(activity);
        storeEvent(event);
    }

    @Override
    public void removeActivity(int eventID, int activityID) {
        Event event = loadEvent(eventID);
        event.removeActivity(activityID);
        storeEvent(event);
    }

    @Override
    public void addParticipantToActivity(int eventID, int activityID, int participantID) {
        Event event = loadEvent(eventID);
        event.addParticipantToActivity(activityID, participantID);
        storeEvent(event);
    }

    @Override
    public void removeParticipantFromActivity(int eventID, int activityID, int participantID) {
        Event event = loadEvent(eventID);
        event.removeParticipantFromActivity(activityID, participantID);
        storeEvent(event);
    }

    @Override
    public void addItemToActivity(int eventID, int activityID, Item item) {
        Event event = loadEvent(eventID);
        int newId = generatedItemId++;
        item.setID(newId);
        event.addItemToActivity(activityID, item);
        storeEvent(event);
    }

    @Override
    public void removeItemFromActivity(int eventID, int activityID, int itemID) {
        Event event = loadEvent(eventID);
        event.removeItemFromActivity(activityID, itemID);
        storeEvent(event);
    }

    @Override
    public void addParticipantToItem(int eventID, int activityID, int itemID, int participantID) {
        Event event = loadEvent(eventID);
        event.addParticipantToItem(activityID, itemID, participantID);
        storeEvent(event);

    }

    @Override
    public void removeParticipantFromItem(int eventID, int activityID, int itemID, int participantID) {
        Event event = loadEvent(eventID);
        event.removeParticipantFromItem(activityID, itemID, participantID);
        storeEvent(event);

    }

    @Override
    public void setPayerToActivity(int eventID, int activityID, int payerID) {
        Event event = loadEvent(eventID);

        // TODO Not implemented yet

        storeEvent(event);
    }

    @Override
    public void setActivityCost(int eventID, int activityID, double cost) {
        Event event = loadEvent(eventID);
        event.setActivityCost(activityID, cost);
        storeEvent(event);

    }

    @Override
    public Event loadEvent(int id) {
        List<Event> events = new ArrayList<>();
        queryService.obtainById(INDEX, TYPE, id, entity -> {
            try {
                GetById<Event> queryResponse = jacksonObjectMapper.readValue(entity.getContent(), new GetByIdTypeReference());

                if (queryResponse.getFound()) {
                    events.add(queryResponse.getSource());
                } else {
                    throw new RuntimeException("An event should have been found");
                }
            } catch (IOException e) {
                logger.warn("Cannot execute query", e);
                throw new RuntimeException("An event should have been found");
            }
        });
        return events.get(0);
    }

    @PostConstruct
    public void afterStart() {
        if (!indexService.indexExist(INDEX)) {
            // Would need to create the index here with the right mapping.
            throw new RuntimeException("Beware the repository is not running, no index.");
        }

    }
}
