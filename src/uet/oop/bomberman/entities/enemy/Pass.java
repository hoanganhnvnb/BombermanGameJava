package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.EntityArr;
import uet.oop.bomberman.graphics.Sprite;

public class Pass extends Enemy {
    public Pass(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(3);
    }

    @Override
    protected void choseSprite() {
        if (isAlive()) {
            if (this.getSpeedX() > 0) {
                this.img = Sprite.movingSprite(Sprite.pass_right1, Sprite.pass_right2
                        , Sprite.pass_right3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            } else {
                this.img = Sprite.movingSprite(Sprite.pass_left1, Sprite.pass_left2
                        , Sprite.pass_left3, this.animate, Sprite.DEFAULT_SIZE).getFxImage();
            }
        } else {
            this.img = Sprite.pass_dead.getFxImage();
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
                        } else {
                            randomVector();
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
                        } else {
                            randomVector();
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
