import greenfoot.*;

public class Map_Awal extends World
{
    // true selama panel kontrol tampil: karakter diam dan rumah belum bisa diklik
    public static boolean tutorialAktif = false;

    // Tahap: [0] awal permainan, [1] setelah Rumah 1, [2] setelah Rumah 2, [3] setelah Rumah 3
    private static final int[] SPAWN_X = {30, 478, 745, 375};
    private static final int[] SPAWN_Y = {190, 225, 208, 252};
    private static final String[] ARAH = {"kanan", "depan", "kiri", "kanan"};

    // Game baru (Start atau Reset): progres dihapus dan panel kontrol tampil
    public Map_Awal()
    {
        this(false);
    }

    // dariLevel = true: kembali dari level, progres dipertahankan, tanpa panel kontrol
    public Map_Awal(boolean dariLevel)
    {
        super(960, 540, 1);

        if (!dariLevel)
        {
            LevelBase.levelSelesai = 0;
            LevelBase.skorTotal = 0;
        }

        GreenfootImage bg = new GreenfootImage("Lahan.jpg");
        bg.scale(960, 540);
        setBackground(bg);

        // urutan pertama = digambar paling atas
        setPaintOrder(Dialog.class, PanelKontrol.class, Player.class, Rumah.class);
        
        addObject(new Rumah("Rumah_Easy.png", "easy", 210), 489, 170);
        addObject(new Rumah("Rumah_Normal.png", "normal", 190), 782, 165);
        addObject(new Rumah("Rumah_Hard.png", "hard", 170), 385, 370);
        
        int tahap = Math.min(LevelBase.levelSelesai, SPAWN_X.length - 1);
        Player p = new Player();
        addObject(p, SPAWN_X[tahap], SPAWN_Y[tahap]);
        p.setArah(ARAH[tahap]);

        tutorialAktif = !dariLevel;
        if (tutorialAktif)
        {
            addObject(new PanelKontrol(), 480, 270);
        }
    }
}