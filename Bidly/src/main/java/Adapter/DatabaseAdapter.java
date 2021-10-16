package Adapter;

import java.sql.*;

// Adapter class
// Any database interface <-> This database adapter <-> Application ports
public class DatabaseAdapter {

    Connection connection;
    PreparedStatement statement;
    int incrementor;

    public void statement( String query ) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        this.statement = this.connection.prepareStatement( query );
        this.incrementor = 1;
    }

    public void passString( String str ) throws SQLException {
        this.statement.setString( this.incrementor, str );
        this.incrementor++;
    }

    public void passInt( int integer ) throws SQLException {
        this.statement.setInt( this.incrementor, integer );
        this.incrementor++;
    }

    public boolean execute() throws SQLException {
        if ( this.statement.executeUpdate() == 1 ) {
            return true;
        }
        return false;
    }

    public ResultSet getResult() throws SQLException {
        return this.statement.executeQuery();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

}
