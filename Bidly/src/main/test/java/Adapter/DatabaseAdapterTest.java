package Adapter;

import BidlyCore.Antique;
import BidlyCore.Store;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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
    void test_getAllProducts() throws SQLException {
        Antique returned_antique = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object", returned_antique.getName());
        Assertions.assertEquals("text", returned_antique.getDescription());
        Assertions.assertEquals("url", returned_antique.getPic_url());
        Assertions.assertEquals(1000, returned_antique.getPrice());
        Assertions.assertEquals(1, returned_antique.getStore_id());
    }

    @Test
    void test_getAmountOfBidsForAntique() throws SQLException {
        int bids = databaseAdapter.getAmountOfBidsForAntique(1);
        Assertions.assertEquals(1, bids);
    }

    @Test
    void test_getAllBidsAmount() throws SQLException {
        int bids = databaseAdapter.getAllBidsAmount();
        Assertions.assertEquals(1, bids);
    }

    @Test
    void test_getHighestBidOfAntique() throws SQLException {
        int highestBid = databaseAdapter.getHighestBidOfAntique(1);
        Assertions.assertTrue(1000 < highestBid);
    }

    @Test
    void test_getStoreName() throws SQLException {
        String store = databaseAdapter.getStoreName(1);
        Assertions.assertEquals("store name", store);

    }

    @Test
    void test_getStores() throws SQLException {
        ArrayList<Store> stores = databaseAdapter.getStores();
        Assertions.assertTrue(stores.contains(stores.get(1)));
        Assertions.assertEquals(2, stores.size());
    }

    @Test
    void test_getStoreProducts() throws SQLException {
        Antique returned_antique = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object", returned_antique.getName());
        Assertions.assertEquals("text", returned_antique.getDescription());
        Assertions.assertEquals("url", returned_antique.getPic_url());
        Assertions.assertEquals(1000, returned_antique.getPrice());
        Assertions.assertEquals(1, returned_antique.getStore_id());
        Assertions.assertEquals(2, databaseAdapter.getStoreProducts(1).size());
    }

    @Test
    void test_deleteAntique() throws SQLException {
        Antique antique = databaseAdapter.getAllProducts().get(0);
        String id = String.valueOf(antique.getAntique_id());
        databaseAdapter.deleteAntique(id);
        Assertions.assertEquals(1, databaseAdapter.getAllProducts().size());
    }

    @Test
    void deleteStore() throws SQLException {
        Store store = databaseAdapter.getStores().get(0);
        int id = store.getStore_id();
        databaseAdapter.deleteStore(id);
        Assertions.assertEquals(1, databaseAdapter.getStores().size());
    }

    @Test
    void test_getAmountOfStores () throws SQLException {
        ArrayList<Store> stores = databaseAdapter.getStores();
        Assertions.assertEquals(2, stores.size());
    }

    @Test
    void test_getAmountOfProducts() throws SQLException {
        ArrayList<Antique> antiques = databaseAdapter.getAllProducts();
        Assertions.assertEquals(2, antiques.size());

    }
}

