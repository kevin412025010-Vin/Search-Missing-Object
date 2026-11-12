import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor
{
    private int kecepatan = 2;
    private String arah = "kanan";

    private GreenfootImage gKanan;
    private GreenfootImage gKiri;
    private GreenfootImage gBelakang;
    private GreenfootImage gDepan;

    // ukuran sprite kelinci
    private int ukuran = 48;

    // untuk animasi lompat
    private int timer = 0;
    private boolean bergerak = false;
    private int lamaLompat = 24;   // makin kecil = lompat makin cepat
    private int tinggiLompat = 14; // tinggi lompatan (piksel)

    public static int LEBAR = 28;

    // tiap garis: {x1, y1, x2, y2, lebar}
    public static int[][] JALUR =
    {
        {  50, 210, 145, 233, 28 },
        { 145, 233, 177, 256, 28 },
        { 177, 256, 225, 272, 28 },
        { 225, 272, 401, 272, 28 },
        { 401, 272, 477, 266, 28 },
        { 477, 266, 577, 260, 28 },
        { 577, 260, 669, 268, 28 },
        { 669, 268, 696, 252, 28 },
        { 696, 252, 723, 228, 28 },
        { 478, 222, 478, 266, 16 },
        { 478, 266, 482, 539, 28 },
        { 431, 373, 478, 370, 28 }
    };

    public Player()
    {
        gKanan    = new GreenfootImage("Player_Kanan.png");
        gKiri     = new GreenfootImage("Player_Kiri.png");
        gBelakang = new GreenfootImage("Player_Belakang.png");
        gDepan    = new GreenfootImage("Player_Depan.png");

        gKanan.scale(ukuran, ukuran);
        gKiri.scale(ukuran, ukuran);
        gBelakang.scale(ukuran, ukuran);
        gDepan.scale(ukuran, ukuran);

        updateGambar();
    }

    public void act()
    {
        gerak();
    }

    private void gerak()
    {
        int dx = 0;
        int dy = 0;

        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left"))
        {
            dx = dx - kecepatan;
            arah = "kiri";
        }
        if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("right"))
        {
            dx = dx + kecepatan;
            arah = "kanan";
        }
        if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("up"))
        {
            dy = dy - kecepatan;
            arah = "belakang";
        }
        if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("down"))
        {
            dy = dy + kecepatan;
            arah = "depan";
        }

        int kaki = ukuran / 2 - 4; // titik yang dicek = kaki karakter
        int awalX = getX();
        int awalY = getY();

        if (boleh(getX() + dx, getY() + kaki))
        {
            setLocation(getX() + dx, getY());
        }
        if (boleh(getX(), getY() + dy + kaki))
        {
            setLocation(getX(), getY() + dy);
        }

        // dianggap bergerak kalau posisinya benar-benar berubah
        bergerak = (getX() != awalX || getY() != awalY);

        if (bergerak)
        {
            timer++;
        }
        else
        {
            timer = 0;
        }

        updateGambar();
    }

    private boolean boleh(int px, int py)
    {
        for (int i = 0; i < JALUR.length; i++)
        {
            if (jarak(px, py, JALUR[i]) <= JALUR[i][4])
            {
                return true;
            }
        }
        return false;
    }

    // jarak titik (px, py) ke satu garis
    private double jarak(int px, int py, int[] s)
    {
        double vx = s[2] - s[0];
        double vy = s[3] - s[1];
        double wx = px - s[0];
        double wy = py - s[1];
        double len2 = vx * vx + vy * vy;

        double t = (wx * vx + wy * vy) / len2;
        if (t < 0) t = 0;
        if (t > 1) t = 1;

        double cx = s[0] + t * vx;
        double cy = s[1] + t * vy;
        return Math.sqrt((px - cx) * (px - cx) + (py - cy) * (py - cy));
    }

    private void updateGambar()
    {
        GreenfootImage dasar;

        if (arah.equals("kanan"))          dasar = gKanan;
        else if (arah.equals("kiri"))      dasar = gKiri;
        else if (arah.equals("belakang"))  dasar = gBelakang;
        else                               dasar = gDepan;

        setImage(gambarLompat(dasar));
    }

    // menggambar sprite dengan efek lompat dan squash & stretch
    private GreenfootImage gambarLompat(GreenfootImage dasar)
    {
        int kanvasL = ukuran + 8;
        int kanvasT = ukuran + tinggiLompat + 8;
        GreenfootImage img = new GreenfootImage(kanvasL, kanvasT);

        int naik = 0;
        int lebar = ukuran;
        int tinggi = ukuran;

        if (bergerak)
        {
            // fase 0..1 di dalam satu lompatan
            double fase = (timer % lamaLompat) / (double) lamaLompat;

            // parabola: 0 di tanah, 1 di puncak
            double tinggiRel = Math.sin(fase * Math.PI);
            naik = (int) Math.round(tinggiRel * tinggiLompat);

            // squash saat mendarat/ancang-ancang, stretch saat di udara
            if (tinggiRel < 0.25)
            {
                lebar = (int) (ukuran * 1.12);
                tinggi = (int) (ukuran * 0.88);
            }
            else
            {
                lebar = (int) (ukuran * 0.94);
                tinggi = (int) (ukuran * 1.06);
            }
        }

        GreenfootImage salinan = new GreenfootImage(dasar);
        salinan.scale(lebar, tinggi);

        // alas sprite selalu menempel di bagian bawah kanvas, lalu naik sebesar 'naik'
        int x = (kanvasL - lebar) / 2;
        int y = kanvasT - 4 - tinggi - naik;
        img.drawImage(salinan, x, y);

        return img;
    }
}
