package com.bidly.Core.Model;

public class Bid {

    int amount;

    boolean isHigher( Bid other ) {
        if ( this.amount > other.getAmount() )
            return true;
        return false;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
