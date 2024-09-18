package SQLServer;

import Model.Users;
import com.microsoft.sqlserver.jdbc.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import javax.swing.*;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.regex.Pattern;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

public class SQLs {
    static HikariDataSource ds = null;
    public static void CreateConnection(){
        String url = "jdbc:sqlserver://DESKTOP-LK6HI9G\\SQLEXPRESS;encrypt=true;trustServerCertificate=true;databaseName=UserDB;";
        String username = "sa";
        String password = "Naruto1014";
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            ds = new HikariDataSource(config);
            var connection = ds.getConnection();
            if(connection==null)
                throw new SQLException("Cannot Access SQLDatabase");
            else {
                System.out.print("Connection has been established");

                var query = connection.createStatement().executeUpdate("IF NOT EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='Users')\n".concat( "CREATE TABLE Users(ID INT IDENTITY(1,1) PRIMARY KEY, Name varchar(255) NOT NULL, Surname varchar(255) NOT NULL,Email varchar(255) NOT NULL,Number REAL NOT NULL)")
                       );
                connection.close();
            }
        }
        catch(SQLException ex){
            System.out.print(STR."\nSQLException:\{ex.getMessage()}");
        }
        catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }
    public static void Insert(Users obj){
        try(var connection = ds.getConnection()){
            if(connection !=null){
                System.out.print("Connection's for Creating has been established");
                var checker = connection.prepareStatement("SELECT * FROM Users WHERE Name = ? OR Surname=? OR Email=? OR Number=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                checker.setString(1, obj.getName());
                checker.setString(2, obj.getSurName());
                checker.setString(3, obj.getEmail());
                checker.setFloat(4, obj.getPhoneNumber());
                ResultSet check = checker.executeQuery();
                if(check.first())throw new SQLException("There's already a recordset with at least one of the input values");
                var adder = connection.prepareStatement("INSERT INTO Users(Name,Surname,Email,Number) VALUES (?,?,?,?)",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
                adder.setString(1, obj.getName());
                adder.setString(2, obj.getSurName());
                var isMailOk=checkMail(obj.getEmail());
                if(!isMailOk){
                    throw new Error("\n\nEmail doesn't match");
                }
                adder.setString(3, obj.getEmail());
                adder.setFloat(4, obj.getPhoneNumber());
                adder.executeUpdate();
            }
            else throw new SQLException("\n Connection's not been established at Insert operation");
        }
        catch(SQLException ex){
            System.out.print(STR."InsertOperation:\{ex.getMessage()}");
        }

    }
    public static boolean checkMail(String mail){
        boolean bool = false;
        Pattern mailRegEx = Pattern.compile("^[-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",Pattern.CASE_INSENSITIVE);
        var checker = mailRegEx.matcher(mail);
        if(checker.matches())bool=true;
        return bool;
    }
}
