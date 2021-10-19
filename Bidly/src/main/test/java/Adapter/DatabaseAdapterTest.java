package Adapter;

import com.bidly.Core.Model.Antiqe;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseAdapterTest {

    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @Test
    void test_insertAntiqe_and_getAllProducts() throws SQLException {
        databaseAdapter.insertAntiqe(new String[]{ "Object", "text", "url", "1000", "1" });
        Antiqe antiqe = databaseAdapter.getStoreProducts(1).get(0);
        databaseAdapter.deleteAntiqe("1");
        Assertions.assertTrue(antiqe.getName().equals("Object") && antiqe.getDescription().equals("text") && antiqe.getPic_url().equals("url") && antiqe.getPrice() == 1000 && antiqe.getStore_id() == 1 );
    }

}