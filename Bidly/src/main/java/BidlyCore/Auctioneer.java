package BidlyCore;

public class Auctioneer {

    int auctioneer_id; // TODO gjør database tabellen til auctioneer og ( database.sql, databaseAdapter )
    String name;

    public Auctioneer(int auctioneer_id, String name ) {
        this.auctioneer_id = auctioneer_id;
        this.name = name;
    }

    public int getAuctioneer_id() {
        return auctioneer_id;
    }
    public String getName() {
        return name;
    }

    public void setAuctioneer_id(int auctioneer_id) {
        this.auctioneer_id = auctioneer_id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
