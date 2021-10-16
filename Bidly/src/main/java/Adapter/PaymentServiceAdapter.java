package Adapter;

// Mock adapter
public class PaymentServiceAdapter {

    public boolean processPayment( String card_number, String expiration, String cvv ) {
        if ( card_number.equals("1234 1234 1234 1234") && expiration.equals("01/09") && cvv.equals("321") )
            return true;
        return false;
    }

}
