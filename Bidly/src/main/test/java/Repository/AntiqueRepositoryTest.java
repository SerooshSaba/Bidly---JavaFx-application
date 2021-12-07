package Repository;

import Adapter.DatabaseAdapter;
import Core.Antique;
import Core.Store;
import org.junit.jupiter.api.*;

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
    @DisplayName("Publish antique / Krav 7.1.18")
    void insert_antique() {
        //Tester insert funksjonen over//
        Assertions.assertEquals(2, antiqueRepository.getAmountOfAntiques());

    }

    @Test
    @DisplayName("Place bid on antique / Krav 7.1.13 og 7.1.15")
    void insert_bid() {
        antiqueRepository.insertBid(2000, 2);
        Assertions.assertEquals(2, antiqueRepository.getAllBidsAmount());
    }

    @Test
    @DisplayName("Get amount of bids for one antique / Krav 7.1.28")
    void get_Amount_Of_Bids_For_Antique() {
        int bids = antiqueRepository.getAmountOfBidsForAntique(1);
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Show total amount of bids made on platform / Krav 7.1.23")
    void get_All_Bids_Amount() {
        int bids = antiqueRepository.getAllBidsAmount();
        Assertions.assertEquals(1, bids);
    }

    @Test
    @DisplayName("Show highest bid for an antique / Krav 7.1.27")
    void get_Highest_Bid_Of_Antique() {
        int highestBid = antiqueRepository.getHighestBidOfAntique(1);
        Assertions.assertTrue(1000 < highestBid);
    }

    @Test
    @DisplayName("Delete Antique / Krav 7.1.20")
    void delete_Antique() {
        Antique antique = antiqueRepository.getAllAntiques().get(0);
        antiqueRepository.deleteAntique(antique.getAntique_id());
        Assertions.assertEquals(1, antiqueRepository.getAllAntiques().size());
    }

    @Test
    @DisplayName("Show total amount of antiques on platform / Krav 7.1.23")
    void get_Amount_Of_Antiques() {
        ArrayList<Antique> antiques = antiqueRepository.getAllAntiques();
        Assertions.assertEquals(2, antiques.size());
    }


    @Test
    @DisplayName("Show all antiques on platform / Krav 7.1.24")
    void get_All_Antiques() {
        ArrayList<Antique> store_antiques = storeRepository.getStoreAntiques(1);
        ArrayList<Antique> ret_antiques = antiqueRepository.getAllAntiques();
        Assertions.assertEquals(store_antiques.toString(),ret_antiques.toString());
        System.out.println(store_antiques);
        System.out.println(ret_antiques);
    }
}
