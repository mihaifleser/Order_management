package org.example.Presentation;

public class Controller {
    private GUI gui;


    public Controller()
    {
        this.gui = new GUI();
    }

    public void run()
    {
        gui.initialise();
    }
}
