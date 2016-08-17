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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Profile("database")
public class DataSourceEventRepository implements EventRepository {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceEventRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert eventsInsert;

    @Autowired
    public DataSourceEventRepository(@SuppressWarnings("SpringJavaAutowiringInspection") JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;

        eventsInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("events")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public List<Event> loadAllEvents() {
        List<Event> events = jdbcTemplate.query("select * from events",
                (resultSet, i) -> extractEventfromResultSet(resultSet));
        logger.debug("Found {} events", events.size());
        return events;
    }

    @Override
    public int storeEvent(Event event) {

        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", event.getName()); // store the String name of employee in the column empName
        parameters.put("description", event.getDescription()); // store the int (as Integer) of the employee in the column dept
        parameters.put("finished", event.getFinished()); // store the int (as Integer) of the employee in the column dept
        final Number key = eventsInsert.executeAndReturnKey(parameters);

        return key.intValue();
    }

    @Override
    public void addParticipantToEvent(int eventID, Participant participant) throws NonUniqueParticipantNameException {
        jdbcTemplate.update("INSERT INTO participants (name,event_id) VALUES (?,?)", participant.getName(), eventID);
    }

    @Override
    public void removeParticipantFromEvent(int eventID, int participantID) {
        jdbcTemplate.update("DELETE FROM participant_item WHERE participant_id = ?", participantID);
        jdbcTemplate.update("DELETE FROM activity_participant WHERE participant_id = ?", participantID);
        jdbcTemplate.update("DELETE FROM participants WHERE id = ?", participantID);

    }

    @Override
    public void addActivityToEvent(int eventID, Activity activity) {
        jdbcTemplate.update(
                "INSERT INTO activities (name, description, cost, event_id) " +
                        "VALUES (?,?,?,?)",
                activity.getName(),
                activity.getDescription(),
                activity.getCost(),
                eventID
        );
    }

    @Override
    public void setActivityCost(int eventId, int activityId, double cost)
    {
        logger.debug("cost" + cost);
        jdbcTemplate.update("UPDATE activities SET cost=? WHERE id = ?",cost,activityId);
    }


    @Override
    public void removeActivity(int eventID, int activityID) {
        // Find items
        List<Integer> itemList = jdbcTemplate.queryForList("SELECT id FROM items WHERE activity_id = ?",Integer.class,activityID);
        // Remove items from activity
        for (Integer item : itemList) {
                removeItemFromActivity(eventID,activityID,item);
        }

        // Delete participants from activity
        jdbcTemplate.update("DELETE FROM activity_participant WHERE activity_id = ?", activityID);

        // Delete Activity itself
        jdbcTemplate.update("DELETE FROM activities where id = ?", activityID);

    }

    @Override
    public void addParticipantToActivity(int eventID, int activityID, int participantID) {
        jdbcTemplate.update("INSERT INTO activity_participant (activity_id,participant_id) VALUES (?,?)", activityID, participantID);
    }

    @Override
    public void removeParticipantFromActivity(int eventID, int activityID, int participantID) {
        List<Integer> ids = jdbcTemplate.queryForList("SELECT id FROM items WHERE activity_id = ?",
                new Object[] {activityID}, Integer.class);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("ids", new HashSet<>(ids));
        parameters.addValue("participantId", participantID);

        namedParameterJdbcTemplate.update("DELETE FROM participant_item WHERE participant_id = :participantId AND item_id IN (:ids)",
                parameters);

        jdbcTemplate.update(
                "DELETE FROM activity_participant WHERE activity_id = ? AND participant_id = ?",
                activityID,
                participantID
        );
    }

    @Override
    public void addItemToActivity(int eventID, int activityID, Item item) {
        jdbcTemplate.update(
                "INSERT INTO items (name, description, cost, activity_id) VALUES (?,?,?,?)",
                item.getName(),
                item.getDescription(),
                item.getCost(),
                activityID
        );
    }

    @Override
    public void removeItemFromActivity(int eventID, int activityID, int itemID) {
        jdbcTemplate.update("DELETE FROM participant_item WHERE item_id = ? ", itemID);
        jdbcTemplate.update("DELETE FROM items WHERE id = ? ", itemID);
    }

    @Override
    public void addParticipantToItem(int eventID, int activityID, int itemID, int participantID) {
        jdbcTemplate.update("INSERT INTO participant_item (participant_id, item_id) VALUES (?,?)", participantID, itemID);
    }

    @Override
    public void removeParticipantFromItem(int eventID, int activityID, int itemID, int participantID) {
        jdbcTemplate.update("DELETE FROM participant_item WHERE item_id = ? AND participant_id = ? ", itemID, participantID);
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

    @Override
    public void setPayerToActivity(int eventID, int activityID, int payerID) {
        jdbcTemplate.update("INSERT INTO activities (buyer_id) WHERE activity_id = ? VALUES (?)", activityID, payerID);
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
                    return new Activity(foundId, foundName, foundDescription, foundCost);
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
