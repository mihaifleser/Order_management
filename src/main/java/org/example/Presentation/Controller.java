package org.example.Presentation;

import org.example.BusinessLogic.TableManager;
import org.example.Model.Client;
import org.example.Model.OrderP;
import org.example.Model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class Controller {
    private GUI gui;
    private AddClientGUI addClientGUI;
    private AddProductGUI addProductGUI;

    private TableManager clientTableManager = new TableManager(Client.class);
    private TableManager productTableManager = new TableManager(Product.class);
    private TableManager orderTableManager = new TableManager(OrderP.class);

    public Controller()
    {
        this.gui = new GUI();
        this.addClientGUI = new AddClientGUI();
        this.addProductGUI = new AddProductGUI();
    }

    private void addClient()
    {
        Client client = new Client(addClientGUI.getFirstName(),addClientGUI.getLastName(),addClientGUI.getEmail(),addClientGUI.getTelephone(),addClientGUI.getAdrress());
        clientTableManager.insertIntoDatabase(client);
    }

    private void deleteOrder()
    {
        int row = gui.getOrdersTable().getSelectedRow();
        int col = 0;
        String value = gui.getOrdersTable().getModel().getValueAt(row, col).toString();
        int id = Integer.valueOf(value);
        orderTableManager.deleteFromDatabase(id);
    }

    private void deleteClient()
    {
        int row = gui.getClientsTable().getSelectedRow();
        int col = 0;
        String value = gui.getClientsTable().getModel().getValueAt(row, col).toString();
        int id = Integer.valueOf(value);
        clientTableManager.deleteFromDatabase(id);
    }

    private void deleteProduct()
    {
        int row = gui.getProductsTable().getSelectedRow();
        int col = 0;
        String value = gui.getProductsTable().getModel().getValueAt(row, col).toString();
        int id = Integer.valueOf(value);
        productTableManager.deleteFromDatabase(id);
    }

    private void addProduct()
    {
        Product product = new Product(addProductGUI.getName(), Integer.valueOf(addProductGUI.getPrice()),Integer.valueOf(addProductGUI.getQuantity()));
        productTableManager.insertIntoDatabase(product);
    }


    public void run()
    {
        gui.initialise();
        clientTableManager.setTable(gui.getClientsTable());
        orderTableManager.setTable(gui.getOrdersTable());
        productTableManager.setTable(gui.getProductsTable());

        gui.setActionOnRefreshClientsButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientTableManager.refreshTable(gui.getClientsTable());
            }
        });
        gui.setActionOnRefreshProductsButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productTableManager.refreshTable(gui.getProductsTable());
            }
        });

        gui.setActionOnAddClientButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClientGUI.initialise();
            }
        });

        gui.setActionOnAddProductButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProductGUI.initialise();
            }
        });

        addClientGUI.setActionOnAddButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
                clientTableManager.refreshTable(gui.getClientsTable());
                addClientGUI.getFrame().dispose();
            }
        });

        addProductGUI.setActionOnAddButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
                productTableManager.refreshTable(gui.getProductsTable());
                addProductGUI.getFrame().dispose();
            }
        });

        gui.setActionOnDeleteClientButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
                clientTableManager.refreshTable(gui.getClientsTable());
            }
        });

        gui.setActionOnDeleteProductButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
                productTableManager.refreshTable(gui.getProductsTable());
            }
        });
        gui.setActionOnDeleteOrderButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
                orderTableManager.refreshTable(gui.getOrdersTable());
            }
        });

    }
}
