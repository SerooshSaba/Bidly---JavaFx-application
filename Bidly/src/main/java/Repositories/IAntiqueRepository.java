package Repositories;
import BidlyCore.Antique;
import java.sql.SQLException;

public interface IAntiqueRepository {

    public int insertAntiqe( Antique antique) throws SQLException;
    public int getAmountOfBidsForAntiqe(int antiqe_id ) throws SQLException;
    public int getHighestBidOfAntiqe( int antiqe_id ) throws SQLException;
    public void deleteAntiqe( String antiqe_id ) throws SQLException;

}
