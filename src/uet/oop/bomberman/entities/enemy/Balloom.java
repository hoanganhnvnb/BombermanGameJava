package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Balloom extends Enemy {
    private int time = 0;

    public Balloom(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        point = 100;
    }

    @Override
    public void update() {
        super.update();
        go();
        choseSprite();
    }

    @Override
    protected void choseSprite() {
        if (isAlive()) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2
                        , Sprite.balloom_right3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2
                        , Sprite.balloom_left3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.balloom_dead.getFxImage();
        }
    }

    @Override
    protected void go() {
        if (isAlive()) {
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (checkBounds() || checkBomb() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (getY() % Sprite.SCALED_SIZE != 0) {
                        this.y -= this.getSpeedY();
                    }
                    this.randomVector();
                }
            } else {
                this.x += this.getSpeedX();
                if (checkBounds() || checkBomb() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (getX() % Sprite.SCALED_SIZE != 0) {
                        this.x -= this.getSpeedX();
                    }
                    this.randomVector();
                }
            }
        } else {
            this.enemyDead();
        }
    }
}
