package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Entity {
    private int speed = Sprite.DEFAULT_SIZE / 10;
    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (this.speed > 0) {
            this.x += this.speed;
            this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2
                    , Sprite.balloom_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
        } else {
            this.x += this.speed;
            this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2
                    , Sprite.balloom_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
