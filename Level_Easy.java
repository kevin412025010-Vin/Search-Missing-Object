import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Level_Easy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Level_Easy extends LevelBase
{

    /**
     * Constructor for objects of class Level_Easy.
     * 
     */
    public Level_Easy()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1, 4, 10, 3, 5, "Level_Easy.jpg");
    }

    protected World levelBerikutnya() 
    {
        return new Level_Normal();
    }
}

