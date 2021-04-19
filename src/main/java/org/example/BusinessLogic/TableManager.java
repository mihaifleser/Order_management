package org.example.BusinessLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

/** @author Mihai Fleser
 * is created by the controller of the application to perform diverse operation, either with the database or with some
 * tables on the GUI
 * @param <T>
 */

public class TableManager<T> {

    private final Class<T> type;
    private AbstractDAO<T> abstractDAO;

    public TableManager(Class<T> type) {
        this.type = type;
        this.abstractDAO = new AbstractDAO<T>(type);
    }

    /**
     * Receives from the database an arraylist of objects and sets up a table.
     * It generates the header of the table by extracting through reflection the object properties and
     * then populates the table with the values of the elements from the list.
     * @param table - the table to be set up
     */

    public void setTable(JTable table)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<T> elements = abstractDAO.findAll();
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

    /**
     * Refreshes an already generated table with the elements from the database.
     * @param table
     */

    public void refreshTable(JTable table)
    {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        ArrayList<T> elements = abstractDAO.findAll();

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

    /**
     * Inserts into the database a new element by calling the AbstractDAO method for inserting.
     * @param t
     */

    public void insertIntoDatabase(T t)
    {
        abstractDAO.insert(t);
    }

    /**
     * Deletes from the databases using the id.
     * @param id
     */

    public void deleteFromDatabase(int id)
    {
        abstractDAO.deleteById(id);
    }

    /**
     * Edits an element from the database.
     * @param t
     */
    public void edit(T t)
    {
        abstractDAO.update(t);
    }

    /**
     * Finds by id an element from a table which is in the database.
     * @param id
     * @return the element searched for.
     */

    public T findById(int id)
    {
        return abstractDAO.findById(id);
    }

}
