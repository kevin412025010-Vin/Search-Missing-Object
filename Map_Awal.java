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
        
        setPaintOrder(Player.class, Rumah.class);
        
        addObject(new Rumah("Rumah_Easy.png", "easy", 210), 487, 170);
        addObject(new Rumah("Rumah_Normal.png", "normal", 190), 782, 165);
        //addObject(new Rumah("Rumah_Hard.png", "hard", 240), 375, 310);
        
        addObject(new Player(), 30, 190);
    }
}
