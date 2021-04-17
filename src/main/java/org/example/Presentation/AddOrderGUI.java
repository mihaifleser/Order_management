package org.example.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddOrderGUI {

    private JFrame frame = new JFrame("Add new Order");

    private JTextArea clientId = new JTextArea();
    private JTextArea productId = new JTextArea();
    private JTextArea quantity = new JTextArea();
    private JButton add = new JButton("ADD");

    public AddOrderGUI()
    {

    }

    public void setActionOnAddButton(ActionListener actionListener)
    {
        add.addActionListener(actionListener);
    }

    public String getClientId()
    {
        return clientId.getText();
    }

    public String getProductId()
    {
        return productId.getText();
    }

    public String getQuantity()
    {
        return quantity.getText();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void initialise()
    {
        Color frameColor = Color.decode("#4ECDC4");
        Color buttonColor = Color.decode("#1A535C");
        int width = 400; int height = 720;
        int labelWidth = width / 4;
        int labelHeight = height / 15;
        int leftMargin = width / 60;
        int buttonWidth = width / 3;
        int buttonHeight = height / 8;

        JLabel clientLabel = new JLabel("Insert Client Id: ");
        clientLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        clientLabel.setBounds(leftMargin, labelHeight,labelWidth * 2,labelHeight);
        frame.add(clientLabel);

        clientId.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        clientId.setBounds(clientLabel.getX(), clientLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(clientId);

        JLabel productLabel = new JLabel("Insert Product Id:");
        productLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        productLabel.setBounds(leftMargin, labelHeight * 3,labelWidth * 2,labelHeight);
        frame.add(productLabel);

        productId.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        productId.setBounds(productLabel.getX(), productLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(productId);

        JLabel quantityLabel = new JLabel("Insert Quantity:");
        quantityLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        quantityLabel.setBounds(leftMargin, labelHeight * 5,labelWidth * 2,labelHeight);
        frame.add(quantityLabel);

        quantity.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        quantity.setBounds(quantityLabel.getX(), quantityLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(quantity);

        add.setBounds(leftMargin,quantity.getY() + labelHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
        add.setBackground(buttonColor);
        add.setFont(new Font(Font.SERIF,  Font.BOLD, 16));
        add.setForeground(Color.white);
        frame.add(add);

        frame.setSize(width,height + 60);
        frame.setLayout(null);//using no layout managers
        frame.getContentPane().setBackground(frameColor);
        frame.setVisible(true);//making the frame visible
    }

}
