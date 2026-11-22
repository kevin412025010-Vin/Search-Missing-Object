import greenfoot.*;

public class Kartu extends Actor {

    public static final int LEBAR = 90;
    public static final int TINGGI = 120;

    private static final double FAKTOR_BELAKANG = 1.0;
    private static final String[] EKSTENSI = {"jpg", "jpeg", "png"};
    private static GreenfootImage belakangCache;

    private final int id;
    private final GreenfootImage depan;
    private final GreenfootImage belakang;

    public Kartu(int id) 
    {
        this.id = id;
        depan = muat("kartu_" + id, false);
        if (belakangCache == null) 
        {
            belakangCache = muat("kartu_belakang", true);
        }
        belakang = belakangCache;
        setImage(depan);
    }

    public void tampil()   { setImage(depan); }
    public void sembunyi() { setImage(belakang); }
    public int getId()     { return id; }

    private GreenfootImage muat(String namaDasar, boolean potongTepi) {
        for (String ext : EKSTENSI) 
        {
            try 
            {
                GreenfootImage img = new GreenfootImage(namaDasar + "." + ext);
                int l = LEBAR;
                int t = TINGGI;
                if (potongTepi) {
                    img = potongTransparan(img);
                    l = (int) Math.round(LEBAR * FAKTOR_BELAKANG);
                    t = (int) Math.round(TINGGI * FAKTOR_BELAKANG);
                }
                img.scale(l, t);
                return img;
            } 
            catch (IllegalArgumentException e) 
            {
                // file dengan ekstensi ini tidak ada, coba berikutnya
            }
        }

        GreenfootImage img = new GreenfootImage(LEBAR, TINGGI);
        img.setColor(Color.DARK_GRAY);
        img.fill();
        img.setColor(Color.WHITE);
        img.drawString(namaDasar, 5, TINGGI / 2);
        return img;
    }

    private GreenfootImage potongTransparan(GreenfootImage img) 
    {
        int w = img.getWidth();
        int h = img.getHeight();
        int minX = w, minY = h, maxX = -1, maxY = -1;

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (img.getColorAt(x, y).getAlpha() > 20) {
                    if (x < minX) minX = x;
                    if (x > maxX) maxX = x;
                    if (y < minY) minY = y;
                    if (y > maxY) maxY = y;
                }
            }
        }

        if (maxX < 0) return img;   // gambar tidak punya tepi transparan yang bisa dipotong

        GreenfootImage hasil = new GreenfootImage(maxX - minX + 1, maxY - minY + 1);
        hasil.drawImage(img, -minX, -minY);
        return hasil;
    }
}