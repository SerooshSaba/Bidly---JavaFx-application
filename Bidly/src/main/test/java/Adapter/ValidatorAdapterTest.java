package Adapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorAdapterTest {

    ValidatorAdapter validatorAdapter = new ValidatorAdapter();

    @Test
    void test_stringsEmpty_with_empty_strings() {
        String[] empty_strings = new String[]{ "", "", "" };
        assertTrue(validatorAdapter.stringsEmpty(empty_strings));
    }

    @Test
    void test_stringsEmpty_with_strings() {
        String[] empty_strings = new String[]{ "sdasd", "21dwdw", " sd sdas d" };
        assertFalse(validatorAdapter.stringsEmpty(empty_strings));
    }

    @ParameterizedTest
    @ValueSource(strings = { "1", "12", "123" })
    void test_containsNumber_with_numbers( String str ) {
        assertTrue(validatorAdapter.containsNumber(str));
    }

    @ParameterizedTest
    @ValueSource(strings = { "this", "is", "just strings_23123" })
    void test_containsNumber_with_no_numbers( String str ) {
        assertFalse(validatorAdapter.containsNumber(str));
    }

}