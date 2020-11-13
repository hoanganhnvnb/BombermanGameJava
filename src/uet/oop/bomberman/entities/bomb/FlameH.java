package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class FlameH extends Flame {
    public FlameH(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        this.animate += Sprite.DEFAULT_SIZE / 10;
        this.setImg(Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1
                , Sprite.explosion_horizontal2, animate, Sprite.DEFAULT_SIZE).getFxImage());
    }
}