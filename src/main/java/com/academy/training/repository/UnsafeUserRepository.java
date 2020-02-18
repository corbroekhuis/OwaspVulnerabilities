package com.academy.training.repository;

import com.academy.training.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UnsafeUserRepository{

    @Value(value="${spring.datasource.url}")
    String url;

    @Value(value="${spring.datasource.driverClassName}")
    String driver;

    @Value(value="${spring.datasource.username}")
    String user;

    @Value(value="${spring.datasource.password}")
    String password;

    public UnsafeUserRepository(){

        try {
            Driver mssqlDriver = (Driver)Class.forName(driver)
                    .getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(mssqlDriver);

        } catch (Exception e) {
            // Never do this....
        }

    }

    public User findUserByUserAndPasswordNamedParams( String user, String password){

        User authenticatedUser = null;

        String query = "SELECT * FROM User WHERE user = '" + user + "' and password = '" + password + "'";
        System.out.println("Query: " + query);

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if(rs.next()) {

                authenticatedUser = new User();
                authenticatedUser.setId(rs.getLong("id"));
                authenticatedUser.setUser(rs.getString("user"));
                authenticatedUser.setPassword(rs.getString("password"));
                authenticatedUser.setBsn(rs.getString("bsn"));

            }

        } catch (SQLException ex) {
           // No, not again....
        }

        return authenticatedUser;

    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
