package org.example.Presentation;

import org.example.BusinessLogic.AbstractDAO;
import org.example.Model.Client;
import org.example.Model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private GUI gui;


    public Controller()
    {
        this.gui = new GUI();
    }

    public void run()
    {
        gui.initialise();
        final AbstractDAO<Client> cc = new AbstractDAO(Client.class);
        cc.setTable(gui.getClientsTable());

        final AbstractDAO<Product> pp = new AbstractDAO(Product.class);
        pp.setTable(gui.getProductsTable());

        gui.setActionOnRefreshClientsButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cc.refreshTable(gui.getClientsTable());
            }
        });
        gui.setActionOnRefreshProductsButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pp.refreshTable(gui.getProductsTable());
            }
        });
    }
}
