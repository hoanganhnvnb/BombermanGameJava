package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (checkBounds()) {
            this.setSpeed(getSpeed() * -1);
        }
        if (this.getSpeed() > 0) {
            this.x += this.getSpeed();
            this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2
                    , Sprite.oneal_right3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
        } else {
            this.x += this.getSpeed();
            this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2
                    , Sprite.oneal_left3, this.x, Sprite.DEFAULT_SIZE).getFxImage();
        }
    }
}
