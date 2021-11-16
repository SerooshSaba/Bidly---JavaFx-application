package BidlyCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class BidTest {
    Bid bid = new Bid(1000);
    Bid bid2 = new Bid(2000);

    @Test
    void is_Higher() {
        Assertions.assertTrue(bid2.isHigher(bid));
    }

    @Test
    void is_Lower() {
        Assertions.assertTrue(bid.isLower(bid2));
    }
}
