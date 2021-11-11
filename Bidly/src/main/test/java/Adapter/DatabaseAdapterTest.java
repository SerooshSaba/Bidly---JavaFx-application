package Adapter;

import BidlyCore.Antique;
import org.junit.jupiter.api.*;
import java.sql.SQLException;

class DatabaseAdapterTest {

    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM Antiques;DELETE FROM bids;DELETE FROM stores;");
    }

    @Test
    void test_insertAntique_and_getAllProducts() throws SQLException {
        databaseAdapter.insertAntiqe(new Antique("Object", "text", "url", 1000, 1 ));
        Antique returned_antique = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object",   returned_antique.getName());
        Assertions.assertEquals("text",     returned_antique.getDescription());
        Assertions.assertEquals("url",      returned_antique.getPic_url());
        Assertions.assertEquals(1000,       returned_antique.getPrice());
        Assertions.assertEquals(1,          returned_antique.getStore_id() );
    }

}