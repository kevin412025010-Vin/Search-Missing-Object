import greenfoot.*;
import java.util.*;

public abstract class LevelBase extends World {

    private enum Fase { MENGINGAT, MENJAWAB, FEEDBACK, SELESAI }

    private static final int TOTAL_KARTU = 45;
    private static final int JARAK_KARTU = 105;
    private static final int JARAK_PILIHAN = 150;
    private static final int Y_KARTU = 170;
    private static final int Y_PILIHAN = 400;
    private static final int NYAWA_AWAL = 3;
    private static final int POIN_BENAR = 10;
    private static final int JEDA_FEEDBACK_MS = 1800;

    protected static int skorTotal = 0;

    private final int nomorLevel;
    private final int jumlahKartu;
    private final int detikMengingat;
    private final int jumlahPilihan;
    private final int jumlahRonde;

    private int ronde = 0;
    private int nyawa = NYAWA_AWAL;
    private boolean gameOver = false;
    private Fase fase;
    private long waktuMulai;

    private final List<Integer> semuaId = new ArrayList<>();
    private final List<Kartu> kartuList = new ArrayList<>();
    private Kartu kartuHilang;

    private Teks teksInfo;
    private Teks teksPesan;

    public LevelBase(int nomorLevel, int jumlahKartu, int detikMengingat,
                     int jumlahPilihan, int jumlahRonde, String fileLatar) 
    {
        super(960, 540, 1);
        this.nomorLevel = nomorLevel;
        this.jumlahKartu = jumlahKartu;
        this.detikMengingat = detikMengingat;
        this.jumlahPilihan = jumlahPilihan;
        this.jumlahRonde = jumlahRonde;

        aturLatar(fileLatar);

        if (nomorLevel == 1) skorTotal = 0;

        for (int i = 1; i <= TOTAL_KARTU; i++) semuaId.add(i);

        teksInfo = new Teks(" ", 22);
        teksPesan = new Teks(" ", 30);
        addObject(teksInfo, 480, 30);
        addObject(teksPesan, 480, 290);

        mulaiRonde();
    }

    protected abstract World levelBerikutnya();

    protected String pesanSelesai() 
    {
        return "Level selesai!";
    }

    private void aturLatar(String fileLatar) 
    {
        GreenfootImage bg;
        try {
            bg = new GreenfootImage(fileLatar);
            bg.scale(960, 540);
        } catch (IllegalArgumentException e) {
            bg = new GreenfootImage(960, 540);
            bg.setColor(new Color(25, 35, 30));
            bg.fill();
        }
        setBackground(bg);
    }

    private void mulaiRonde() 
    {
        ronde++;
        removeObjects(getObjects(Kartu.class));
        kartuList.clear();
        kartuHilang = null;

        List<Integer> pool = new ArrayList<>(semuaId);
        Collections.shuffle(pool);

        int startX = 480 - ((jumlahKartu - 1) * JARAK_KARTU) / 2;
        for (int i = 0; i < jumlahKartu; i++) {
            Kartu k = new Kartu(pool.get(i));
            kartuList.add(k);
            addObject(k, startX + i * JARAK_KARTU, Y_KARTU);
        }

        fase = Fase.MENGINGAT;
        waktuMulai = System.currentTimeMillis();
        perbaruiInfo();
        teksPesan.setText("Ingat kartunya!");
    }

    public void act() 
    {
        long lewat = System.currentTimeMillis() - waktuMulai;

        if (fase == Fase.MENGINGAT) {
            long sisaMs = detikMengingat * 1000L - lewat;
            if (sisaMs <= 0) {
                sembunyikanSatu();
            } else {
                int sisaDetik = (int) Math.ceil(sisaMs / 1000.0);
                teksPesan.setText("Ingat kartunya!  " + sisaDetik);
            }
        } else if (fase == Fase.FEEDBACK) {
            if (lewat >= JEDA_FEEDBACK_MS) selesaiFeedback();
        } else if (fase == Fase.SELESAI) {
            if (Greenfoot.mouseClicked(null) || Greenfoot.isKeyDown("space")) {
                pindahLevel();
            }
        }
    }

    private void sembunyikanSatu() 
    {
        kartuHilang = kartuList.get(Greenfoot.getRandomNumber(kartuList.size()));
        kartuHilang.sembunyi();
        buatPilihan();
        fase = Fase.MENJAWAB;
        teksPesan.setText("Kartu mana yang hilang?");
    }

    private void buatPilihan() 
    {
        List<Integer> opsi = new ArrayList<>();
        opsi.add(kartuHilang.getId());

        List<Integer> sisa = new ArrayList<>(semuaId);
        for (Kartu k : kartuList) sisa.remove(Integer.valueOf(k.getId()));
        Collections.shuffle(sisa);
        for (int i = 0; i < jumlahPilihan - 1; i++) opsi.add(sisa.get(i));

        Collections.shuffle(opsi);

        int startX = 480 - ((opsi.size() - 1) * JARAK_PILIHAN) / 2;
        for (int i = 0; i < opsi.size(); i++) {
            addObject(new PilihanKartu(opsi.get(i), this), startX + i * JARAK_PILIHAN, Y_PILIHAN);
        }
    }

    public void jawab(int idDipilih) 
    {
        if (fase != Fase.MENJAWAB) return;

        removeObjects(getObjects(PilihanKartu.class));
        kartuHilang.tampil();

        if (idDipilih == kartuHilang.getId()) {
            skorTotal += POIN_BENAR;
            teksPesan.setText("Benar!  +" + POIN_BENAR);
        } else {
            nyawa--;
            teksPesan.setText("Salah!  Nyawa tersisa: " + nyawa);
        }
        perbaruiInfo();

        fase = Fase.FEEDBACK;
        waktuMulai = System.currentTimeMillis();
    }

    private void selesaiFeedback() 
    {
        if (nyawa <= 0) {
            gameOver = true;
            fase = Fase.SELESAI;
            teksPesan.setText("Game Over!  Skor: " + skorTotal + "  (klik untuk kembali)");
        } else if (ronde >= jumlahRonde) {
            fase = Fase.SELESAI;
            teksPesan.setText(pesanSelesai() + "  (klik untuk lanjut)");
        } else {
            mulaiRonde();
        }
    }

    private void pindahLevel() 
    {
        if (gameOver) {
            Greenfoot.setWorld(new Map_Awal());
        } else {
            Greenfoot.setWorld(levelBerikutnya());
        }
    }

    private void perbaruiInfo() 
    {
        teksInfo.setText("Level " + nomorLevel
                + "   |   Ronde " + ronde + "/" + jumlahRonde
                + "   |   Skor " + skorTotal
                + "   |   Nyawa " + nyawa);
    }
}