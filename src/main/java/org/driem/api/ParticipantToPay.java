package org.driem.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martijn.koenis on 12-8-2016.
 */
public class ParticipantToPay {
    private Participant participant;
    private double toPay;
    private double payed;
     private List<BillItem> billItems;

    public ParticipantToPay(Participant participant) {
        this.participant = participant;
        this.toPay = 0.0;
        this.billItems = new ArrayList<>();
        this.payed = 0.0;
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

    public double getPayed() {
        return payed;
    }

    public void setPayed(double payed) {
        this.payed = payed;
    }

    public void addPayement(double amount)    {
        payed+=amount;
    }
    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }


}
