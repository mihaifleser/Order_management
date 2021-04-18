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
        Controller controller = new Controller();
        controller.run();
    }
}
