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

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(Class<T> type) {
        this.type = type;

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

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

    public void refreshTable(JTable table)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<T> elements = findAll();

        model.setRowCount(0);
        for(T element: elements)
        {
            LinkedList<Object> data = new LinkedList<>();
            for(Field field: type.getDeclaredFields())
            {
                field.setAccessible(true);
                try {
                    Object value = field.get(element);
                    data.add(value.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(data.toArray());
        }

    }

    public void setTable(JTable table)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<T> elements = findAll();
        for (Field field : type.getDeclaredFields())
        {
            model.addColumn(field.getName());
        }

        for(T element: elements)
        {
            LinkedList<Object> data = new LinkedList<>();
            for(Field field: type.getDeclaredFields())
            {
                field.setAccessible(true);
                try {
                    Object value = field.get(element);
                    data.add(value.toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            model.addRow(data.toArray());
        }

    }

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
        return list;
    }

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

    public T update(T t) {
        // TODO:
        return t;
    }
}
