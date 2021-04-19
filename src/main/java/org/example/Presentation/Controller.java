package org.example.Presentation;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.BusinessLogic.TableManager;
import org.example.BusinessLogic.Validator;
import org.example.Model.Client;
import org.example.Model.OrderP;
import org.example.Model.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**@author Mihai Fleser
 * This class communicates between the GUIs and Bussiness Logic. It handles the events that happens on the GUI.
 * It takes the data introduced by the user and transfers it further to the BussinessLogic to manage it.
 */

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

    /**
     * Takes the input from the addClientGUI and commands the clientManager to insert the new created client in the database.
     */

    private void addClient()
    {
        try {
            String firstName = addClientGUI.getFirstName();
            String lastName = addClientGUI.getLastName();
            String email = addClientGUI.getEmail();
            String telephone = addClientGUI.getTelephone();
            String address = addClientGUI.getAdrress();
            if(!Validator.checkString(firstName) || !Validator.checkString(lastName) || !Validator.checkString(email) || !Validator.checkString(telephone) || !Validator.checkString(address))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: You must complete all fields!");
                return;
            }
            if(!Validator.checkTelephoneNumber(telephone))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong telephone!");
                return;
            }
            if(!Validator.checkEmail(email))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong email!");
                return;
            }

            Client client = new Client(firstName, lastName, email, telephone, address);
            clientTableManager.insertIntoDatabase(client);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Input");
        }
    }

    /**
     * Generates a new Bill with the client and the product he ordered.
     * @param client
     * @param product
     * @param quantity
     */

    private void generateBill(Client client, Product product, int quantity)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String dateAndTime = dtf.format(now);
        dateAndTime = dateAndTime.replace(' ', '_');
        dateAndTime = dateAndTime.replace(':', '/');
        dateAndTime = dateAndTime.replace('/', '_');
        String pdfName = "BILL_" + dateAndTime +".pdf";

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(pdfName));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        document.open();
        Paragraph paragraph = new Paragraph("Client " + client.getFirstName() + " " + client.getLastName() + " has ordered:\n " +
                product.getName() + " in quantity of " + quantity + " for a total: \n" +
                product.getPrice() + " x " + quantity +" = "+product.getPrice() * quantity);

        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    /**
     * Takes the input from the addOrderGUI and commands the orderManager to insert the new created order in the database.
     */

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
            if(!Validator.checkInteger(quantity))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Quantity");
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

            generateBill(client,product,quantity);

        }catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    /**
     * Deletes the selected order from the database.
     */
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

    /**
     * Deletes the selected client from the database.
     */
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

    /**
     * Deletes the selected product from the database.
     */
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

    /**
     * Edits the selected client with the new values inserted by the user.
     */

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

            if(!Validator.checkString(firstName) || !Validator.checkString(lastName) || !Validator.checkString(email) || !Validator.checkString(telephone) || !Validator.checkString(address))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: You must complete all fields!");
                return;
            }
            if(!Validator.checkTelephoneNumber(telephone))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong telephone!");
                return;
            }
            if(!Validator.checkEmail(email))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong email!");
                return;
            }

            Client c = new Client(id, firstName, lastName, email, telephone, address);
            clientTableManager.edit(c);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to edit and you must insert valid data");
        }
    }

    /**
     * Edits the selected product with the new values inserted by the user.
     */

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

            if(!Validator.checkString(firstName))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: You must give a name to the new product");
                return;
            }
            if(!Validator.checkInteger(quantity))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Quantity");
                return;
            }
            if(!Validator.checkInteger(price))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Price");
                return;
            }

            Product p = new Product(id, firstName, price, quantity);
            productTableManager.edit(p);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: You Must select something to edit and you must insert valid data");
        }
    }

    /**
     * Takes the input from the addProductGUI and commands the productManager to insert the new created product in the database.
     */

    private void addProduct()
    {
        try {
            if(!Validator.checkString(addProductGUI.getName()))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: You must give a name to the new product");
                return;
            }
            if(!Validator.checkInteger(Integer.valueOf(addProductGUI.getQuantity())))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Quantity");
                return;
            }
            if(!Validator.checkInteger(Integer.valueOf(addProductGUI.getPrice())))
            {
                JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Price");
                return;
            }
            Product product = new Product(addProductGUI.getName(), Integer.valueOf(addProductGUI.getPrice()), Integer.valueOf(addProductGUI.getQuantity()));
            productTableManager.insertIntoDatabase(product);
        }catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(), "ERROR: Wrong Input");
        }
    }

    /**
     * Sets up the actionListeners on the GUIs.
     */

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
