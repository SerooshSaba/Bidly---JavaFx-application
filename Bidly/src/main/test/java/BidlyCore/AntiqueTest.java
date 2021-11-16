package BidlyCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AntiqueTest {

    @Test
    void antique_table_initial_value() {
        Antique antique = new Antique("Object", "text", "url", 1000, 1 );
        Assertions.assertEquals(0, antique.getAntiqe_id());
    }

    @Test
    void antique_table_fill_value() {
        Antique antique = new Antique(1, "text", "text", "url", 100, 100, 1, "name"  );
        Assertions.assertEquals(antique.getAntiqe_id(), 1);
        Assertions.assertEquals(antique.getName(), "text");
        Assertions.assertEquals(antique.getDescription(), "text");
        Assertions.assertEquals(antique.getPic_url(), "url");
        Assertions.assertEquals(antique.getPrice(), 100);
        Assertions.assertEquals(antique.getLast_bid_price(), 100);
        Assertions.assertEquals(antique.getStore_id(), 1);
        Assertions.assertEquals(antique.getStoreName(), "name");
    }

}
