package org.driem.api.database;

import org.driem.api.Activity;
import org.driem.api.Event;
import org.driem.api.EventRepository;
import org.driem.api.Item;
import org.driem.api.NonUniqueParticipantNameException;
import org.driem.api.Participant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Profile("database")
public class DataSourceEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceEventRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSourceEventRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Event> loadAllEvents() {
        List<Event> events = jdbcTemplate.query("select * from events",
                (resultSet, i) -> extractEventfromResultSet(resultSet));
        logger.debug("Found {} events", events.size());
        return events;
    }

    @Override
    public Event storeEvent(Event event) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addParticipantToEvent(int eventID, Participant participant) throws NonUniqueParticipantNameException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeParticipantFromEvent(int eventID, int participantID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addActivityToEvent(int eventID, Activity activity)
    {
        int update = jdbcTemplate.update(
                "INSERT INTO activities (name, description, cost, event_id) " +
                        "VALUES (?,?,?,?)",
                        activity.getName(),
                        activity.getDescription(),
                        activity.getCost(),
                        eventID
        );
    }

    @Override
    public void removeActivity(int eventID, int activityID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addParticipantToActivity(int eventID, int activityID, int participantID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeParticipantFromActivity(int eventID, int activityID, int participantID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addItemToActivity(int eventID, int activityID, Item item) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeItemFromActivity(int eventID, int activityID, int itemID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void addParticipantToItem(int eventID, int activityID, int itemID, int participantID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void removeParticipantFromItem(int eventID, int activityID, int itemID, int participantID) {
        throw new UnsupportedOperationException();

    }

    @Override
    public Event loadEvent(int id) {
        Event event = jdbcTemplate.queryForObject("select * from events where id = ?",
                new Object[]{id},
                (resultSet, i) -> extractEventfromResultSet(resultSet));

        List<Activity> activities = loadActivitiesForEvent(id);

        for (Activity activity : activities) {
            loadItemsForActivity(activity);
        }

        List<Participant> participants = loadParticipantsForEvent(id);

        Map<Integer, Activity> mappedActivities =
                activities.stream().collect(Collectors.toMap(Activity::getID, Function.identity()));
        Map<Integer, Participant> mappedParticipants =
                participants.stream().collect(Collectors.toMap(Participant::getId, Function.identity()));
        event.setActivities(mappedActivities);
        event.setParticipants(mappedParticipants);

        return event;
    }

    private List<Participant> loadParticipantsForEvent(int id) {
        return jdbcTemplate.query("select * from participants where event_id = ?",
                new Object[]{id}, (resultSet, i) -> {
                    String foundName = resultSet.getString("name");
                    int foundId = resultSet.getInt("id");
                    return new Participant(foundName, foundId);
                });
    }

    private void loadItemsForActivity(Activity activity) {
        List<Item> items = jdbcTemplate.query("select * from items where activity_id = ?",
                new Object[]{activity.getID()}, (resultSet, i) -> {
                    String foundName = resultSet.getString("name");
                    String foundDescription = resultSet.getString("description");
                    double foundCost = resultSet.getDouble("cost");
                    int foundId = resultSet.getInt("id");
                    return new Item(foundId, foundCost, foundName, foundDescription);
                });

        for (Item item : items) {
            item.setParticipants(jdbcTemplate.query("select * from participant_item where item_id = ?",
                    new Object[]{item.getID()}, (resultSet, i) -> resultSet.getInt("participant_id")));
        }

        activity.setItems(items.stream().collect(Collectors.toMap(Item::getID, Function.identity())));
        activity.setParticipants(jdbcTemplate.query("select * from activity_participant where activity_id = ?",
                new Object[]{activity.getID()}, (resultSet, i) -> resultSet.getInt("participant_id")));
    }

    private List<Activity> loadActivitiesForEvent(int id) {
        return jdbcTemplate.query("select * from activities where event_id = ?",
                new Object[]{id}, (resultSet, i) -> {
                    String foundName = resultSet.getString("name");
                    String foundDescription = resultSet.getString("description");
                    int foundId = resultSet.getInt("id");
                    double foundCost = resultSet.getDouble("cost");
                    return new Activity(foundName, foundDescription, foundId, foundCost);
                });
    }

    private Event extractEventfromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String foundName = resultSet.getString("name");
        String foundDescription = resultSet.getString("description");
        boolean foundFinished = resultSet.getBoolean("finished");
        return new Event(id, foundName, foundDescription, foundFinished);
    }
}
