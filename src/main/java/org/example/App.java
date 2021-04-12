package org.example;

import org.example.Connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Salut!" );
        Connection connection = ConnectionFactory.getConnection();
    }
}
