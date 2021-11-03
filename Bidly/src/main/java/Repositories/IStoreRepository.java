package Repositories;

import BidlyCore.Antiqe;
import BidlyCore.Auctioneer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IStoreRepository {

    public ArrayList<Auctioneer> getStores() throws SQLException;
    public int getAmountOfStores() throws SQLException;
    public String getStoreName( int store_id ) throws SQLException;
    public ArrayList<Antiqe> getStoreProducts(int store_id ) throws SQLException;
    public void deleteStore( int store_id ) throws SQLException;


}
