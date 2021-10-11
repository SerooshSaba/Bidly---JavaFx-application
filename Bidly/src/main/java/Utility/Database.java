package Utility;

import java.sql.*;

public class Database {

    Connection connection;
    PreparedStatement statement;
    int param_i;

    public void statement( String query ) throws SQLException {
        //this.connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/seroo/Desktop/SoftwareGit/SoftwareGruppe5/Bidly/database.sqlite");
        this.connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
        this.statement = this.connection.prepareStatement( query );
        this.param_i = 1;
    }

    public void passString( String str ) throws SQLException {
        this.statement.setString( this.param_i, str );
        this.param_i++;
    }

    public void passInt( int integer ) throws SQLException {
        this.statement.setInt( this.param_i, integer );
        this.param_i++;
    }

    public boolean execute() throws SQLException {
        if ( this.statement.executeUpdate() == 1 ) {
            this.connection.close();
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
