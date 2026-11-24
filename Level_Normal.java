import greenfoot.*;
import java.util.*;

public class Level_Normal extends World {

    private enum Fase { INTIP, MAIN, JEDA_SALAH, JEDA_BENAR, SELESAI }

    private static final int NOMOR_LEVEL = 2;
    private static final int TOTAL_KARTU = 45;
    private static final int JUMLAH_PASANG = 3;      // 3 gambar x 2 kartu = 6 kartu
    private static final int JUMLAH_RONDE = 3;
    private static final int NYAWA_AWAL = 8;
    private static final int POIN_PASANGAN = 10;
    private static final int DETIK_INTIP = 4;        // lama semua kartu diperlihatkan di awal ronde
    private static final int JEDA_SALAH_MS = 900;    // lama 2 kartu salah terlihat sebelum menutup
    private static final int JEDA_BENAR_MS = 1300;   // jeda setelah semua pasangan ketemu
    private static final int Y_KARTU = 240;
    private static final int AREA_KIRI = 80;
    private static final int AREA_LEBAR = 800;

    private int ronde = 0;
    private int nyawa = NYAWA_AWAL;
    private Fase fase;
    private long waktuMulai;

    private final List<Kartu> kartuList = new ArrayList<>();
    private final Set<Kartu> cocok = new HashSet<>();
    private Kartu pertama;
    private Kartu kedua;

    private final Teks teksInfo;
    private final Teks teksPesan;

    public Level_Normal() 
    {
        super(960, 540, 1);
        aturLatar("Level_Normal.jpg");

        teksInfo = new Teks(" ", 22);
        teksPesan = new Teks(" ", 30);
        addObject(teksInfo, 480, 30);
        addObject(teksPesan, 480, 390);

        mulaiRonde();
    }

    private void aturLatar(String fileLatar) 
    {
        GreenfootImage bg;
        try 
        {
            bg = new GreenfootImage(fileLatar);
            bg.scale(960, 540);
        } 
        catch (IllegalArgumentException e) 
        {
            bg = new GreenfootImage(960, 540);
            bg.setColor(new Color(25, 35, 30));
            bg.fill();
        }
        setBackground(bg);
    }

    // Posisi X kartu ke-i dari n kartu, dibagi rata di sepanjang area
    private int posisiX(int i, int n) 
    {
        return AREA_KIRI + (2 * i + 1) * AREA_LEBAR / (2 * n);
    }

    private void mulaiRonde() 
    {
        ronde++;
        removeObjects(getObjects(Kartu.class));
        kartuList.clear();
        cocok.clear();
        pertama = null;
        kedua = null;

        // pilih 3 gambar berbeda, masing-masing dibuat 2 kartu, lalu diacak
        List<Integer> semua = new ArrayList<>();
        for (int i = 1; i <= TOTAL_KARTU; i++) semua.add(i);
        Collections.shuffle(semua);

        List<Integer> isi = new ArrayList<>();
        for (int i = 0; i < JUMLAH_PASANG; i++) 
        {
            isi.add(semua.get(i));
            isi.add(semua.get(i));
        }
        Collections.shuffle(isi);

        for (int i = 0; i < isi.size(); i++) 
        {
            Kartu k = new Kartu(isi.get(i));   // mulai dalam keadaan terbuka
            kartuList.add(k);
            addObject(k, posisiX(i, isi.size()), Y_KARTU);
        }

        fase = Fase.INTIP;
        waktuMulai = System.currentTimeMillis();
        perbaruiInfo();
        teksPesan.setText("Ingat posisi kartunya!  " + DETIK_INTIP);
    }

    public void act() 
    {
        long lewat = System.currentTimeMillis() - waktuMulai;

        if (fase == Fase.INTIP) 
        {
            long sisaMs = DETIK_INTIP * 1000L - lewat;
            if (sisaMs <= 0) 
            {
                for (Kartu k : kartuList) k.sembunyi();   // semua kartu menutup bersamaan
                fase = Fase.MAIN;
                teksPesan.setText("Cocokkan 2 gambar yang sama!");
            } 
            else 
            {
                int sisaDetik = (int) Math.ceil(sisaMs / 1000.0);
                teksPesan.setText("Ingat posisi kartunya!  " + sisaDetik);
            }
        } 
        else if (fase == Fase.MAIN) 
        {
            if (Greenfoot.mouseClicked(null)) 
            {
                MouseInfo mi = Greenfoot.getMouseInfo();
                if (mi != null) 
                {
                    List<Kartu> kena = getObjectsAt(mi.getX(), mi.getY(), Kartu.class);
                    if (!kena.isEmpty()) klik(kena.get(0));
                }
            }
        } 
        else if (fase == Fase.JEDA_SALAH) 
        {
            if (lewat >= JEDA_SALAH_MS) 
            {
                pertama.sembunyi();
                kedua.sembunyi();
                pertama = null;
                kedua = null;
                if (nyawa <= 0) 
                {
                    fase = Fase.SELESAI;
                    teksPesan.setText("Game Over!  Skor: " + LevelBase.skorTotal + "  (klik untuk kembali)");
                } 
                else 
                {
                    fase = Fase.MAIN;
                    teksPesan.setText("Cocokkan 2 gambar yang sama!");
                }
            }
        } 
        else if (fase == Fase.JEDA_BENAR) 
        {
            if (lewat >= JEDA_BENAR_MS) 
            {
                if (ronde >= JUMLAH_RONDE) 
                {
                    selesaiLevel();
                } 
                else 
                {
                    mulaiRonde();
                }
            }
        } 
        else if (fase == Fase.SELESAI) 
        {
            if (Greenfoot.mouseClicked(null) || Greenfoot.isKeyDown("space")) 
            {
                Greenfoot.setWorld(new Map_Awal(true));
            }
        }
    }

    private void klik(Kartu k) 
    {
        if (cocok.contains(k) || k == pertama) return;   // abaikan kartu yang sudah terbuka

        k.tampil();

        if (pertama == null) 
        {
            pertama = k;
            return;
        }

        kedua = k;

        if (pertama.getId() == kedua.getId()) 
        {
            cocok.add(pertama);
            cocok.add(kedua);
            pertama = null;
            kedua = null;
            LevelBase.skorTotal += POIN_PASANGAN;
            teksPesan.setText("Cocok!  +" + POIN_PASANGAN);
            perbaruiInfo();

            if (cocok.size() == kartuList.size()) 
            {
                teksPesan.setText("Semua pasangan ditemukan!");
                fase = Fase.JEDA_BENAR;
                waktuMulai = System.currentTimeMillis();
            }
        } 
        else 
        {
            nyawa--;
            teksPesan.setText("Tidak cocok!  Nyawa tersisa: " + nyawa);
            perbaruiInfo();
            fase = Fase.JEDA_SALAH;
            waktuMulai = System.currentTimeMillis();
        }
    }

    private void selesaiLevel() 
    {
        if (NOMOR_LEVEL > LevelBase.levelSelesai) LevelBase.levelSelesai = NOMOR_LEVEL;
        fase = Fase.SELESAI;
        teksPesan.setText("Level selesai!  Skor: " + LevelBase.skorTotal + "  (klik untuk kembali ke peta)");
    }

    private void perbaruiInfo() 
    {
        teksInfo.setText("Level " + NOMOR_LEVEL
                + "   |   Ronde " + ronde + "/" + JUMLAH_RONDE
                + "   |   Skor " + LevelBase.skorTotal
                + "   |   Nyawa " + nyawa);
    }
}