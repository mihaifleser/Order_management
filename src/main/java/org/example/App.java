package org.example;

import org.example.DAO.AbstractDAO;
import org.example.Model.Client;
import org.example.Model.OrderP;
import org.example.Model.Product;
import org.example.Presentation.Controller;

import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {

        ArrayList<Client> clients = (new AbstractDAO<Client>(Client.class)).findAll();
        for(Client client:clients)
        {
            System.out.println(client.getFirstName());
        }

        ArrayList<Product> products = (new AbstractDAO<Product>(Product.class)).findAll();
        for(Product product:products)
        {
            System.out.println(product.getName());
        }

        ArrayList<OrderP> orders = (new AbstractDAO<OrderP>(OrderP.class)).findAll();
        for(OrderP order:orders)
        {
            System.out.println(order.getQuantity());
        }



        Controller controller = new Controller();
        controller.run();



    }
}
