package org.driem.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martijn.koenis on 12-8-2016.
 */
public class ParticipantToPay {
    private Participant participant;
    private double toPay;

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    private List<BillItem> billItems;

    public ParticipantToPay(Participant participant) {
        this.participant = participant;
        this.toPay = 0.0;
        this.billItems = new ArrayList<>();
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

    public void addToPay(String description,double toPay){
        billItems.add(new BillItem(description,toPay));
        this.toPay += toPay;
    }

}
