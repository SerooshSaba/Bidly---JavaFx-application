package Interfaces;

import BidlyCore.Antique;
import BidlyCore.Store;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IStoreRepository {

    public ArrayList<Antique> getStoreAntiques( int store_id ) throws SQLException;
    public String getStoreName( int store_id ) throws SQLException;
    public int getAmountOfStores() throws SQLException;
    public ArrayList<Store> getStores() throws SQLException;
    public void deleteStore( int store_id ) throws SQLException;
    public void insertStore( Store store ) throws SQLException;

}