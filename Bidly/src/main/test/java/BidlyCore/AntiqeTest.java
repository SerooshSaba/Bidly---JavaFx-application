package BidlyCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AntiqeTest {

    @Test
    void antique_table_initial_value() {
        Antiqe antiqe = new Antiqe("Object", "text", "url", 1000, 1 );
        Assertions.assertEquals(0, antiqe.getAntiqe_id());
    }
}
