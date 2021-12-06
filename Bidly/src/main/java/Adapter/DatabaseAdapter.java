package Adapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import Interfaces.IAntiqueRepository;
import Interfaces.IStoreRepository;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseAdapter implements IStoreRepository, IAntiqueRepository {

    String database_name;
    Connection connection;
    PreparedStatement statement;

    public DatabaseAdapter( String database_name ) {
        this.database_name = database_name;
    }

    public ArrayList<Antique> getAllAntiques() {
        ArrayList<Antique> antiques = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            String query_string = "SELECT antiques.antique_id, antiques.name, antiques.description, antiques.pic_url, " +
                    "antiques.price, MAX(bids.amount) AS last_bid, antiques.store_id, stores.name AS storename FROM antiques " +
                    "LEFT OUTER JOIN bids ON bids.antique_id = antiques.antique_id " +
                    "INNER JOIN stores ON antiques.store_id = stores.store_id " +
                    "GROUP BY antiques.antique_id";
            this.statement = this.connection.prepareStatement(query_string);
            ResultSet result = this.statement.executeQuery();
            while (result.next()) {
                Antique antique = new Antique(result.getInt("antique_id"), result.getString("name"), result.getString("description"), result.getString("pic_url"), result.getInt("price"), result.getInt("last_bid"), result.getInt("store_id"), result.getString("storename"));
                antiques.add(antique);
            }
            this.connection.close();

        } catch ( SQLException e ) {
            System.out.println( e );
        }
        return antiques;
    }

    public ArrayList<Antique> getStoreAntiques(int store_id ) {
        ArrayList<Antique> antiques = new ArrayList<>();

        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
            this.statement = this.connection.prepareStatement("SELECT * FROM antiques WHERE store_id = ?");
            this.statement.setInt(1, store_id );
            ResultSet result = this.statement.executeQuery();
            while( result.next() ) {
                antiques.add( new Antique(result.getInt("antique_id"),result.getString("name"),result.getString("description"),result.getString("pic_url"),result.getInt("price"),store_id) );
            }
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }

        return antiques;
    }

    public String getStoreName( int store_id ) {
        String name = "";
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
            this.statement = this.connection.prepareStatement("SELECT name FROM stores WHERE store_id = ?");
            this.statement.setInt(1,store_id);
            name = this.statement.executeQuery().getString("name");
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println(e);
        }
        return name;
    }

    public int getAmountOfBidsForAntique( int antique_id ) {
        int amount = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT COUNT(*) AS bids FROM bids WHERE antique_id = ? ");
            this.statement.setInt(1, antique_id);
            amount = this.statement.executeQuery().getInt("bids");
            this.connection.close();
        } catch ( SQLException e ) {
            System.out.println( e );
        }
        return amount;
    }

    public int getAllBidsAmount() {
        int amount = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT COUNT(*) AS bids FROM bids");
            amount = this.statement.executeQuery().getInt("bids");
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return amount;
    }

    public int getAmountOfStores() {
        int amount = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT COUNT(*) as platform_stores FROM stores");
            amount = this.statement.executeQuery().getInt("platform_stores");
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return amount;
    }

    public int getAmountOfAntiques() {
        int amount = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT COUNT(*) as platform_products FROM antiques");
            amount = this.statement.executeQuery().getInt("platform_products");
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return amount;
    }

    public int getHighestBidOfAntique( int antique_id ) {
        int bid = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT MAX(amount) as highest_bid FROM bids WHERE antique_id = ?");
            this.statement.setInt(1, antique_id);
            bid = this.statement.executeQuery().getInt("highest_bid");
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return bid;
    }

    public ArrayList<Store> getStores() {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("SELECT * FROM stores");
            ResultSet result = this.statement.executeQuery();
            while (result.next()) {
                stores.add(new Store(result.getInt("store_id"), result.getString("name")));
            }
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return stores;
    }

    // DELETE
    public void deleteAntique( int antique_id ) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("DELETE FROM antiques WHERE antique_id = ?");
            this.statement.setInt(1, antique_id);
            this.statement.executeUpdate();
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
    }

    public void deleteStore( int store_id ) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("DELETE FROM stores WHERE store_id = ?");
            this.statement.setInt(1, store_id);
            this.statement.executeUpdate();
            this.connection.close();
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("DELETE FROM antiques WHERE store_id = ?");
            this.statement.setInt(1, store_id);
            this.statement.executeUpdate();
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
    }

    // CREATE
    public void insertStore( Store store ) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name);
            this.statement = this.connection.prepareStatement("INSERT INTO stores VALUES (NULL,?)");
            this.statement.setString(1, store.getName());
            this.statement.executeUpdate();
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
    }

    public int insertAntique( Antique antique) {
        int inserted = 0;
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
            this.statement = this.connection.prepareStatement("INSERT INTO antiques VALUES ( NULL, ?, ?, ?, ?, ? )");
            this.statement.setString(1, antique.getName() );
            this.statement.setString(2, antique.getDescription() );
            this.statement.setString(3, antique.getPic_url() );
            this.statement.setInt(4,    antique.getPrice() );
            this.statement.setInt(5,    antique.getStore_id() );
            inserted = this.statement.executeUpdate();
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
        return inserted;
    }

    public void insertBid( int bid_amount, int antiqe_id ) {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
            this.statement = this.connection.prepareStatement("INSERT INTO bids VALUES (NULL,?,?)");
            this.statement.setInt(1, bid_amount);
            this.statement.setInt(2, antiqe_id );
            this.statement.executeUpdate();
            this.connection.close();
        }
        catch ( SQLException e ) {
            System.out.println( e );
        }
    }

    public void runQuery( String query ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database_name );
        this.statement = this.connection.prepareStatement(query);
        this.statement.executeUpdate();
        this.connection.close();
    }

}
