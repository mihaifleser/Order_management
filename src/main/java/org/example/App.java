package org.example;

import org.example.BusinessLogic.AbstractDAO;
import org.example.Model.Client;
import org.example.Model.OrderP;
import org.example.Model.Product;
import org.example.Presentation.Controller;

import java.util.ArrayList;

public class App
{
    public static void main( String[] args )
    {
        AbstractDAO<Client> cc = new AbstractDAO(Client.class);
        AbstractDAO<Product> pp = new AbstractDAO<>(Product.class);
        AbstractDAO<OrderP> oo = new AbstractDAO(OrderP.class);

        ArrayList<Client> clients = cc.findAll();
        for(Client client:clients)
        {
            System.out.println(client.getFirstName());
        }

        ArrayList<Product> products = pp.findAll();
        for(Product product:products)
        {
            System.out.println(product.getName());
        }

        ArrayList<OrderP> orders = oo.findAll();
        for(OrderP order:orders)
        {
            System.out.println(order.getQuantity());
        }

        Product one = pp.findById(3);
        System.out.println(one.getName());

        //cc.deleteById(15);
        //pp.deleteById(5);

        //Client example = new Client("Arhimede","Paltinis", "ahrp@yahoo.com", "0765346575" ,"Fagaras");
        //cc.insert(example);

        //Product example = new Product("TV LG", 1200,6);
        //pp.insert(example);

        //OrderP example = new OrderP(3, 2,4);
        //oo.insert(example);

        Controller controller = new Controller();
        controller.run();



    }
}
