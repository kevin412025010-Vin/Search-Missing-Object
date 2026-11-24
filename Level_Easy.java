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
        super(1, 4, 10, 3, 5, "Level_Easy.jpg", 230, 410);
        
        if (!Map_Awal.dialogDilihat[1]) 
        {
            Map_Awal.dialogDilihat[1] = true;
            tampilkanDialog(Dialog.RUMAH_EASY, "Tuytuy");
        }
    }

    protected World levelBerikutnya() 
    {
        return new Map_Awal(true);
    }
}

