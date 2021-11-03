package Adapter;

import com.bidly.Core.Model.Antiqe;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseAdapterTest {

    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM Antiqes;DELETE FROM bids;DELETE FROM stores;");
    }

    @Test
    void test_insertAntique_and_getAllProducts() throws SQLException {
        databaseAdapter.insertAntiqe(new Antiqe("Object", "text", "url", 1000, 1 ));
        Antiqe returned_antiqe = databaseAdapter.getStoreProducts(1).get(0);
        Assertions.assertEquals("Object",   returned_antiqe.getName());
        Assertions.assertEquals("text",     returned_antiqe.getDescription());
        Assertions.assertEquals("url",      returned_antiqe.getPic_url());
        Assertions.assertEquals(1000,       returned_antiqe.getPrice());
        Assertions.assertEquals(1,          returned_antiqe.getStore_id() );
    }

}