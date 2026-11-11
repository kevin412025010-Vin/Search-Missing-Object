import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Button here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Button extends Actor
{
    private String jenis;
    private int px, py;
    private boolean siap = false;

    public Button(String gambar, String jenis)
    {
        this.jenis = jenis;
        GreenfootImage img = new GreenfootImage(gambar);
        img.scale(260, 75);
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

        if (Greenfoot.mouseClicked(this))
        {
            if (jenis.equals("start"))
            {
                Greenfoot.setWorld(new Map_Awal());
            }
            else if (jenis.equals("setting"))
            {
                // nanti diisi
            }
            else if (jenis.equals("quit"))
            {
                Greenfoot.stop();
            }
        }
    }
}
