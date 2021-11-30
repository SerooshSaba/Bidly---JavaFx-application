package Repositories;
import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import Interfaces.IAntiqueRepository;
import java.util.ArrayList;

public class AntiqueRepository implements IAntiqueRepository {

    DatabaseAdapter databaseAdapter;

    public AntiqueRepository( String database ) {
        this.databaseAdapter = new DatabaseAdapter( database );
    }

    public ArrayList<Antique> getAllAntiques() {
        return databaseAdapter.getAllAntiques();
    }

    public int getAmountOfBidsForAntique( int antique_id ){
        return databaseAdapter.getAmountOfBidsForAntique( antique_id );
    }

    public int getAllBidsAmount() {
        return databaseAdapter.getAllBidsAmount();
    }

    public int getAmountOfAntiques() {
        return databaseAdapter.getAmountOfAntiques();
    }

    public int getHighestBidOfAntique( int antique_id ) {
        return databaseAdapter.getHighestBidOfAntique( antique_id );
    }

    public void deleteAntique( int antique_id ) {
        databaseAdapter.deleteAntique( antique_id );
    }

    public int insertAntique( Antique antique ) {
        return databaseAdapter.insertAntique( antique );
    }

    public void insertBid( int bid, int id ) {
        databaseAdapter.insertBid( bid, id );
    }

}
