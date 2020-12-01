package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy {
    public Doll(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        point = 400;
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
                this.img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2
                        , Sprite.doll_right3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2
                        , Sprite.doll_left3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.doll_dead.getFxImage();
        }
    }

    @Override
    protected void go() {
        if (isAlive()) {
            int diffX = Math.abs(getX() - EntityArr.bomberman.getX());
            int diffY = Math.abs(getY() - EntityArr.bomberman.getY());
            if (this.getSpeedX() == 0) {
                this.y += this.getSpeedY();
                if (diffX <= Sprite.SCALED_SIZE * 10 && diffY <= Sprite.SCALED_SIZE * 10 || getY() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.y -= this.getSpeedY();
                        if (getY() % Sprite.SCALED_SIZE == 0 && getY() == EntityArr.bomberman.getY()) {
                            choseVector(diffRaw());
                        }
                    }
                    if (getY() % Sprite.SCALED_SIZE == 0) {
                        if (getY() == EntityArr.bomberman.getY())
                            choseVector(diffRaw());
                        else
                            choseVector(calculateVector());
                    }
                } else
                if (checkBounds() || checkBomb() || getY() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.y -= this.getSpeedY();
                    }
                    this.randomVector();
                }
            } else {
                this.x += this.getSpeedX();
                if (diffX <= Sprite.SCALED_SIZE * 10 && diffY <= Sprite.SCALED_SIZE * 10 && getX() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.x -= this.getSpeedX();
                        if (getX() == EntityArr.bomberman.getX()) {
                            choseVector(diffCol());
                        }
                    }
                    if (getX() % Sprite.SCALED_SIZE == 0) {
                        if (getX() == EntityArr.bomberman.getX())
                            choseVector(diffCol());
                        else
                            choseVector(calculateVector());
                    }
                } else
                if (checkBounds() || checkBomb() || getX() % Sprite.SCALED_SIZE == 0) {
                    if (checkBounds() || checkBomb()) {
                        this.x -= this.getSpeedX();
                    }
                    this.randomVector();
                }
            }
            choseSprite();
        } else {
            this.enemyDead();
        }
    }

    protected int calculateVector() {
        if(getVector() == 1) {
            int v = diffRaw();
            if(v != -1)
                return v;
            else
                return diffCol();

        } else {
            int h = diffCol();

            if(h != -1)
                return h;
            else
                return diffRaw();
        }
    }
}
