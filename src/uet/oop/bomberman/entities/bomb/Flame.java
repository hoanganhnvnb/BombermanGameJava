package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.blocks.Brick;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Flame extends Entity {
    private boolean isVisible = true;
    public Flame(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public abstract void update();

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    protected void checkWall() {
        for (Entity w : walls) {
            if (this.getX() == w.getX() && this.getY() == w.getY()) {
                this.setVisible(false);
                return;
            }
        }
    }

    protected boolean checkBrick() {
        for (Brick b : bricks) {
            if (this.getX() == b.getX() && this.getY() == b.getY()) {
                this.setVisible(false);
                b.setBroken(true);
                return true;
            }
        }
        return false;
    }
}
