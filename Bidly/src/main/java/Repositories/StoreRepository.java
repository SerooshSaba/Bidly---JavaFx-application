package Repositories;

import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import Interfaces.IStoreRepository;

import java.util.ArrayList;

public class StoreRepository implements IStoreRepository {

    DatabaseAdapter databaseAdapter;

    public StoreRepository( String database ) {
        this.databaseAdapter = new DatabaseAdapter( database );
    }

    public ArrayList<Antique> getStoreAntiques( int store_id ) {
        return databaseAdapter.getStoreAntiques( store_id );
    }

    public String getStoreName( int store_id ) {
        return databaseAdapter.getStoreName( store_id );
    }

    public int getAmountOfStores() {
        return databaseAdapter.getAmountOfStores();
    }

    public ArrayList<Store> getStores(){
        return databaseAdapter.getStores();
    }

    public void deleteStore( int store_id ) {
        databaseAdapter.deleteStore( store_id );
    }

    public void insertStore( Store store ) {
        databaseAdapter.insertStore( store );
    }

}
