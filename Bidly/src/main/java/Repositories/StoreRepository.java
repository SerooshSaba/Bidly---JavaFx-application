package Repositories;

import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import Interfaces.IStoreRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoreRepository implements IStoreRepository {

    DatabaseAdapter databaseAdapter;

    public StoreRepository( String database ) {
        this.databaseAdapter = new DatabaseAdapter( database );
    }

    public ArrayList<Antique> getStoreAntiques( int store_id ) throws SQLException {
        return databaseAdapter.getStoreAntiques( store_id );
    }

    public String getStoreName( int store_id ) throws SQLException {
        return databaseAdapter.getStoreName( store_id );
    }

    public int getAmountOfStores() throws SQLException {
        return databaseAdapter.getAmountOfStores();
    }

    public ArrayList<Store> getStores() throws SQLException {
        return databaseAdapter.getStores();
    }

    public void deleteStore( int store_id ) throws SQLException {
        databaseAdapter.deleteStore( store_id );
    }

    public void insertStore( Store store ) throws SQLException {
        databaseAdapter.insertStore( store );
    }

}
