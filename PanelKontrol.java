import greenfoot.*;

public class PanelKontrol extends Actor
{
    private enum Fase { MUNCUL, TAMPIL, HILANG }

    private static final String[] EKSTENSI = {"png", "jpg", "jpeg"};
    private static final int LEBAR_GAMBAR = 420;        // makin kecil angkanya, makin kecil panelnya
    private static final int GELAP_MAKS = 150;          // kegelapan latar (0 sampai 255)
    private static final int DURASI_MUNCUL_MS = 350;    // lama animasi muncul
    private static final int DURASI_HILANG_MS = 200;    // lama animasi hilang

    private final GreenfootImage isi;                   // gambar kontrol + tulisan "Klik untuk melanjutkan"
    private Fase fase = Fase.MUNCUL;
    private long waktuMulai = System.currentTimeMillis();

    public PanelKontrol()
    {
        GreenfootImage gambar = muatGambar("Petunjuk");
        if (gambar != null)
        {
            int tinggi = gambar.getHeight() * LEBAR_GAMBAR / gambar.getWidth();
            gambar.scale(LEBAR_GAMBAR, tinggi);
        }
        else
        {
            gambar = new GreenfootImage(LEBAR_GAMBAR, 120);
            gambar.setColor(new Color(40, 40, 40));
            gambar.fill();
            gambar.setColor(Color.WHITE);
            gambar.drawString("Gambar Kontrol tidak ditemukan di folder images", 20, 60);
        }

        GreenfootImage petunjuk = new GreenfootImage("Klik untuk melanjutkan", 20,
                                                     Color.WHITE, new Color(0, 0, 0, 0));

        isi = new GreenfootImage(LEBAR_GAMBAR, gambar.getHeight() + 14 + petunjuk.getHeight());
        isi.drawImage(gambar, 0, 0);
        isi.drawImage(petunjuk, (LEBAR_GAMBAR - petunjuk.getWidth()) / 2, gambar.getHeight() + 14);

        gambarFrame(0.0, 0.7);   // frame pertama: belum terlihat
    }

    public void act()
    {
        long lewat = System.currentTimeMillis() - waktuMulai;

        if (fase == Fase.MUNCUL)
        {
            double p = Math.min(1.0, lewat / (double) DURASI_MUNCUL_MS);
            gambarFrame(p, 0.7 + 0.3 * efekPantul(p));
            if (p >= 1.0) fase = Fase.TAMPIL;
        }
        else if (fase == Fase.TAMPIL)
        {
            if (Greenfoot.mouseClicked(null)
                || Greenfoot.isKeyDown("space")
                || Greenfoot.isKeyDown("enter"))
            {
                fase = Fase.HILANG;
                waktuMulai = System.currentTimeMillis();
            }
        }
        else if (fase == Fase.HILANG)
        {
            double p = 1.0 - lewat / (double) DURASI_HILANG_MS;
            if (p <= 0)
            {
                // panel kontrol selesai, lanjut ke cerita pembuka
                // (tutorialAktif tetap true sampai cerita selesai)
                World dunia = getWorld();
                dunia.removeObject(this);
                dunia.addObject(new Dialog(Dialog.PEMBUKA), 480, 430);
                return;
            }
            gambarFrame(p, 0.85 + 0.15 * p);
        }
    }

    // p = tingkat kemunculan (0 sampai 1), skala = ukuran panel relatif
    private void gambarFrame(double p, double skala)
    {
        GreenfootImage layar = new GreenfootImage(960, 540);

        // latar meredup
        layar.setColor(new Color(0, 0, 0, (int) (GELAP_MAKS * p)));
        layar.fill();

        // panel membesar dan memudar masuk
        int w = Math.max(1, (int) (isi.getWidth() * skala));
        int h = Math.max(1, (int) (isi.getHeight() * skala));
        GreenfootImage panel = new GreenfootImage(isi);
        panel.scale(w, h);
        panel.setTransparency((int) (255 * Math.min(1.0, p * 2.0)));

        layar.drawImage(panel, 480 - w / 2, 260 - h / 2);
        setImage(layar);
    }

    // 0 menjadi 1 dengan sedikit melampaui 1 di tengah, lalu mengendap (efek pop)
    private double efekPantul(double t)
    {
        double c1 = 1.70158;
        double c3 = c1 + 1;
        double u = t - 1;
        return 1 + c3 * u * u * u + c1 * u * u;
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