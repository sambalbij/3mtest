package org.driem.api;

/**
 * Created by mark.van.kessel on 12-8-2016.
 */
public class BillItem
{
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    String description;
    double cost;

    public BillItem(String description, double cost)
    {
        this.description = description;
        this.cost = cost;
    }
}
