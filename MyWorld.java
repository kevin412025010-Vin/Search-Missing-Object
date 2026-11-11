import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 540, 1);

        GreenfootImage bg = new GreenfootImage("Hutan.jpeg");
        bg.scale(960, 540);
        setBackground(bg);

        int tengah = getWidth() / 2;

        addObject(new Button("Start.png", "start"), tengah, 270);
        addObject(new Button("Setting.png", "setting"), tengah, 360);
        addObject(new Button("Quit.png", "quit"), tengah, 450);
    }
}
