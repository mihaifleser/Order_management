package org.example.BusinessLogic;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mysql.cj.util.StringUtils;
import org.example.DataAccess.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Mihai Fleser
 * This class uses reflection tehniques to generate specific queries for accessing the DB.
 * @param <T>
 */

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(Class<T> type) {
        this.type = type;

    }

    /**
     * Creates a basic select query depending on the field given.
     * @param field - the parameter used in the Where clause
     * @return  - returns the query generated
     */

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Selects all the rows from a table.
     * @return - the arrayList of objects created from the rows returned by the query
     */

    public ArrayList<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Select * from " + type.getSimpleName();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Finds by id a row in a table from the database.
     * @param id
     * @return  the object made with that returned row
     */

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Makes an arrayList of objects based on a resultSet from a query.
     * @param resultSet
     * @return the ArrayList generated
     */

    private ArrayList<T> createObjects(ResultSet resultSet) {
        ArrayList<T> list = new ArrayList<T>();
        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        if(list.size() == 0)
            list.add(null);
        return list;
    }

    /**
     * Inserts into a table from the database a new object.
     * @param t
     */

    public void insert(T t) {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "Insert into " + type.getSimpleName() + "(";

        for(Field field: t.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if(value != null)
                {
                    query = query + field.getName() + ", ";
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query = query.substring(0,query.length() - 2);
        query = query + ") VALUES (";


        for (Field field : t.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if(value != null)
                    query = query + "'" + value + "', ";

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query = query.substring(0,query.length() - 2);
        query = query + ")";
        System.out.println(query);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Deletes an element from a table depending on its id.
     * @param id
     */

    public void deleteById(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "DELETE FROM " + type.getSimpleName() + " Where id = '" + id + "'";
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Updates an element from a table in the database.
     * @param t
     */

    public void update(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "UPDATE " + type.getSimpleName() + " SET ";
        Object id = null;

        boolean first = false;
        for(Field field: t.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            try {
                Object value = field.get(t);
                if(!first)
                {
                    first = true;
                    id = value;
                }
                query = query + field.getName() + " ='" + value + "', ";

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        query = query.substring(0,query.length() - 2);
        query = query + " WHERE id = " + id;
        System.out.println(query);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.execute();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
