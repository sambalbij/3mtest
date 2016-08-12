package org.driem.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by martijn.koenis on 12-8-2016.
 */
public class EndBill {
    private Map<Integer,ParticipantToPay> partipantToPays = new HashMap<>();

    public void addEmptyParticipantToPay(Participant participant)
    {
        partipantToPays.put(participant.getId(),new ParticipantToPay(participant));
    }

    public void addAmountToParticipantToPay(int id, double amount, String description)
    {
        partipantToPays.get(id).addToPay(description,amount);
    }

    public Map<Integer, ParticipantToPay> getPartipantToPays() {
        return partipantToPays;
    }

}


