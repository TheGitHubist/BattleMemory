import javax.swing.*;

public class Player extends JLabel {
    private final int maxHP;
    private final int atkStart;
    private int HPact;
    private int atk;
    Player(int maxHP, int atkStart) {
        this.maxHP = maxHP;
        this.HPact = maxHP;
        this.atkStart = atkStart;
        this.atk = atkStart;
    }

    public int getAtk() {
        return atk;
    }

    public int getAtkStart() {
        return atkStart;
    }

    public int getHPact() {
        return HPact;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setHPact(int HPact) {
        this.HPact = HPact;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
