import greenfoot.*;

public class Kartu extends Actor 
{

    public static final int LEBAR = 90;
    public static final int TINGGI = 120;

    private final int id;
    private final GreenfootImage depan;
    private final GreenfootImage belakang;

    public Kartu(int id) 
    {
        this.id = id;
        depan = muat("kartu_" + id + ".jpg");
        belakang = muat("kartu_belakang.jpg");
        setImage(depan);
    }

    public void tampil()   { setImage(depan); }
    public void sembunyi() { setImage(belakang); }
    public int getId()     { return id; }

    private GreenfootImage muat(String namaFile) 
    {
        GreenfootImage img;
        try {
            img = new GreenfootImage(namaFile);
            img.scale(LEBAR, TINGGI);
        } catch (IllegalArgumentException e) {
            img = new GreenfootImage(LEBAR, TINGGI);
            img.setColor(Color.DARK_GRAY);
            img.fill();
            img.setColor(Color.WHITE);
            img.drawString(namaFile, 5, TINGGI / 2);
        }
        return img;
    }
}