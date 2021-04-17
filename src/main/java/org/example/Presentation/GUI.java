package org.example.Presentation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI {

    private JFrame frame = new JFrame("Car Parking");
    private JLayeredPane lpane = new JLayeredPane();
    private JButton refreshClients=new JButton("Refresh Clients");
    private JButton addClient=new JButton("Add Client");
    private JButton editClient=new JButton("Edit Client");
    private JButton deleteClient=new JButton("Delete Client");
    private JButton refreshProducts=new JButton("Refresh Products");
    private JButton addProduct=new JButton("Add Product");
    private JButton editProduct=new JButton("Edit Product");
    private JButton deleteProduct=new JButton("Delete Product");
    private JButton deleteOrder=new JButton("Delete Order");
    private JTable clientsTable = new JTable(new DefaultTableModel());
    private JTable productsTable = new JTable(new DefaultTableModel());
    private JTable orderTable = new JTable(new DefaultTableModel());

    public JTable getClientsTable()
    {
        return clientsTable;
    }

    public JTable getOrdersTable()
    {
        return orderTable;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setActionOnRefreshClientsButton(ActionListener actionListener)
    {
        refreshClients.addActionListener(actionListener);
    }

    public void setActionOnAddClientButton(ActionListener actionListener)
    {
        addClient.addActionListener(actionListener);
    }

    public void setActionOnAddProductButton(ActionListener actionListener)
    {
        addProduct.addActionListener(actionListener);
    }

    public void setActionOnRefreshProductsButton(ActionListener actionListener)
    {
        refreshProducts.addActionListener(actionListener);
    }

    public void setActionOnDeleteClientButton(ActionListener actionListener)
    {
        deleteClient.addActionListener(actionListener);
    }

    public void setActionOnDeleteProductButton(ActionListener actionListener)
    {
        deleteProduct.addActionListener(actionListener);
    }

    public void setActionOnDeleteOrderButton(ActionListener actionListener)
    {
        deleteOrder.addActionListener(actionListener);
    }


    public JTable getProductsTable()
    {
        return productsTable;
    }

    public GUI()
    {

    }

    public void initialise()
    {
        Color frameColor = Color.decode("#4ECDC4");
        Color buttonColor = Color.decode("#1A535C");
        int width = 1024; int height = 720;
        int labelWidth = width / 4;
        int labelHeight = height / 15;
        int leftMargin = width / 60;
        int buttonWidth = width / 6;
        int buttonHeight = height / 8;
        frame=new JFrame("Order Management");//creating instance of JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane jpt = new JTabbedPane();
        JPanel clientPanel = new JPanel();
        JPanel productPanel = new JPanel();
        JPanel orderPanel = new JPanel();

        clientPanel.setLayout(null);
        productPanel.setLayout(null);
        orderPanel.setLayout(null);

        JLabel titleLabel1 = new JLabel("Clients Manager");
        titleLabel1.setFont(new Font(Font.SERIF,  Font.BOLD, 22));
        titleLabel1.setBounds(labelWidth + labelWidth / 2, 0,labelWidth,labelHeight);
        clientPanel.add(titleLabel1);

        JLabel titleLabel2 = new JLabel("Products Manager");
        titleLabel2.setFont(new Font(Font.SERIF,  Font.BOLD, 22));
        titleLabel2.setBounds(labelWidth + labelWidth / 2, 0,labelWidth,labelHeight);
        productPanel.add(titleLabel2);

        JLabel titleLabel3 = new JLabel("Orders Manager");
        titleLabel3.setFont(new Font(Font.SERIF,  Font.BOLD, 22));
        titleLabel3.setBounds(labelWidth + labelWidth / 2, 0,labelWidth,labelHeight);
        orderPanel.add(titleLabel3);

        JLabel madeByLabel = new JLabel("@ Made By Fleser Mihai");
        madeByLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 14));
        madeByLabel.setBounds(labelWidth + labelWidth / 2, height - labelHeight,labelWidth,labelHeight);
        JLabel madeByLabel2 = new JLabel("@ Made By Fleser Mihai");
        madeByLabel2.setFont(new Font(Font.SERIF,  Font.ITALIC, 14));
        madeByLabel2.setBounds(labelWidth + labelWidth / 2, height - labelHeight,labelWidth,labelHeight);
        JLabel madeByLabel3 = new JLabel("@ Made By Fleser Mihai");
        madeByLabel3.setFont(new Font(Font.SERIF,  Font.ITALIC, 14));
        madeByLabel3.setBounds(labelWidth + labelWidth / 2, height - labelHeight,labelWidth,labelHeight);
        clientPanel.add(madeByLabel);
        orderPanel.add(madeByLabel2);
        productPanel.add(madeByLabel3);


        addClient.setBounds(leftMargin,titleLabel1.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        addClient.setBackground(buttonColor);
        addClient.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        addClient.setForeground(Color.white);
        clientPanel.add(addClient);

        editClient.setBounds(leftMargin,addClient.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        editClient.setBackground(buttonColor);
        editClient.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        editClient.setForeground(Color.white);
        clientPanel.add(editClient);

        deleteClient.setBounds(leftMargin,editClient.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        deleteClient.setBackground(buttonColor);
        deleteClient.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        deleteClient.setForeground(Color.white);
        clientPanel.add(deleteClient);

        refreshClients.setBounds(leftMargin,deleteClient.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        refreshClients.setBackground(buttonColor);
        refreshClients.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        refreshClients.setForeground(Color.white);
        clientPanel.add(refreshClients);

        JScrollPane sp = new JScrollPane(clientsTable);
        sp.setBounds(addClient.getX() + labelWidth,addClient.getY(),700,500);
        clientPanel.add(sp);

        addProduct.setBounds(leftMargin,titleLabel1.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        addProduct.setBackground(buttonColor);
        addProduct.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        addProduct.setForeground(Color.white);
        productPanel.add(addProduct);

        editProduct.setBounds(leftMargin,addClient.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        editProduct.setBackground(buttonColor);
        editProduct.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        editProduct.setForeground(Color.white);
        productPanel.add(editProduct);

        deleteProduct.setBounds(leftMargin,editClient.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        deleteProduct.setBackground(buttonColor);
        deleteProduct.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        deleteProduct.setForeground(Color.white);
        productPanel.add(deleteProduct);

        refreshProducts.setBounds(leftMargin,deleteProduct.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        refreshProducts.setBackground(buttonColor);
        refreshProducts.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        refreshProducts.setForeground(Color.white);
        productPanel.add(refreshProducts);

        JScrollPane sp2 = new JScrollPane(productsTable);
        sp2.setBounds(addProduct.getX() + labelWidth,addProduct.getY(),700,500);
        productPanel.add(sp2);

        deleteOrder.setBounds(leftMargin,titleLabel3.getY() + buttonHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        deleteOrder.setBackground(buttonColor);
        deleteOrder.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        deleteOrder.setForeground(Color.white);
        orderPanel.add(deleteOrder);

        JScrollPane sp3 = new JScrollPane(orderTable);
        sp3.setBounds(deleteOrder.getX() + labelWidth,deleteOrder.getY(),700,500);
        orderPanel.add(sp3);


        clientPanel.setBackground(frameColor);
        productPanel.setBackground(frameColor);
        orderPanel.setBackground(frameColor);

        jpt.addTab("Clients", clientPanel);
        jpt.addTab("Products", productPanel);
        jpt.addTab("Order", orderPanel);

        //frame.pack();
        frame.add(jpt);
        frame.setSize(width,height + 60);
        //frame.setLayout(null);//using no layout managers
        //frame.getContentPane().setBackground(frameColor);
        frame.setVisible(true);//making the frame visible
    }
}

