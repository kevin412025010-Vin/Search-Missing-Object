import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Rumah here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Rumah extends Actor
{
    private String level;
    private int px, py;
    private boolean siap = false;

    public Rumah(String gambar, String level, int lebar)
    {
        this.level = level;
        GreenfootImage img = new GreenfootImage(gambar);
        img.scale(lebar, img.getHeight() * lebar / img.getWidth());
        setImage(img);
    }

    public void act()
    {
        if (!siap)
        {
            px = getX();
            py = getY();
            siap = true;
        }
        setLocation(px, py);

        if (Greenfoot.mouseClicked(this) && playerDekat())
        {
            if (level.equals("easy"))
            {
                Greenfoot.setWorld(new Level_Easy());
            }
            else if (level.equals("normal"))
            {
                Greenfoot.setWorld(new Level_Normal());
            }
            else if (level.equals("hard"))
            {
                Greenfoot.setWorld(new Level_Hard());
            }
        }
    }

    private boolean playerDekat()
    {
        return !getObjectsInRange(60, Player.class).isEmpty();
    }
}
