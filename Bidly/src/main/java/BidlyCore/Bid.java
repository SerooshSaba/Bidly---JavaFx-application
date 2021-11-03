package BidlyCore;

public class Bid {

    int amount;

    public Bid( int amount ) {
        this.amount = amount;
    }

    boolean isHigher( Bid other ) {
        if ( this.getAmount() > other.getAmount() )
            return true;
        return false;
    }

    boolean isLower( Bid other ) {
        if ( this.getAmount() < other.getAmount() )
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
