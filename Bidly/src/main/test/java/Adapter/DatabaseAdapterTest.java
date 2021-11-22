package Adapter;

import BidlyCore.Antique;
import BidlyCore.Store;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.sql.SQLException;


class DatabaseAdapterTest {

    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @BeforeEach
    void insertStore() throws SQLException {
        databaseAdapter.insertStore(new Store(1, "store name"));
        databaseAdapter.insertStore(new Store(2, "store r us"));
    }

    @BeforeEach
    void insertAntique() throws SQLException {
        databaseAdapter.insertAntique(new Antique("Object", "text", "url", 1000, 1));
        databaseAdapter.insertAntique(new Antique("Table", "text", "url", 2000, 1));
    }

    @BeforeEach
    void insertBid() throws SQLException {
        databaseAdapter.insertBid(1001, 1);
    }

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM Antiques;DELETE FROM bids;DELETE FROM stores;");
        databaseAdapter.runQuery("DELETE FROM bids");
        databaseAdapter.runQuery("DELETE FROM STORES");

    }

    @Test
    @DisplayName("Insert store test")
    void insert_store() throws SQLException {
        Assertions.assertEquals(2, databaseAdapter.getAmountOfStores());
    }
    @Test
    @DisplayName("Insert antique test")
    void insert_antique() throws SQLException {
        Assertions.assertEquals(2, databaseAdapter.getAmountOfProducts());
    }
    @Test
    @DisplayName("Insert bid test")
    void insert_bid() throws SQLException {
        Assertions.assertEquals(1, databaseAdapter.getAllBidsAmount());
    }

    @Test
    @DisplayName("Get all products on platform test")
    void get_All_Products() throws SQLException {
        Antique returned_antique = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object", returned_antique.getName());
        Assertions.assertEquals("text", returned_antique.getDescription());
        Assertions.assertEquals("url", returned_antique.getPic_url());
        Assertions.assertEquals(1000, returned_antique.getPrice());
        Assertions.assertEquals(1, returned_antique.getStore_id());
        
    }

    @Test
    @DisplayName("Get the amount of bid for one antique test")
    void get_Amount_Of_Bids_For_Antique() throws SQLException {
        int bids = databaseAdapter.getAmountOfBidsForAntique(1);
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Get total amount of bids on platform test")
    void get_All_Bids_Amount() throws SQLException {
        int bids = databaseAdapter.getAllBidsAmount();
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Get highest bid of an antique test")
    void get_Highest_Bid_Of_Antique() throws SQLException {
        int highestBid = databaseAdapter.getHighestBidOfAntique(1);
        Assertions.assertTrue(1000 < highestBid);
    }

    @Test
    @DisplayName("Get Store Name test")
    void get_Store_Name() throws SQLException {
        String store = databaseAdapter.getStoreName(1);
        Assertions.assertEquals("store name", store);

    }

    @Test
    @DisplayName("Get all stores test")
    void get_Stores() throws SQLException {
        ArrayList<Store> stores = databaseAdapter.getStores();
        Assertions.assertTrue(stores.contains(stores.get(1)));
        Assertions.assertEquals(2, stores.size());
    }

    @Test
    @DisplayName("Get all antiques from one store test")
    void get_Store_Products() throws SQLException {
        Antique returned_antique = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object", returned_antique.getName());
        Assertions.assertEquals("text", returned_antique.getDescription());
        Assertions.assertEquals("url", returned_antique.getPic_url());
        Assertions.assertEquals(1000, returned_antique.getPrice());
        Assertions.assertEquals(1, returned_antique.getStore_id());
        Assertions.assertEquals(2, databaseAdapter.getStoreProducts(1).size());
    }

    @Test
    @DisplayName("Delete Antique test")
    void delete_Antique() throws SQLException {
        Antique antique = databaseAdapter.getAllProducts().get(0);
        String id = String.valueOf(antique.getAntique_id());
        databaseAdapter.deleteAntique(id);
        Assertions.assertEquals(1, databaseAdapter.getAllProducts().size());
    }

    @Test
    @DisplayName("Delete Store test")
    void delete_Store() throws SQLException {
        Store store = databaseAdapter.getStores().get(0);
        int id = store.getStore_id();
        databaseAdapter.deleteStore(id);
        Assertions.assertEquals(1, databaseAdapter.getStores().size());
        Assertions.assertEquals(0, databaseAdapter.getStoreProducts(id).size());
    }

    @Test
    @DisplayName("Get number of all stores on platform test")
    void get_Amount_Of_Stores () throws SQLException {
        ArrayList<Store> stores = databaseAdapter.getStores();
        Assertions.assertEquals(2, stores.size());
    }

    @Test
    @DisplayName("Get number of antiques on platform test")
    void get_Amount_Of_Products() throws SQLException {
        ArrayList<Antique> antiques = databaseAdapter.getAllProducts();
        Assertions.assertEquals(2, antiques.size());
    }
}

