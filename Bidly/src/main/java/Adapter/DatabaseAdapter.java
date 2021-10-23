package Adapter;
import com.bidly.Core.Model.Antiqe;
import com.bidly.Core.Model.Auctioneer;

import java.sql.*;
import java.util.ArrayList;

// Adapter class
// Application ports <-> This database adapter <-> Any database interface
public class DatabaseAdapter {

    String database_name;
    Connection connection;
    PreparedStatement statement;

    public DatabaseAdapter( String database_name ) {
        this.database_name = database_name;
    }

    public ArrayList<Antiqe> getAllProducts() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        String query_string = "SELECT antiqes.antiqe_id, antiqes.name, antiqes.description, antiqes.pic_url, " +
                "antiqes.price, MAX(bids.amount) AS last_bid, antiqes.store_id, stores.name AS storename FROM antiqes " +
                "LEFT OUTER JOIN bids ON bids.antiqe_id = antiqes.antiqe_id " +
                "INNER JOIN stores ON antiqes.store_id = stores.store_id " +
                "GROUP BY antiqes.antiqe_id";
        this.statement = this.connection.prepareStatement(query_string);
        ResultSet result = this.statement.executeQuery();
        ArrayList<Antiqe> prodcuts = new ArrayList();
        while ( result.next() ) {
            Antiqe antiqe = new Antiqe( result.getInt("antiqe_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getInt("store_id"), result.getString("storename") );
            prodcuts.add(antiqe);
        }
        this.connection.close();
        return prodcuts;
    }

    public ArrayList<Antiqe> getStoreProducts( int store_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT * FROM antiqes WHERE store_id = ?");
        this.statement.setInt(1, store_id );
        ResultSet result = this.statement.executeQuery();
        ArrayList<Antiqe> products = new ArrayList();
        while( result.next() ) {
            products.add( new Antiqe(result.getInt("antiqe_id"),result.getString("name"),result.getString("description"),result.getString("pic_url"),result.getInt("price"),store_id) );
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

    public int getAmountOfBidsForAntiqe(int antiqe_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) AS bids FROM bids WHERE antiqe_id = ? ");
        this.statement.setInt(1,antiqe_id);
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

    public int getAmountOfProducts() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT COUNT(*) as platform_products FROM antiqes");
        int result = this.statement.executeQuery().getInt("platform_products");
        this.connection.close();
        return result;
    }

    public int getHighestBidOfAntiqe( int antiqe_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT MAX(amount) as highest_bid FROM bids WHERE antiqe_id = ?");
        this.statement.setInt(1, antiqe_id);
        int result = this.statement.executeQuery().getInt("highest_bid");
        this.connection.close();
        return result;
    }

    public ArrayList<Auctioneer> getStores() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("SELECT * FROM stores");
        ResultSet result = this.statement.executeQuery();
        ArrayList<Auctioneer> auctioneers = new ArrayList();
        while ( result.next() ) {
            auctioneers.add( new Auctioneer( result.getInt("store_id"), result.getString("name") ) );
        }
        this.connection.close();
        return auctioneers;
    }

    public void deleteAntiqe( String antiqe_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("DELETE FROM antiqes WHERE antiqe_id = ?");
        this.statement.setInt(1,Integer.parseInt(antiqe_id));
        this.statement.executeUpdate();
        this.connection.close();
    }

    public void deleteStore( int store_id ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("DELETE FROM stores WHERE store_id = ?");
        this.statement.setInt(1,store_id);
        this.statement.executeUpdate();
        this.connection.close();
    }

    public int insertAntiqe( Antiqe antiqe ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement("INSERT INTO antiqes VALUES ( NULL, ?, ?, ?, ?, ? )");
        this.statement.setString(1, antiqe.getName() );
        this.statement.setString(2, antiqe.getDescription() );
        this.statement.setString(3, antiqe.getPic_url() );
        this.statement.setInt(4,    antiqe.getPrice() );
        this.statement.setInt(5,    antiqe.getStore_id() );
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
