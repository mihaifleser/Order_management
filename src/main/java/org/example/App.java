package org.example;

import org.example.Presentation.Controller;

/**@author Mihai Fleser
 * The start point of the application. Just creates a new controller that will handle the rest.
 */

public class App
{
    public static void main( String[] args )
    {
        Controller controller = new Controller();
        controller.run();
    }
}
