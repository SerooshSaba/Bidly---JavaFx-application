package Repositories;
import BidlyCore.Antiqe;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminRepository {

    public ArrayList<Antiqe> getAllProducts() throws SQLException;
    public int getAllBidsAmount() throws SQLException;
    public int getAmountOfProducts() throws SQLException;

}
