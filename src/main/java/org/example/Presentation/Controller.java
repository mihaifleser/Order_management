package org.example.Presentation;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.BusinessLogic.TableManager;
import org.example.Model.Client;
import org.example.Model.OrderP;
import org.example.Model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;

public class Controller {
    private GUI gui;
    private AddClientGUI addClientGUI;
    private AddProductGUI addProductGUI;
    private AddOrderGUI addOrderGUI;

    private TableManager<Client> clientTableManager = new TableManager(Client.class);
    private TableManager<Product> productTableManager = new TableManager(Product.class);
    private TableManager<OrderP> orderTableManager = new TableManager(OrderP.class);

    public Controller()
    {
        this.gui = new GUI();
        this.addClientGUI = new AddClientGUI();
        this.addProductGUI = new AddProductGUI();
        this.addOrderGUI = new AddOrderGUI();
    }

    private void addClient()
    {
        try {
            Client client = new Client(addClientGUI.getFirstName(), addClientGUI.getLastName(), addClientGUI.getEmail(), addClientGUI.getTelephone(), addClientGUI.getAdrress());
            clientTableManager.insertIntoDatabase(client);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Input");
        }
    }

    private void addOrder()
    {
        try {
            int clientId = Integer.valueOf(addOrderGUI.getClientId());
            int productId = Integer.valueOf(addOrderGUI.getProductId());
            int quantity = Integer.valueOf(addOrderGUI.getQuantity());

            Client client = clientTableManager.findById(clientId);
            if (client == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Can't find this client in the database.");
                return;
            }

            Product product = productTableManager.findById(productId);
            if (product == null) {
                JOptionPane.showMessageDialog(new JFrame(), "Can't find this product in the database.");
                return;
            }
            if (product.getQuantity() < quantity) {
                JOptionPane.showMessageDialog(new JFrame(), "Not Enough Products in stock.");
                return;
            }

            orderTableManager.insertIntoDatabase(new OrderP(clientId, productId, quantity));
            product.setQuantity(product.getQuantity() - quantity);
            productTableManager.edit(product);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Bill.pdf"));

            document.open();
            Paragraph paragraph = new Paragraph("Client " + client.getFirstName() + " " + client.getLastName() + " has ordered:\n " +
                    product.getName() + " in quantity of " + quantity + " for a total: \n" +
                    product.getPrice() + " x " + quantity +" = "+product.getPrice() * quantity);

            document.add(paragraph);
            document.close();

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    private void deleteOrder()
    {
        try {
            int row = gui.getOrdersTable().getSelectedRow();
            int col = 0;
            String value = gui.getOrdersTable().getModel().getValueAt(row, col).toString();
            int id = Integer.valueOf(value);
            orderTableManager.deleteFromDatabase(id);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to delete");
        }
    }

    private void deleteClient()
    {
        try {
            int row = gui.getClientsTable().getSelectedRow();
            int col = 0;
            String value = gui.getClientsTable().getModel().getValueAt(row, col).toString();
            int id = Integer.valueOf(value);
            clientTableManager.deleteFromDatabase(id);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to delete");
        }
    }

    private void deleteProduct()
    {
        try {
            int row = gui.getProductsTable().getSelectedRow();
            int col = 0;
            String value = gui.getProductsTable().getModel().getValueAt(row, col).toString();
            int id = Integer.valueOf(value);
            productTableManager.deleteFromDatabase(id);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to delete");
        }
    }

    private void editClient()
    {
        try {
            int row = gui.getClientsTable().getSelectedRow();
            int col = 0;
            String value = gui.getClientsTable().getModel().getValueAt(row, col).toString();
            int id = Integer.valueOf(value);
            value = gui.getClientsTable().getModel().getValueAt(row, col + 1).toString();
            String firstName = value;
            value = gui.getClientsTable().getModel().getValueAt(row, col + 2).toString();
            String lastName = value;
            value = gui.getClientsTable().getModel().getValueAt(row, col + 3).toString();
            String email = value;
            value = gui.getClientsTable().getModel().getValueAt(row, col + 4).toString();
            String telephone = value;
            value = gui.getClientsTable().getModel().getValueAt(row, col + 5).toString();
            String address = value;
            Client c = new Client(id, firstName, lastName, email, telephone, address);
            clientTableManager.edit(c);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to edit and you must insert valid data");
        }
    }

    private void editProduct()
    {
        try {
            int row = gui.getProductsTable().getSelectedRow();
            int col = 0;
            String value = gui.getProductsTable().getModel().getValueAt(row, col).toString();
            int id = Integer.valueOf(value);
            value = gui.getProductsTable().getModel().getValueAt(row, col + 1).toString();
            String firstName = value;
            value = gui.getProductsTable().getModel().getValueAt(row, col + 2).toString();
            int price = Integer.valueOf(value);
            value = gui.getProductsTable().getModel().getValueAt(row, col + 3).toString();
            int quantity = Integer.valueOf(value);
            Product p = new Product(id, firstName, price, quantity);
            productTableManager.edit(p);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to edit and you must insert valid data");
        }
    }

    private void addProduct()
    {
        try {
            Product product = new Product(addProductGUI.getName(), Integer.valueOf(addProductGUI.getPrice()), Integer.valueOf(addProductGUI.getQuantity()));
            productTableManager.insertIntoDatabase(product);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Input");
        }
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

            gui.setActionOnEditClientsButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editClient();
                    clientTableManager.refreshTable(gui.getClientsTable());

                }
            });

            gui.setActionOnEditProductsButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editProduct();
                    productTableManager.refreshTable(gui.getProductsTable());

                }
            });

            gui.setActionOnAddOrderButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addOrderGUI.initialise();

                }
            });

            addOrderGUI.setActionOnAddButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addOrder();
                    orderTableManager.refreshTable(gui.getOrdersTable());
                    addOrderGUI.getFrame().dispose();
                    productTableManager.refreshTable(gui.getProductsTable());
                }
            });

    }
}
