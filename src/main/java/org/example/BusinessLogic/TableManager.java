package org.example.BusinessLogic;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

public class TableManager<T> {

    private final Class<T> type;
    private AbstractDAO<T> abstractDAO;

    public TableManager(Class<T> type) {
        this.type = type;
        this.abstractDAO = new AbstractDAO<T>(type);
    }

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


}
