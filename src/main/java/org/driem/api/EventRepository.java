package org.driem.api;

import java.util.List;

public interface EventRepository {
    List<Event> loadAllEvents();

    int storeEvent(Event event);

    /**
     * Add the provided participant name to the event.
     *
     * @param eventID Id of the event to add the participant to.
     * @param participant containing the name of the participant to add.
     * @throws NonUniqueParticipantNameException when the name of the provided participant is already in the list of
     *                                           participants of the specified event
     */
    void addParticipantToEvent(int eventID, Participant participant) throws NonUniqueParticipantNameException;

    void removeParticipantFromEvent(int eventID, int participantID);

    void addActivityToEvent(int eventID, Activity activity);

    void removeActivity(int eventID, int activityID);

    void addParticipantToActivity(int eventID, int activityID, int participantID);

    void removeParticipantFromActivity(int eventID, int activityID, int participantID);

    void addItemToActivity(int eventID, int activityID, Item item);

    void removeItemFromActivity(int eventID, int activityID, int itemID);

    void addParticipantToItem(int eventID, int activityID, int itemID, int participantID);

    void removeParticipantFromItem(int eventID, int activityID, int itemID, int participantID);

    void setPayerToActivity(int eventID, int activityID, int payerID);

    void setActivityCost(int eventId, int activityId, double cost);

    Event loadEvent(int id);
}
