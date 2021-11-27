package Adapter;
import BidlyCore.Antique;
import BidlyCore.Store;

import java.sql.*;
import java.util.ArrayList;

// Adapter class
public class DatabaseAdapter {

    String database_name;
    Connection connection;
    PreparedStatement statement;

    public DatabaseAdapter( String database_name ) {
        this.database_name = database_name;
    }

    // READ
    public ArrayList<Antique> getAllAntiques() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        String query_string = "SELECT antiques.antique_id, antiques.name, antiques.description, antiques.pic_url, " +
                "antiques.price, MAX(bids.amount) AS last_bid, antiques.store_id, stores.name AS storename FROM antiques " +
                "LEFT OUTER JOIN bids ON bids.antique_id = antiques.antique_id " +
                "INNER JOIN stores ON antiques.store_id = stores.store_id " +
                "GROUP BY antiques.antique_id";
        this.statement = this.connection.prepareStatement(query_string);
        ResultSet result = this.statement.executeQuery();
        ArrayList<Antique> prodcuts = new ArrayList();
        while ( result.next() ) {
            Antique antique = new Antique( result.getInt("antique_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getInt("store_id"), result.getString("storename") );
            prodcuts.add(antique);
        }
        this.connection.close();
        return prodcuts;
    }

    public ArrayList<Antique> getStoreAntiques(int store_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT * FROM antiques WHERE store_id = ?");
        this.statement.setInt(1, store_id );
        ResultSet result = this.statement.executeQuery();
        ArrayList<Antique> products = new ArrayList();
        while( result.next() ) {
            products.add( new Antique(result.getInt("antique_id"),result.getString("name"),result.getString("description"),result.getString("pic_url"),result.getInt("price"),store_id) );
        }
        this.connection.close();
        return products;
    }

    public String getStoreName( int store_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT name FROM stores WHERE store_id = ?");
        this.statement.setInt(1,store_id);
        String name = this.statement.executeQuery().getString("name");
        this.connection.close();
        return name;
    }

    public int getAmountOfBidsForAntique(int antique_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) AS bids FROM bids WHERE antique_id = ? ");
        this.statement.setInt(1,antique_id);
        int result = this.statement.executeQuery().getInt("bids");
        this.connection.close();
        return result;
    }

    public int getAllBidsAmount() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) AS bids FROM bids");
        int result = this.statement.executeQuery().getInt("bids");
        this.connection.close();
        return result;
    }

    public int getAmountOfStores() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) as platform_stores FROM stores");
        int result = this.statement.executeQuery().getInt("platform_stores");
        this.connection.close();
        return result;
    }

    public int getAmountOfAntiques() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) as platform_products FROM antiques");
        int result = this.statement.executeQuery().getInt("platform_products");
        this.connection.close();
        return result;
    }

    public int getHighestBidOfAntique(int antique_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT MAX(amount) as highest_bid FROM bids WHERE antique_id = ?");
        this.statement.setInt(1, antique_id);
        int result = this.statement.executeQuery().getInt("highest_bid");
        this.connection.close();
        return result;
    }

    public ArrayList<Store> getStores() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT * FROM stores");
        ResultSet result = this.statement.executeQuery();
        ArrayList<Store> stores = new ArrayList();
        while ( result.next() ) {
            stores.add( new Store( result.getInt("store_id"), result.getString("name") ) );
        }
        this.connection.close();
        return stores;
    }

    // DELETE
    public void deleteAntique( int antique_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("DELETE FROM antiques WHERE antique_id = ?");
        this.statement.setInt(1, antique_id );
        this.statement.executeUpdate();
        this.connection.close();
    }

    public void deleteStore( int store_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("DELETE FROM stores WHERE store_id = ?");
        this.statement.setInt(1,store_id);
        this.statement.executeUpdate();
        this.connection.close();
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("DELETE FROM antiques WHERE store_id = ?");
        this.statement.setInt(1,store_id);
        this.statement.executeUpdate();
        this.connection.close();
    }

    // CREATE
    public void insertStore( Store store ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("INSERT INTO stores VALUES (NULL,?)");
        this.statement.setString(1, store.getName() );
        this.statement.executeUpdate();
        this.connection.close();
    }

    public int insertAntique( Antique antique) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("INSERT INTO antiques VALUES ( NULL, ?, ?, ?, ?, ? )");
        this.statement.setString(1, antique.getName() );
        this.statement.setString(2, antique.getDescription() );
        this.statement.setString(3, antique.getPic_url() );
        this.statement.setInt(4,    antique.getPrice() );
        this.statement.setInt(5,    antique.getStore_id() );
        int result = this.statement.executeUpdate();
        this.connection.close();
        return result;
    }

    public void insertBid( int bid_amount, int antiqe_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("INSERT INTO bids VALUES (NULL,?,?)");
        this.statement.setInt(1, bid_amount);
        this.statement.setInt(2, antiqe_id );
        this.statement.executeUpdate();
        this.connection.close();
    }

    public void runQuery( String query ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement(query);
        this.statement.executeUpdate();
        this.connection.close();
    }

}
