package BidlyCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AntiqueTest {

    @Test
    @DisplayName("Checks the antique table for the initial value")
    void antique_table_initial_value() {
        Antique antique = new Antique("Object", "text", "url", 1000, 1 );
        Assertions.assertEquals(0, antique.getAntique_id());
    }

    @Test
    @DisplayName("Fill antique table test")
    void antique_table_fill_value() {
        Antique antique = new Antique(1, "name", "text", "url", 100, 110, 1, "store name"  );
        Assertions.assertEquals(1, antique.getAntique_id());
        Assertions.assertEquals("name", antique.getName());
        Assertions.assertEquals("text", antique.getDescription());
        Assertions.assertEquals("url", antique.getPic_url());
        Assertions.assertEquals(100, antique.getPrice());
        Assertions.assertEquals(110, antique.getLast_bid_price());
        Assertions.assertEquals(1, antique.getStore_id());
        Assertions.assertEquals("store name", antique.getStoreName());
    }

}
