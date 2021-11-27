package Interfaces;

import BidlyCore.Antique;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAntiqueRepository {

    public ArrayList<Antique> getAllAntiques() throws SQLException;
    public int getAmountOfBidsForAntique( int antique_id ) throws SQLException;
    public int getAllBidsAmount() throws SQLException;
    public int getAmountOfAntiques() throws SQLException;
    public int getHighestBidOfAntique( int antique_id ) throws SQLException;
    public void deleteAntique( int antique_id ) throws SQLException;
    public int insertAntique( Antique antique ) throws SQLException;
    public void insertBid( int bid, int id ) throws SQLException;

}