package BidlyCore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BidTest {

    Bid bid = new Bid(1000);
    Bid bid2 = new Bid(2000);

    @Test
    @DisplayName("Checks if bid are higher test")
    void is_Higher() {
        Assertions.assertTrue(bid2.isHigher(bid));
    }

    @Test
    @DisplayName("Checks if bid are lower test")
    void is_Lower() {
        Assertions.assertTrue(bid.isLower(bid2));
    }
}
