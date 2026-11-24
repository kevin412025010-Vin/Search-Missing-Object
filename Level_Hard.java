import greenfoot.*;

public class Level_Hard extends LevelBase {

    public Level_Hard() {
        super(3, 8, 6, 5, 5, "Level_Hard.jpg", 230, 410);

        if (!Map_Awal.dialogDilihat[3]) {
            Map_Awal.dialogDilihat[3] = true;
            tampilkanDialog(Dialog.RUMAH_HARD, "Gondrong");
        }
    }

    protected World levelBerikutnya() {
        return new Map_Awal(true);
    }

    protected String pesanSelesai() {
        return "Selamat! Semua level selesai. Skor akhir: " + skorTotal;
    }
}