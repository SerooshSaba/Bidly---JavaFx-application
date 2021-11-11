package Repositories;
import BidlyCore.Antique;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IAdminRepository {

    public ArrayList<Antique> getAllProducts() throws SQLException;
    public int getAllBidsAmount() throws SQLException;
    public int getAmountOfStores() throws SQLException;
    public int getAmountOfProducts() throws SQLException;

}
