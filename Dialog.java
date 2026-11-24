import greenfoot.*;
import java.util.*;

public class Dialog extends Actor
{
    // ===== CERITA PEMBUKA: format {pembicara, kalimat} =====
    public static final String[][] PEMBUKA = {
        {"Narator", "Hehehe... selamat datang di Gua Kristal! Kamu datang untuk mencari kartu-kartu yang hilang, bukan?"},
        {"Narator", "Di dalam gua ini terdapat tiga rumah yang masing - masing memiliki tantangan yang berbeda. Mulai dari yang mudah hingga yang paling sulit. Apakah kamu berani mencobanya?"},
        {"Kamu",    "..."},
        {"Narator", "..."},
        {"Kamu",    "..."},
        {"Narator", "Kalau begitu, bersiaplah! Masuki rumah pertama dan buktikan seberapa kuat ingatanmu!"}
    };

    // ===== DIALOG RUMAH 1: Tuytuy (Level Easy) =====
    public static final String[][] RUMAH_EASY = {
        {"Tuytuy", "Wih, ada pengunjung! Selamat datang di rumahku. Tenang saja, tantanganku masih mudah kok!"},
        {"Tuytuy", "Perhatikan baik-baik semua kartu yang terbuka. Ingat posisi dan gambarnya, karena sebentar lagi semua kartu akan tertutup!"},
        {"Tuytuy", "Sekarang waktunya mencari kartu sesuai gambar yang diminta. Jangan sampai salah pilih, ya! Hehehe!"}
    };

    // ===== DIALOG RUMAH 2: Kunkun (Level Normal) =====
    public static final String[][] RUMAH_NORMAL = {
        {"Kunkun", "Hihihihi... akhirnya kamu sampai juga di rumahku. Tantangan kali ini sedikit lebih sulit, lho!"},
        {"Kunkun", "Di sini kamu harus membuka dua kartu sekaligus dan menemukan pasangan gambar yang sama. Perhatikan setiap kartu baik-baik!"},
        {"Kunkun", "Jangan menyerah sebelum semua pasangan ditemukan. Aku akan menunggumu sampai berhasil! Hihihihi!"}
    };

    // ===== DIALOG RUMAH 3: Gondrong (Level Hard) =====
    public static final String[][] RUMAH_HARD = {
        {"Gondrong", "GRRR... jadi kamu berhasil melewati dua rumah sebelumnya. Sekarang saatnya menghadapi tantangan terakhir!"},
        {"Gondrong", "Di goa ini, kamu harus menggunakan seluruh kemampuanmu. Ingat posisi kartu dan temukan setiap pasangan gambar yang sama!"},
        {"Gondrong", "Tantangan ini tidak akan mudah! Buktikan bahwa ingatanmu benar-benar kuat dan selesaikan semua kartu yang ada!"}
    };

    private static final int LEBAR = 860;
    private static final int TINGGI = 180;
    private static final int PADDING = 24;
    private static final int UKURAN_TEKS = 22;
    private static final int UKURAN_NAMA = 24;
    private static final int MS_PER_HURUF = 28;      // makin kecil makin cepat mengetik
    private static final int FADE_MS = 250;          // lama efek muncul dan hilang
    private static final int JEDA_AWAL_MS = 300;     // klik dalam 0,3 detik pertama diabaikan

    private final String[][] isiCerita;
    private final String tujuanLevel;                // null = kembali ke peta, atau "easy" / "normal" / "hard"
    private int indeks = 0;
    private List<String> teksBaris = new ArrayList<>();
    private int totalHuruf = 0;
    private int hurufTampil = 0;
    private long mulaiKetik;

    private final long waktuMulai = System.currentTimeMillis();
    private boolean keluar = false;
    private long mulaiKeluar = 0;

    // untuk mendeteksi perubahan, supaya gambar hanya digambar ulang saat perlu
    private int alphaTerakhir = -1;
    private int hurufTerakhir = -1;
    private boolean kedipTerakhir = false;

    // Dialog biasa: setelah selesai, karakter boleh berjalan lagi
    public Dialog(String[][] isiCerita)
    {
        this(isiCerita, null);
    }

    // Dialog rumah: setelah selesai, langsung masuk ke level tujuan
    public Dialog(String[][] isiCerita, String tujuanLevel)
    {
        this.isiCerita = isiCerita;
        this.tujuanLevel = tujuanLevel;
        mulaiKalimat(0);
        gambar(0, false);
    }

    public void act()
    {
        long sekarang = System.currentTimeMillis();
        int alpha;

        if (keluar)
        {
            long e = sekarang - mulaiKeluar;
            if (e >= FADE_MS)
            {
                selesai();
                return;
            }
            alpha = 255 - (int) (255L * e / FADE_MS);
        }
        else
        {
            alpha = (int) Math.min(255L, 255L * (sekarang - waktuMulai) / FADE_MS);
            hurufTampil = Math.min(totalHuruf, (int) ((sekarang - mulaiKetik) / MS_PER_HURUF));

            String tombol = Greenfoot.getKey();
            boolean lanjut = Greenfoot.mouseClicked(null)
                             || "space".equals(tombol) || "enter".equals(tombol);

            if (lanjut && sekarang - waktuMulai >= JEDA_AWAL_MS)
            {
                if (hurufTampil < totalHuruf)
                {
                    // kalimat masih mengetik: tampilkan seluruhnya
                    mulaiKetik = sekarang - (long) totalHuruf * MS_PER_HURUF;
                    hurufTampil = totalHuruf;
                }
                else if (indeks + 1 < isiCerita.length)
                {
                    mulaiKalimat(indeks + 1);
                }
                else
                {
                    keluar = true;
                    mulaiKeluar = sekarang;
                }
            }
        }

        boolean kalimatSelesai = hurufTampil >= totalHuruf;
        boolean kedip = kalimatSelesai && (sekarang / 500) % 2 == 0;

        if (alpha != alphaTerakhir || hurufTampil != hurufTerakhir || kedip != kedipTerakhir)
        {
            gambar(alpha, kedip);
        }
    }

    // dipanggil setelah dialog selesai memudar
    private void selesai()
    {
        if (tujuanLevel == null)
        {
            Map_Awal.tutorialAktif = false;   // karakter boleh jalan lagi
            getWorld().removeObject(this);
        }
        else if (tujuanLevel.equals("easy"))
        {
            Greenfoot.setWorld(new Level_Easy());
        }
        else if (tujuanLevel.equals("normal"))
        {
            Greenfoot.setWorld(new Level_Normal());
        }
        else
        {
            Greenfoot.setWorld(new Level_Hard());
        }
    }

    private void mulaiKalimat(int i)
    {
        indeks = i;
        teksBaris = bungkus(isiCerita[i][1]);
        totalHuruf = 0;
        for (String b : teksBaris) totalHuruf += b.length();
        hurufTampil = 0;
        mulaiKetik = System.currentTimeMillis();
    }

    private void gambar(int alpha, boolean kedip)
    {
        alphaTerakhir = alpha;
        hurufTerakhir = hurufTampil;
        kedipTerakhir = kedip;

        GreenfootImage img = new GreenfootImage(LEBAR, TINGGI);

        // kotak dialog gelap dengan bingkai
        img.setColor(new Color(10, 18, 22, 220));
        img.fillRect(0, 0, LEBAR, TINGGI);
        img.setColor(new Color(110, 210, 195));
        img.drawRect(0, 0, LEBAR - 1, TINGGI - 1);
        img.drawRect(3, 3, LEBAR - 7, TINGGI - 7);

        // nama pembicara
        GreenfootImage nama = new GreenfootImage(isiCerita[indeks][0], UKURAN_NAMA,
                                                 new Color(120, 235, 215), new Color(0, 0, 0, 0));
        img.drawImage(nama, PADDING, 12);

        // teks yang sudah "diketik"
        int sisa = hurufTampil;
        int y = 12 + nama.getHeight() + 10;
        for (String baris : teksBaris)
        {
            int ambil = Math.min(sisa, baris.length());
            if (ambil > 0)
            {
                GreenfootImage potongan = new GreenfootImage(baris.substring(0, ambil), UKURAN_TEKS,
                                                             Color.WHITE, new Color(0, 0, 0, 0));
                img.drawImage(potongan, PADDING, y);
            }
            sisa -= ambil;
            y += UKURAN_TEKS + 10;
        }

        // segitiga berkedip = tanda "klik untuk lanjut"
        if (kedip)
        {
            img.setColor(new Color(120, 235, 215));
            int[] xs = {LEBAR - 44, LEBAR - 22, LEBAR - 33};
            int[] ys = {TINGGI - 32, TINGGI - 32, TINGGI - 18};
            img.fillPolygon(xs, ys, 3);
        }

        img.setTransparency(alpha);
        setImage(img);
    }

    // memecah kalimat menjadi beberapa baris sesuai lebar kotak
    private List<String> bungkus(String teks)
    {
        int maksLebar = LEBAR - 2 * PADDING;
        List<String> hasil = new ArrayList<>();
        String baris = "";

        for (String kata : teks.split(" "))
        {
            String coba = baris.isEmpty() ? kata : baris + " " + kata;
            if (!baris.isEmpty() && lebarTeks(coba) > maksLebar)
            {
                hasil.add(baris);
                baris = kata;
            }
            else
            {
                baris = coba;
            }
        }
        if (!baris.isEmpty()) hasil.add(baris);
        return hasil;
    }

    private int lebarTeks(String s)
    {
        return new GreenfootImage(s, UKURAN_TEKS, Color.WHITE, new Color(0, 0, 0, 0)).getWidth();
    }
}