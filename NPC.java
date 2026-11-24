import greenfoot.*;

public class NPC extends Actor
{
    private static final String[] EKSTENSI = {"png", "jpg", "jpeg"};
    private static final int TINGGI_GAMBAR = 250;   // tinggi karakter di layar
    private static final int FADE_MS = 300;         // lama efek muncul
    private static final int AYUN_PIKSEL = 4;       // seberapa naik-turun karakter (0 = diam)

    private final long waktuMulai = System.currentTimeMillis();
    private int yDasar = -1;
    private boolean penuh = false;

    public NPC(String namaDasar)
    {
        GreenfootImage img = muatGambar(namaDasar);
        if (img != null)
        {
            int lebar = img.getWidth() * TINGGI_GAMBAR / img.getHeight();
            img.scale(lebar, TINGGI_GAMBAR);
            img.setTransparency(0);
            setImage(img);
        }
        else
        {
            setImage(new GreenfootImage(1, 1));   // gambar tidak ada: dialog tetap jalan
        }
    }

    public void act()
    {
        if (yDasar < 0) yDasar = getY();
        long lewat = System.currentTimeMillis() - waktuMulai;

        // muncul perlahan
        if (!penuh)
        {
            if (lewat >= FADE_MS)
            {
                getImage().setTransparency(255);
                penuh = true;
            }
            else
            {
                getImage().setTransparency((int) (255L * lewat / FADE_MS));
            }
        }

        // naik-turun pelan seperti bernapas
        setLocation(getX(), yDasar + (int) Math.round(Math.sin(lewat / 350.0) * AYUN_PIKSEL));
    }

    private GreenfootImage muatGambar(String namaDasar)
    {
        for (String ext : EKSTENSI)
        {
            try
            {
                return new GreenfootImage(namaDasar + "." + ext);
            }
            catch (IllegalArgumentException e)
            {
                // coba ekstensi berikutnya
            }
        }
        return null;
    }
}