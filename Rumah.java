import greenfoot.*;

public class Rumah extends Actor
{
    private String level;
    private int px, py;
    private boolean siap = false;

    private int radiusKlik = 60;   // jarak karakter ke rumah agar bisa diklik

    private final Teks petunjuk = new Teks("Terkunci!", 20);
    private long pesanSampai = 0;

    public Rumah(String gambar, String level, int lebar)
    {
        this.level = level;
        if (level.equals("hard")) radiusKlik = 100;   // lubang lebih besar, jarak klik dilebarkan

        GreenfootImage img = new GreenfootImage(gambar);
        img.scale(lebar, img.getHeight() * lebar / img.getWidth());
        if (!terbuka()) img.setTransparency(120);   // rumah terkunci tampak pudar
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

        // sembunyikan pesan setelah 2 detik
        if (petunjuk.getWorld() != null && System.currentTimeMillis() > pesanSampai)
        {
            getWorld().removeObject(petunjuk);
        }

        if (Greenfoot.mouseClicked(this) && playerDekat() && !Map_Awal.tutorialAktif)
        {
            if (!terbuka())
            {
                tampilkanPesan("Terkunci! Selesaikan Rumah " + (nomor() - 1) + " dulu");
            }
            else if (level.equals("easy"))
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

    // easy = rumah 1, normal = rumah 2, hard = rumah 3
    private int nomor()
    {
        if (level.equals("easy")) return 1;
        if (level.equals("normal")) return 2;
        return 3;
    }

    // rumah 1 selalu terbuka; rumah berikutnya terbuka jika level sebelumnya sudah selesai
    private boolean terbuka()
    {
        return nomor() == 1 || LevelBase.levelSelesai >= nomor() - 1;
    }

    private void tampilkanPesan(String teks)
    {
        petunjuk.setText(teks);
        if (petunjuk.getWorld() == null)
        {
            int y = Math.max(30, getY() - getImage().getHeight() / 2 - 20);
            getWorld().addObject(petunjuk, getX(), y);
        }
        pesanSampai = System.currentTimeMillis() + 2000;
    }

    private boolean playerDekat()
    {
        return !getObjectsInRange(radiusKlik, Player.class).isEmpty();
    }
}