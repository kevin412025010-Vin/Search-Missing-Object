import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level_Normal here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level_Normal extends World
{

    /**
     * Constructor for objects of class Level_Normal.
     * 
     */
    public Level_Normal()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(960, 540, 1); 
        
        GreenfootImage bg = new GreenfootImage("Level_Normal.jpg");
        bg.scale(960, 540);
        
        setBackground(bg);
    }
}
