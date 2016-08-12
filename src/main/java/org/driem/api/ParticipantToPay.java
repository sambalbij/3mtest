package org.driem.api;

/**
 * Created by martijn.koenis on 12-8-2016.
 */
public class ParticipantToPay {
    private Participant participant;
    private double toPay;

    public ParticipantToPay(Participant participant, double toPay) {
        this.participant = participant;
        this.toPay = toPay;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public double getToPay() {
        return toPay;
    }

    public void setToPay(double toPay) {
        this.toPay = toPay;
    }

    public void addToPay(double toPay){
        this.toPay += toPay;
    }

}
