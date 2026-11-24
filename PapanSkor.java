import greenfoot.*;
import java.util.*;

public class PapanSkor extends Actor
{
    private static final Color WARNA_JUDUL = Color.WHITE;
    private static final Color WARNA_ISI = Color.WHITE;

    public PapanSkor()
    {
        List<Integer> rekor = LevelBase.ambilRekor();

        List<GreenfootImage> baris = new ArrayList<>();
        baris.add(teksBertepi("Skor Terbaik", 22, WARNA_JUDUL));
        for (int i = 0; i < LevelBase.JUMLAH_REKOR; i++)
        {
            String nilai = (i < rekor.size()) ? String.valueOf(rekor.get(i)) : "-";
            baris.add(teksBertepi((i + 1) + ".  " + nilai, 20, WARNA_ISI));
        }

        int lebar = 0;
        int tinggi = 0;
        for (GreenfootImage b : baris)
        {
            lebar = Math.max(lebar, b.getWidth());
            tinggi += b.getHeight();
        }

        // semua baris rata kanan, latar sepenuhnya transparan
        GreenfootImage img = new GreenfootImage(lebar, tinggi);
        int y = 0;
        for (GreenfootImage b : baris)
        {
            img.drawImage(b, lebar - b.getWidth(), y);
            y += b.getHeight();
        }
        setImage(img);
    }

    // Teks dengan garis tepi gelap tipis, supaya terbaca tanpa kotak latar
    private GreenfootImage teksBertepi(String isi, int ukuran, Color warna)
    {
        GreenfootImage depan = new GreenfootImage(isi, ukuran, warna, new Color(0, 0, 0, 0));
        GreenfootImage tepi = new GreenfootImage(isi, ukuran, Color.BLACK, new Color(0, 0, 0, 0));

        GreenfootImage hasil = new GreenfootImage(depan.getWidth() + 4, depan.getHeight() + 4);
        for (int dx = -1; dx <= 1; dx++)
        {
            for (int dy = -1; dy <= 1; dy++)
            {
                if (dx != 0 || dy != 0) hasil.drawImage(tepi, 2 + dx, 2 + dy);
            }
        }
        hasil.drawImage(depan, 2, 2);
        return hasil;
    }
}