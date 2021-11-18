package Repositories;
import BidlyCore.Antique;
import java.sql.SQLException;

public interface IAntiqueRepository {

    int insertAntique( Antique antique) throws SQLException;
    int getAmountOfBidsForAntique(int antique_id ) throws SQLException;
    int getHighestBidOfAntique( int antique_id ) throws SQLException;
    void deleteAntique( String antique_id ) throws SQLException;

}
