import greenfoot.*;
import java.util.*;

public class Dialog extends Actor
{
    // ===== ISI CERITA PEMBUKA: ganti sesuka hati, format {pembicara, kalimat} =====
    public static final String[][] PEMBUKA = {
        {"Narator", "Jauh di dalam gua berkristal, tersimpan ribuan kartu ajaib. Setiap kartu menjaga satu kenangan dari dunia di atas sana."},
        {"Narator", "Namun suatu malam, cahaya kristal meredup. Kartu-kartu itu berhamburan, dan kenangan di dalamnya perlahan mulai terlupakan."},
        {"Kamu",    "Kalau kartu-kartu itu hilang, kenangan itu ikut lenyap selamanya. Aku harus menemukan dan mengingatnya kembali."},
        {"Narator", "Di gua ini berdiri rumah-rumah penjaga kartu. Setiap rumah menyimpan ujian ingatan yang harus kamu selesaikan."},
        {"Narator", "Perhatikan kartunya baik-baik, ingat posisinya, dan jangan sampai ada yang terlewat. Mulailah dari rumah di tengah."}
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

    public Dialog(String[][] isiCerita)
    {
        this.isiCerita = isiCerita;
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
                Map_Awal.tutorialAktif = false;   // cerita selesai, karakter boleh jalan
                getWorld().removeObject(this);
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