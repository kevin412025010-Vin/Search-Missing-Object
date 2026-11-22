import greenfoot.*;

public class Teks extends Actor 
{

    private final int ukuran;
    private String isi = "";

    public Teks(String isi, int ukuran) 
    {
        this.ukuran = ukuran;
        setText(isi);
    }

    public void setText(String baru) 
    {
        if (baru.equals(isi)) return;
        isi = baru;
        setImage(new GreenfootImage(baru, ukuran, Color.WHITE, new Color(0, 0, 0, 150)));
    }
}