package Adapter;
import javafx.scene.image.Image;

public class ValidatorAdapter {

    // If string is empty, return true
    public boolean stringEmpty( String str ) {
        if ( str.length() == 0 )
            return true;
        return false;
    }

    // If one string in array is empty, return true
    public boolean stringsEmpty( String[] strings ) {
        boolean isEmpty = false;
        for ( String string : strings ) {
            if ( string.equals("") ) {
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }

    // Is a string can be converted to an integer, return true
    public boolean containsNumber( String string ) {
        boolean isNumber = false;
        try {
            String remove_whitespace = string.replaceAll("\\s+", "");
            Integer.parseInt(remove_whitespace);
            isNumber = true;
        } catch (NumberFormatException exception){
            System.out.println( "Could not convert string to integer." );
        }
        return isNumber;
    }

    // If image url is valid, return true
    public boolean isUrl( String url ) {
        boolean isValid = false;
        try {
            Image img = new Image(url);
            isValid = true;
            System.out.println("Image url is valid");
        } catch (Exception exception) {
            System.out.println("Image url is not valid.");
        }
        return isValid;
    }

}