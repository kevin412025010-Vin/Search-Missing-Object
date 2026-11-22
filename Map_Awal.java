import greenfoot.*;

public class Map_Awal extends World
{
    // Tahap: [0] awal permainan, [1] setelah Rumah 1, [2] setelah Rumah 2, [3] setelah Rumah 3
    private static final int[] SPAWN_X = {30, 478, 745, 375};
    private static final int[] SPAWN_Y = {190, 225, 208, 252};
    private static final String[] ARAH = {"kanan", "depan", "kiri", "kanan"};

    public Map_Awal()
    {
        super(960, 540, 1);

        GreenfootImage bg = new GreenfootImage("Lahan.jpg");
        bg.scale(960, 540);
        setBackground(bg);

        setPaintOrder(Player.class, Rumah.class);

        addObject(new Rumah("Rumah_Easy.png", "easy", 210), 487, 170);
        addObject(new Rumah("Rumah_Normal.png", "normal", 190), 782, 165);
        //addObject(new Rumah("Rumah_Hard.png", "hard", 240), 375, 310);

        // Karakter muncul di depan rumah terakhir yang diselesaikan, dengan arah hadap sesuai tahap
        int tahap = Math.min(LevelBase.levelSelesai, SPAWN_X.length - 1);
        Player p = new Player();
        addObject(p, SPAWN_X[tahap], SPAWN_Y[tahap]);
        p.setArah(ARAH[tahap]);
    }
}