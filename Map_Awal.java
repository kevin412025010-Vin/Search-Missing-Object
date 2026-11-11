import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Map_Awal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Map_Awal extends World
{

    /**
     * Constructor for objects of class Map_Awal.
     * 
     */
    public Map_Awal()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 540, 1);

        GreenfootImage bg = new GreenfootImage("Lahan.jpg");
        bg.scale(960, 540);
        setBackground(bg);
        
        addObject(new Player(), 30, 205);
    }
}
