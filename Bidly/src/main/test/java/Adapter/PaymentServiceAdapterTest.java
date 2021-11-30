package Adapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceAdapterTest {

    PaymentServiceAdapter paymentServiceAdapter = new PaymentServiceAdapter();

    @Test
    @DisplayName("Correct payment info test")
    void test_processPayment_with_correct_info() {
        assertTrue( paymentServiceAdapter.processPayment("1234 1234 1234 1234","01/09","321") );
    }

    @Test
    @DisplayName("Incorrect payment info test")
    void test_processPayment_with_incorrect_info() {
        assertFalse( paymentServiceAdapter.processPayment("4233 1554 2654 2345","02/12","897") );
    }

}
