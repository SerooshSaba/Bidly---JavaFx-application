package Repositories;

import BidlyCore.Antique;
import BidlyCore.Store;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IStoreRepository {

    // CREATE
    public void insertStore( Store store ) throws SQLException;

    // READ
    public ArrayList<Store> getStores() throws SQLException;
    public int getAmountOfStores() throws SQLException;
    public String getStoreName( int store_id ) throws SQLException;
    public ArrayList<Antique> getStoreProducts(int store_id ) throws SQLException;

    // DELETE
    public void deleteStore( int store_id ) throws SQLException;

}
