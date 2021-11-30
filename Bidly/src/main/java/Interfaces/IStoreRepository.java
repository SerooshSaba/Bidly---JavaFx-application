package Interfaces;

import BidlyCore.Antique;
import BidlyCore.Store;

import java.util.ArrayList;

public interface IStoreRepository {

    public ArrayList<Antique> getStoreAntiques( int store_id );
    public String getStoreName( int store_id );
    public int getAmountOfStores();
    public ArrayList<Store> getStores();
    public void deleteStore( int store_id );
    public void insertStore( Store store );

}