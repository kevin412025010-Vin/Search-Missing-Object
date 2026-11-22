import greenfoot.*;

public class PilihanKartu extends Kartu 
{

    private final LevelBase level;

    public PilihanKartu(int id, LevelBase level) 
    {
        super(id);
        this.level = level;
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            level.jawab(getId());
        }
    }
}