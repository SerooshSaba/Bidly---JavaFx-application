package Adapter;

import BidlyCore.Antiqe;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import java.util.ArrayList;

class DatabaseAdapterTest {

    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @BeforeEach
    void insertAntiqe() throws SQLException {
        databaseAdapter.insertAntiqe(new Antiqe("Object", "text", "url", 1000, 1 ));
        databaseAdapter.insertBid(1001, 1);
    }

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM Antiqes;DELETE FROM bids;DELETE FROM stores;");
        databaseAdapter.runQuery("DELETE FROM bids");

    }

    @Test
    void test_getAllProducts() throws SQLException {
        Antiqe returned_antiqe = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object",   returned_antiqe.getName());
        Assertions.assertEquals("text",     returned_antiqe.getDescription());
        Assertions.assertEquals("url",      returned_antiqe.getPic_url());
        Assertions.assertEquals(1000,       returned_antiqe.getPrice());
        Assertions.assertEquals(1,          returned_antiqe.getStore_id());
    }

    @Test
    void test_getAmountOfBidsForAntiqe() throws SQLException {
        int bids = databaseAdapter.getAmountOfBidsForAntiqe(1);
        Assertions.assertEquals(1, bids);
    }

    @Test
    void test_getAllBidsAmount() throws SQLException {
        int bids = databaseAdapter.getAllBidsAmount();
        Assertions.assertEquals(1, bids);
    }

    @Test
    void test_getHighestBidOfAntiqe() throws SQLException {
        int highestBid = databaseAdapter.getHighestBidOfAntiqe(1);
        Assertions.assertTrue(1000<highestBid);
    }





}