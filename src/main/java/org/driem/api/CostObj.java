package org.driem.api;

/**
 * Created by mark.van.kessel on 17-8-2016.
 */
public class CostObj {
    double cost;

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public CostObj(){}
    public CostObj(double cost)
    {
        this.cost = cost;
    }
}
