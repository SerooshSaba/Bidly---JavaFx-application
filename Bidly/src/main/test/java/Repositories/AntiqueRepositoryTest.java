package Repositories;

import Adapter.DatabaseAdapter;
import BidlyCore.Antique;
import BidlyCore.Store;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLException;
import java.util.ArrayList;

public class AntiqueRepositoryTest {

    public StoreRepository storeRepository = new StoreRepository("testing.sqlite");
    public AntiqueRepository antiqueRepository = new AntiqueRepository("testing.sqlite");
    public DatabaseAdapter databaseAdapter = new DatabaseAdapter("testing.sqlite");

    @BeforeEach
    void insertStoresAntiques() {
        storeRepository.insertStore(new Store(1, "Antiques r us"));
        storeRepository.insertStore(new Store(2, "Hobby"));
        antiqueRepository.insertAntique(new Antique(1,"Clock", "old clock", "url", 1000, 1));
        antiqueRepository.insertAntique(new Antique(2, "Table", "old table", "url", 2000, 1));
    }

    @BeforeEach
    void insertBid() {
        antiqueRepository.insertBid(1001, 1);
    }

    @AfterEach
    void clearTables() throws SQLException {
        databaseAdapter.runQuery("DELETE FROM antiques");
        databaseAdapter.runQuery("DELETE FROM bids");
        databaseAdapter.runQuery("DELETE FROM stores");
    }

    @Test
    @DisplayName("Publish antique")
    void insert_antique() {
        //Tester insert funksjonen over//
        Assertions.assertEquals(2, antiqueRepository.getAmountOfAntiques());

    }

    @Test
    @DisplayName("Place bid on antique")
    void insert_bid() {
        Assertions.assertEquals(1, antiqueRepository.getAllBidsAmount());
    }

    @Test
    @DisplayName("Get amount of bids for one antique")
    void get_Amount_Of_Bids_For_Antique() {
        int bids = antiqueRepository.getAmountOfBidsForAntique(1);
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Show total amount of bids made on platform")
    void get_All_Bids_Amount() {
        int bids = antiqueRepository.getAllBidsAmount();
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Show highest bid for an antique")
    void get_Highest_Bid_Of_Antique() {
        int highestBid = antiqueRepository.getHighestBidOfAntique(1);
        Assertions.assertTrue(1000 < highestBid);
    }

    @Test
    @DisplayName("Delete Antique")
    void delete_Antique() {
        Antique antique = antiqueRepository.getAllAntiques().get(0);
        antiqueRepository.deleteAntique(antique.getAntique_id());
        Assertions.assertEquals(1, antiqueRepository.getAllAntiques().size());
    }

    @Test
    @DisplayName("Show total amount of antiques on platform")
    void get_Amount_Of_Antiques() {
        ArrayList<Antique> antiques = antiqueRepository.getAllAntiques();
        Assertions.assertEquals(2, antiques.size());
    }


    @Test
    @DisplayName("Show all antiques from store")
    void get_All_Antiques() {
        ArrayList<Antique> store_antiques = storeRepository.getStoreAntiques(1);
        ArrayList<Antique> ret_antiques = antiqueRepository.getAllAntiques();
        Assertions.assertEquals(store_antiques.toString(),ret_antiques.toString());
        System.out.println(store_antiques);
        System.out.println(ret_antiques);
    }
}
