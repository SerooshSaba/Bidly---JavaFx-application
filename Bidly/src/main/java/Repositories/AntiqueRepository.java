package Repositories;

import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import Interfaces.IAntiqueRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class AntiqueRepository implements IAntiqueRepository {

    DatabaseAdapter databaseAdapter;

    public AntiqueRepository( String database ) {
        this.databaseAdapter = new DatabaseAdapter( database );
    }

    public ArrayList<Antique> getAllAntiques() throws SQLException {
        return databaseAdapter.getAllAntiques();
    }

    public int getAmountOfBidsForAntique( int antique_id ) throws SQLException {
        return databaseAdapter.getAmountOfBidsForAntique( antique_id );
    }

    public int getAllBidsAmount() throws SQLException {
        return databaseAdapter.getAllBidsAmount();
    }

    public int getAmountOfAntiques() throws SQLException {
        return databaseAdapter.getAmountOfAntiques();
    }

    public int getHighestBidOfAntique( int antique_id ) throws SQLException {
        return databaseAdapter.getHighestBidOfAntique( antique_id );
    }

    public void deleteAntique( int antique_id ) throws SQLException {
        databaseAdapter.deleteAntique( antique_id );
    }

    public int insertAntique( Antique antique ) throws SQLException {
        return databaseAdapter.insertAntique( antique );
    }

    public void insertBid( int bid, int id ) throws SQLException {
        databaseAdapter.insertBid( bid, id );
    }

}
