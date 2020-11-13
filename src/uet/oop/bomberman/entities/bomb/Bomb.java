package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.blocks.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

public class Bomb extends Entity {
    public static int flameLength = 2;
    public static List<Flame> flames = new ArrayList<>();
    private List<Flame> fLeft = new ArrayList<>();
    private List<Flame> fRight = new ArrayList<>();
    private List<Flame> fUp = new ArrayList<>();
    private List<Flame> fDown = new ArrayList<>();
    private boolean isExploded = false;
    public Bomb(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (this.isExploded()) {
            addFlame();
            this.animate += Sprite.DEFAULT_SIZE / 10;
            this.setImg(Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1
                    , Sprite.bomb_exploded2, animate, Sprite.DEFAULT_SIZE).getFxImage());
            updateFlame();
        } else {
            this.animate += Sprite.DEFAULT_SIZE / 10;
            this.setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1
                    , Sprite.bomb_2, animate, Sprite.DEFAULT_SIZE).getFxImage());
        }
    }

    public void addFlame() {
        for (int i = 0; i < flameLength; ++i) {
            Flame flameV;
            flameV = new FlameV(getX() / Sprite.SCALED_SIZE, getY() / Sprite.SCALED_SIZE + 1 + i
                    , Sprite.explosion_vertical.getFxImage());
            fDown.add(flameV);
            flameV = new FlameV(getX() / Sprite.SCALED_SIZE, getY() / Sprite.SCALED_SIZE - 1 - i
                    , Sprite.explosion_vertical.getFxImage());
            fUp.add(flameV);

            Flame flameH;
            flameH = new FlameH(getX() / Sprite.SCALED_SIZE + i + 1, getY() / Sprite.SCALED_SIZE
                    , Sprite.explosion_horizontal.getFxImage());
            fRight.add(flameH);
            flameH = new FlameH(getX() / Sprite.SCALED_SIZE - i - 1, getY() / Sprite.SCALED_SIZE
                    , Sprite.explosion_horizontal.getFxImage());
            fLeft.add(flameH);
        }

        this.checkFlame();
    }

    public static int getFlameLength() {
        return flameLength;
    }

    public static void setFlameLength(int flameLength) {
        Bomb.flameLength = flameLength;
    }

    public boolean isExploded() {
        return isExploded;
    }

    public void setExploded(boolean exploded) {
        isExploded = exploded;
    }

    private void updateFlame() {
        for (Flame f : fUp) {
            f.update();
        }
        for (Flame f : fDown) {
            f.update();
        }
        for (Flame f : fLeft) {
            f.update();
        }
        for (Flame f : fDown) {
            f.update();
        }
    }

    public void removeFlame() {
        flames.clear();
    }

    private void checkFlame() {
        for (Flame f : fUp) {
            f.checkWall();
            f.checkBrick();
            if (f.isVisible()) {
                flames.add(f);
            }
        }
        for (Flame f : fDown) {
            f.checkWall();
            f.checkBrick();
            if (f.isVisible()) {
                flames.add(f);
            }
        }
        for (Flame f : fLeft) {
            f.checkWall();
            f.checkBrick();
            if (f.isVisible()) {
                flames.add(f);
            }
        }
        for (Flame f : fRight) {
            f.checkWall();
            f.checkBrick();
            if (f.isVisible()) {
                flames.add(f);
            }
        }
    }
}
