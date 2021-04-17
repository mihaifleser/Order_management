package org.example.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddProductGUI {

    private JFrame frame = new JFrame("Insert new Client");

    private JTextArea name = new JTextArea();
    private JTextArea price = new JTextArea();
    private JTextArea quantity = new JTextArea();
    private JButton add = new JButton("ADD");

    public AddProductGUI()
    {

    }

    public void setActionOnAddButton(ActionListener actionListener)
    {
        add.addActionListener(actionListener);
    }

    public String getName()
    {
        return name.getText();
    }

    public String getPrice()
    {
        return price.getText();
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

        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        nameLabel.setBounds(leftMargin, labelHeight,labelWidth,labelHeight);
        frame.add(nameLabel);

        name.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        name.setBounds(nameLabel.getX(), nameLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(name);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        priceLabel.setBounds(leftMargin, labelHeight * 3,labelWidth,labelHeight);
        frame.add(priceLabel);

        price.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        price.setBounds(priceLabel.getX(), priceLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(price);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        quantityLabel.setBounds(leftMargin, labelHeight * 5,labelWidth,labelHeight);
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
