package org.example.Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AddClientGUI {

    private JFrame frame = new JFrame("Insert new Client");

    private JTextArea firstName = new JTextArea();
    private JTextArea lastName = new JTextArea();
    private JTextArea email = new JTextArea();
    private JTextArea telephone = new JTextArea();
    private JTextArea address = new JTextArea();
    private JButton add = new JButton("ADD");

    public String getFirstName()
    {
        return firstName.getText();
    }

    public String getLastName()
    {
        return lastName.getText();
    }

    public String getEmail()
    {
        return email.getText();
    }
    public String getTelephone()
    {
        return telephone.getText();
    }
    public String getAdrress()
    {
        return address.getText();
    }

    public void setActionOnAddButton(ActionListener actionListener)
    {
        add.addActionListener(actionListener);
    }

    public JFrame getFrame() {
        return frame;
    }

    public AddClientGUI()
    {

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

        JLabel nameLabel1 = new JLabel("FirstName: ");
        nameLabel1.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        nameLabel1.setBounds(leftMargin, labelHeight,labelWidth,labelHeight);
        frame.add(nameLabel1);

        firstName.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        firstName.setBounds(nameLabel1.getX(), nameLabel1.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(firstName);

        JLabel nameLabel2 = new JLabel("Last Name:");
        nameLabel2.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        nameLabel2.setBounds(leftMargin, labelHeight * 3,labelWidth,labelHeight);
        frame.add(nameLabel2);

        lastName.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        lastName.setBounds(nameLabel2.getX(), nameLabel2.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(lastName);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        emailLabel.setBounds(leftMargin, labelHeight * 5,labelWidth,labelHeight);
        frame.add(emailLabel);

        email.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        email.setBounds(emailLabel.getX(), emailLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(email);

        JLabel telephoneLabel = new JLabel("Telephone:");
        telephoneLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        telephoneLabel.setBounds(leftMargin, labelHeight * 7,labelWidth,labelHeight);
        frame.add(telephoneLabel);

        telephone.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        telephone.setBounds(telephoneLabel.getX(), telephoneLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(telephone);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font(Font.SERIF,  Font.ITALIC, 18));
        addressLabel.setBounds(leftMargin, labelHeight * 9,labelWidth,labelHeight);
        frame.add(addressLabel);

        address.setFont(new Font(Font.SERIF,  Font.PLAIN, 17));
        address.setBounds(addressLabel.getX(), addressLabel.getY() + labelHeight,labelWidth * 2,labelHeight - labelHeight / 3);
        frame.add(address);

        add.setBounds(leftMargin,address.getY() + labelHeight,buttonWidth, buttonHeight);//x axis, y axis, width, height
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
