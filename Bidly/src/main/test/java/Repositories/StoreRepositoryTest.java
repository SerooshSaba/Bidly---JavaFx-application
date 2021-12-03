package Repositories;

import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class StoreRepositoryTest {

    public StoreRepository storeRepository = new StoreRepository("testing.sqlite");
    public AntiqueRepository antiqueRepository = new AntiqueRepository("testing.sqlite");
    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @BeforeEach
    void insertStoresAntiques() {
        storeRepository.insertStore(new Store(1, "store name"));
        storeRepository.insertStore(new Store(2, "store r us"));
        antiqueRepository.insertAntique(new Antique("Clock", "text", "url", 1000, 1));
        antiqueRepository.insertAntique(new Antique("Table", "text", "url", 2000, 1));
    }

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM antiques");
        databaseAdapter.runQuery("DELETE FROM stores");
    }

    @Test
    @DisplayName("Add store to platform")
    void insert_store() {
        Assertions.assertEquals(2, storeRepository.getAmountOfStores());
    }

    @Test
    @DisplayName("Display store name")
    void get_Store_Name() {
        String store = storeRepository.getStoreName(1);
        Assertions.assertEquals("store name", store);
    }

    @Test
    @DisplayName("Show all stores")
    void get_Stores() {
        ArrayList<Store> stores = storeRepository.getStores();
        Assertions.assertTrue(stores.contains(stores.get(1)));
        Assertions.assertEquals(2, stores.size());
    }

    @Test
    @DisplayName("Get all antiques from a store")
    void get_Store_Antiques() {
        Antique returned_antique = storeRepository.getStoreAntiques(1).get(0);
        Assertions.assertEquals("Clock", returned_antique.getName());
        Assertions.assertEquals("text", returned_antique.getDescription());
        Assertions.assertEquals("url", returned_antique.getPic_url());
        Assertions.assertEquals(1000, returned_antique.getPrice());
        Assertions.assertEquals(1, returned_antique.getStore_id());
        Assertions.assertEquals(2, storeRepository.getStoreAntiques(1).size());
    }

    @Test
    @DisplayName("Delete a store")
    void delete_Store() {
        Store store = storeRepository.getStores().get(0);
        int id = store.getStore_id();
        storeRepository.deleteStore(id);
        Assertions.assertEquals(1, storeRepository.getStores().size());
        Assertions.assertEquals(0, storeRepository.getStoreAntiques(id).size());
    }

    @Test
    @DisplayName("Show number of stores on platform")
    void get_Amount_Of_Stores () {
        ArrayList<Store> stores = storeRepository.getStores();
        Assertions.assertEquals(2, stores.size());
    }
}
