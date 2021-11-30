package Interfaces;

import BidlyCore.Antique;
import java.util.ArrayList;

public interface IAntiqueRepository {

    public ArrayList<Antique> getAllAntiques();
    public int getAmountOfBidsForAntique( int antique_id );
    public int getAllBidsAmount();
    public int getAmountOfAntiques();
    public int getHighestBidOfAntique( int antique_id );
    public void deleteAntique( int antique_id );
    public int insertAntique( Antique antique );
    public void insertBid( int bid, int id );

}