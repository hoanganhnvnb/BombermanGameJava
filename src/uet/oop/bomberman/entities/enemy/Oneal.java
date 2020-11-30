package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Oneal extends Enemy {

    public Oneal(int xUnit, int yUnit, Image img) {
                super(xUnit, yUnit, img);
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
                this.img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2
                        , Sprite.oneal_right3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2
                        , Sprite.oneal_left3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.oneal_dead.getFxImage();
        }
    }

    @Override
    protected void go() {
        if (isAlive()) {
            int diffX = Math.abs(getX() - EntityArr.bomberman.getX());
            int diffY = Math.abs(getY() - EntityArr.bomberman.getY());
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (diffX <= Sprite.SCALED_SIZE * 3 && diffY <= Sprite.SCALED_SIZE * 3) {
                    this.setSpeed(1);
                    findBomber();
                    if (checkBounds() || checkBomb()) {
                        this.y -= this.getSpeedY();
                        findBomber();
                    }
                } else
                if (checkBounds() || checkBomb() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.y -= this.getSpeedY();
                    }
                    this.randomVector();
                    this.randomSpeed();
                }
            } else {
                this.x += this.getSpeedX();
                if (diffX <= Sprite.SCALED_SIZE * 3 && diffY <= Sprite.SCALED_SIZE * 3) {
                    this.setSpeed(1);
                    findBomber();
                    if (checkBounds() || checkBomb()) {
                        this.x -= this.getSpeedX();
                        findBomber();
                    }
                } else
                if (checkBounds() || checkBomb() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.x -= this.getSpeedX();
                    }
                    this.randomVector();
                    this.randomSpeed();
                }
            }
        } else {
            this.enemyDead();
        }
    }

    protected void randomSpeed() {
        Random random = new Random();
        int num = random.nextInt();
        if (num % 100 <= 70) this.setSpeed(1);
            else this.setSpeed(2);
    }

    protected void findBomber() {
        if (getY() == EntityArr.bomberman.getY()) {
            choseVector(diffRaw());
        } else if (getX() == EntityArr.bomberman.getX()) {
            choseVector(diffCol());
        }
    }
}

