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
        Antique antiqe = new Antique(1, "text", "text", "url", 100, 100, 1, "name"  );
        Assertions.assertEquals(antiqe.getAntiqe_id(), 1);
        Assertions.assertEquals(antiqe.getName(), "text");
        Assertions.assertEquals(antiqe.getDescription(), "text");
        Assertions.assertEquals(antiqe.getPic_url(), "url");
        Assertions.assertEquals(antiqe.getPrice(), 100);
        Assertions.assertEquals(antiqe.getLast_bid_price(), 100);
        Assertions.assertEquals(antiqe.getStore_id(), 1);
        Assertions.assertEquals(antiqe.getStoreName(), "name");
    }

}
